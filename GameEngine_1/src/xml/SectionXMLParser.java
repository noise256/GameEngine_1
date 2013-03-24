package xml;

import game.section.ShipSection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import textureManager.TextureLoader;

public class SectionXMLParser {
	public static ShipSection parseShipSectionXML(String sectionPath, int index) {
		int health;
		String sectionName = null;
		String texturePath = null;
		Vector2D sectionPosition;
		int textureWidth = 0;
		int textureHeight = 0;
		ArrayList<Float> textureVertices = new ArrayList<Float>();
		
		Document xmlDoc = getDocument(sectionPath);
		
		/**
		 * sd:health
		 */
		NodeList listOfHealths = xmlDoc.getElementsByTagName("sd:health");
		Element healthElement = (Element) listOfHealths.item(0);
		NodeList healthChildren = healthElement.getChildNodes();
		
		health = Integer.parseInt(healthChildren.item(0).getNodeValue());
		
		/**
		 * sd:section_name
		 */
		NodeList listOfSectionNames = xmlDoc.getElementsByTagName("sd:section_name");
		Element sectionNameElement = (Element) listOfSectionNames.item(0);
		NodeList sectionNameChildren = sectionNameElement.getChildNodes();
		
		sectionName = sectionNameChildren.item(0).getNodeValue();
		
		/**
		 * sd:texture_path
		 */
		NodeList listOfTexturePaths = xmlDoc.getElementsByTagName("sd:texture_path");
		Element texturePathElement = (Element) listOfTexturePaths.item(0);
		NodeList texturePathChildren = texturePathElement.getChildNodes();
		
		texturePath = texturePathChildren.item(0).getNodeValue();

		/**
		 * sd:section_position
		 */
		NodeList listOfTextureDimensions = xmlDoc.getElementsByTagName("sd:texture_dimensions");
		Element textureDimensionElement = (Element) listOfTextureDimensions.item(0);
		
		NodeList listOfDimensions = textureDimensionElement.getElementsByTagName("sd:dimension");
		Element dimensionElement = (Element) listOfDimensions.item(0);
		
		NodeList listOfWidths = dimensionElement.getElementsByTagName("sd:width");
		Element widthElement = (Element) listOfWidths.item(0);
		
		NodeList listOfHeights = dimensionElement.getElementsByTagName("sd:height");
		Element heightElement = (Element) listOfHeights.item(0);
		
		NodeList widthElementChildren = widthElement.getChildNodes();
		NodeList heightElementChildren = heightElement.getChildNodes();
		
		textureWidth = Integer.parseInt(widthElementChildren.item(0).getNodeValue());
		textureHeight = Integer.parseInt(heightElementChildren.item(0).getNodeValue());
		
		/**
		 * sd:texture_dimensions
		 */
		NodeList listOfSectionPositions = xmlDoc.getElementsByTagName("sd:section_position");
		Element sectionPositionElement = (Element) listOfSectionPositions.item(0);
		
		NodeList listOfPositions = sectionPositionElement.getElementsByTagName("sd:position");
		Element positionElement = (Element) listOfPositions.item(0);
		
		NodeList listOfPositionXs = positionElement.getElementsByTagName("sd:x");
		Element positionXElement = (Element) listOfPositionXs.item(0);
		
		NodeList listOfPositionYs = positionElement.getElementsByTagName("sd:y");
		Element positionYElement = (Element) listOfPositionYs.item(0);
		
		NodeList positionXElementChildren = positionXElement.getChildNodes();
		NodeList positionYElementChildren = positionYElement.getChildNodes();
		
		sectionPosition = new Vector2D(
				Double.parseDouble(positionXElementChildren.item(0).getNodeValue()),
				Double.parseDouble(positionYElementChildren.item(0).getNodeValue())
		);
		
		/**
		 * sd:vertices
		 */
		NodeList listOfVertices = xmlDoc.getElementsByTagName("sd:vertices");
		Element verticesElement = (Element) listOfVertices.item(0);
		
		NodeList listOfVertexElements = verticesElement.getElementsByTagName("sd:vertex");
		
		for (int i = 0; i < listOfVertexElements.getLength(); i++) {
			Element vertexElement = (Element) listOfVertexElements.item(i);
			
			NodeList listOfXs = vertexElement.getElementsByTagName("sd:x");
			NodeList listOfYs = vertexElement.getElementsByTagName("sd:y");
			
			Element xElement = (Element) listOfXs.item(0);
			Element yElement = (Element) listOfYs.item(0);
			
			NodeList xElementChildren = xElement.getChildNodes();
			NodeList yElementChildren = yElement.getChildNodes();
			
			textureVertices.add(new Float(Float.parseFloat(xElementChildren.item(0).getNodeValue())));
			textureVertices.add(new Float(Float.parseFloat(yElementChildren.item(0).getNodeValue())));
		}
		
		return new ShipSection(null, index, health, sectionName, texturePath, sectionPosition, textureWidth, textureHeight, textureVertices);
	}
	
	private static Document getDocument(String docPath) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setValidating(true);
		
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new DefaultErrorHandler());
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Document doc = null;
		try {
			InputStream fis = TextureLoader.class.getClassLoader().getResourceAsStream(docPath);
			doc = builder.parse(fis);
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return doc;
	}
}