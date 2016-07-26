package riceBuddy;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Rice {
	// instance variables
	private String grainType;
	private String cookStyle;
	private double expansionRatio;
	private double waterRatio;
	private int cookTime;

	// constructor
	public Rice(String grainNameGiven, String cookStyleGiven, Document xmlDocInput) {
		this.grainType = grainNameGiven;
		this.cookStyle = cookStyleGiven;
		this.expansionRatio = Double
				.parseDouble(infoGrabber(xmlDocInput, grainNameGiven, cookStyleGiven, "ExpansionRatio"));
		this.waterRatio = Double.parseDouble(infoGrabber(xmlDocInput, grainNameGiven, cookStyleGiven, "WaterRatio"));
		this.cookTime = Integer.valueOf(infoGrabber(xmlDocInput, grainNameGiven, cookStyleGiven, "CookTime"));
	}

	
	
	public String getGrainType() {
		return grainType;
	}



	public String getCookStyle() {
		return cookStyle;
	}



	public double getExpansionRatio() {
		return expansionRatio;
	}



	public double getWaterRatio() {
		return waterRatio;
	}



	public int getCookTime() {
		return cookTime;
	}



	private String infoGrabber(Document xmlDocInput, String grainNameGiven, String cookStyleGiven, String itemToPull) {

		Node root = xmlDocInput.getDocumentElement();

		// get first child
		Node grainTypeNode = root.getFirstChild();

		// keep getting next sibling until element with correct attribute found
		while (grainTypeNode.getNextSibling() != null) {
			if (grainTypeNode.getNodeType() == Node.ELEMENT_NODE) {
				Element tempElement = (Element) grainTypeNode;
				if (tempElement.getAttribute("name").equals(grainNameGiven)) {
					break;
				} else {
					grainTypeNode = grainTypeNode.getNextSibling();
				}
			} else {
				grainTypeNode = grainTypeNode.getNextSibling();
			}
		}

		// get first child
		Node cookTypeNode = grainTypeNode.getFirstChild();
		// keep getting next sibling until element with correct attribute found
		while (cookTypeNode.getNextSibling() != null) {
			if (cookTypeNode.getNodeType() == Node.ELEMENT_NODE) {
				Element tempElement = (Element) cookTypeNode;
				if (tempElement.getAttribute("name").equals(cookStyleGiven)) {
					break;
				} else {
					cookTypeNode = cookTypeNode.getNextSibling();
				}
			} else {
				cookTypeNode = cookTypeNode.getNextSibling();
			}
		}
		Node itemToPullNode = cookTypeNode.getFirstChild();
		// keep getting next sibling until element with correct attribute found
		while (itemToPullNode.getNextSibling() != null) {
			if (itemToPullNode.getNodeType() == Node.ELEMENT_NODE) {
				Element tempElement = (Element) itemToPullNode;
				if (tempElement.getNodeName().equals(itemToPull)) {
					break;
				} else {
					itemToPullNode = itemToPullNode.getNextSibling();
				}
			} else {
				itemToPullNode = itemToPullNode.getNextSibling();
			}
		}
		itemToPullNode.getNextSibling();
		Node dataToReturn = itemToPullNode.getFirstChild();
		return dataToReturn.getNodeValue();

	}

	// methods

}
