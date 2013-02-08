package modelManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jogamp.common.nio.Buffers;

public class VertexArrayConverter {
	public static FloatBuffer processFile(String path) throws FileNotFoundException {
		//file iterator
		Scanner scanner = new Scanner(new FileReader(path));
		
		//vertex array collections
		ArrayList<float[]> vertices = new ArrayList<float[]>();
		ArrayList<int[]> faces = new ArrayList<int[]>();
		
		//process obj file
		try {
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine(), vertices, faces);
			}
		}
		finally {
			scanner.close();
		}
		
		//create vertex array buffer objects
		FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(faces.size() * 9);
		for (int i = 0; i < faces.size(); i++) {
			for (int j = 0; j < 3; j++) {
				int vIndex = faces.get(i)[j] - 1;
				for (int k = 0; k < 3; k++) {
					vertexBuffer.put(vertices.get(vIndex)[k]);
				}
			}
		}
		
		//reset buffer position
		vertexBuffer.rewind();
		
		return vertexBuffer;
	}
	
	private static void processLine(String line, ArrayList<float[]> vertices, ArrayList<int[]> faces) {
		Pattern vertexReg = Pattern.compile("v\\s(-?[0-9]+\\.[0-9]+)\\s(-?[0-9]+\\.[0-9]+)\\s(-?[0-9]+\\.[0-9]+)");
		Matcher vertexMatcher = vertexReg.matcher(line);
		
		Pattern triReg = Pattern.compile("f\\s([0-9]+)\\s([0-9]+)\\s([0-9]+)");
		Matcher triMatcher = triReg.matcher(line);
		
		if (vertexMatcher.matches()) {
			float[] newVertex = new float[3];
			for (int i = 1; i <= vertexMatcher.groupCount(); i++) {
				newVertex[i - 1] = Float.parseFloat(vertexMatcher.group(i)); 
			}
			vertices.add(newVertex);
		}
		else if (triMatcher.matches()) {
			int[] newTri = new int[3];
			for (int i = 1; i <= triMatcher.groupCount(); i++) {
				newTri[i - 1] = Integer.parseInt(triMatcher.group(i)); 
			}
			faces.add(newTri);
		}
	}
}
