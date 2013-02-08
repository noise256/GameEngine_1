package modelManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.media.opengl.GL3bc;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {
	private static Hashtable<String, Texture> textures = new Hashtable<String, Texture>();
	
	public static void loadTexture(GL3bc gl, String name, String path) {
		Texture texture = null;
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	    try {
			texture = TextureIO.newTexture(fis, true, TextureIO.PNG);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    textures.put(name, texture);
	}
	
	public static Texture getTexture(String name) {
		return textures.get(name);
	}
}
