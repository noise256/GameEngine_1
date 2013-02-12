package modelManager;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.Hashtable;

import com.jogamp.common.nio.Buffers;

public class ModelLoader {
	private static Hashtable<String, FloatBuffer> vertexBuffers = new Hashtable<String, FloatBuffer>();
	private static Hashtable<String, FloatBuffer> colourBuffers = new Hashtable<String, FloatBuffer>();

	public static void loadVertexBuffer(String name, FloatBuffer buffer) {
		if (!vertexBuffers.containsKey(name)) {
			vertexBuffers.put(name, buffer);
		}
	}

	public static void loadVertexBuffer(String path, String name) {
		if (!vertexBuffers.containsKey(name)) {
			try {
				vertexBuffers.put(name, VertexArrayConverter.processFile(path));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static void loadColourBuffer(String path, String name) {
		// TODO not implemented yet
		if (!colourBuffers.containsKey(name)) {
			FloatBuffer colourBuffer = Buffers.newDirectFloatBuffer(1008);
			colourBuffer.put(0.5f);
			colourBuffers.put(name, colourBuffer);
		}
	}

	public static FloatBuffer getVertexBuffer(String name) {
		return vertexBuffers.get(name);
	}

	public static FloatBuffer getColourBuffer(String name) {
		return colourBuffers.get(name);
	}
}
