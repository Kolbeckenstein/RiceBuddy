package riceBuddy;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.Scanner;

/**
 * @author Mike & Lizzie Kolbeck
 * @version 0.1
 */
public class MainDriver {

	public static void main(String[] args) {

		// use builder to make a DOM
		Document inputDocument = openXMLMakeDoc("RiceData.xml");

		// gets different types of grain
		String[] RiceMenu = MenuGrabber(false, null, inputDocument);

		// making a scanner
		Scanner stringScanbot = new Scanner(System.in);
		Scanner doubleScanBot = new Scanner(System.in);

		// asking for the type of grain, outputting it to the console, then
		// taking the users response as a String
		System.out.println("Please enter a grain. The available grain options are:");
		for (int k = 0; k < RiceMenu.length; k++) {
			System.out.println(RiceMenu[k]);
		}
		String grainNameGiven = stringScanbot.nextLine();

		// Takes the user given grain name, pulls the available cook types for
		// that grain

		System.out.println("Please enter a method of cooking. The available methods are:");
		String[] CookMenu = MenuGrabber(true, grainNameGiven, inputDocument);
		for (int k = 0; k < CookMenu.length; k++) {
			System.out.println(CookMenu[k]);
		}
		// uses user input to select a cook Style
		String cookStyleGiven = stringScanbot.nextLine();

		// creating a rice object, which will pull a expansionRatio,
		// waterRatio, and CookTime from the xmlFile for the given grainType and
		// cookStyle
		Rice testRice = new Rice(grainNameGiven, cookStyleGiven, inputDocument);

		System.out.println("How much rice would you like to make (in cups)?");
		Double inputGrainQuantity = doubleScanBot.nextDouble();
		Double startingGrainQuantity;
		Double finalGrainQuantity;

		System.out.println("Is that the amount of cooked or uncooked rice?");
		String cookedOrUncooked = stringScanbot.nextLine();

		if (cookedOrUncooked.equals("cooked")) {

			finalGrainQuantity = inputGrainQuantity;
			startingGrainQuantity = (finalGrainQuantity / testRice.getExpansionRatio());
		} else {
			startingGrainQuantity = inputGrainQuantity;
			finalGrainQuantity = inputGrainQuantity * testRice.getExpansionRatio();
		}

		System.out.println("Thank you! Creating your customized recipe...\n\n");
		// END INPUT

		System.out.println("");
		System.out.println(testRice.getWaterRatio());
		System.out.println(testRice.getExpansionRatio());
		System.out.println(testRice.getCookTime());
		System.out.println("");

		System.out.println(
				"Add " + startingGrainQuantity * testRice.getWaterRatio() + " cups of water to your " + cookStyleGiven);
		System.out.println("Add " + startingGrainQuantity + " cups of rice to your " + cookStyleGiven);
		System.out.println("Cook rice for " + testRice.getCookTime() + " minutes.");
		System.out.println("Remove lid and enjoy! You will now have " + finalGrainQuantity + " cups of cooked rice.");

		// ---------------------------------------

		/*
		 * create doc ask user what rice type give dropdown based on each rice
		 * user selects one uses that selection to populate dropdown with cook
		 * types for this uses that to make a rice object rice object asks
		 * xmlreader for cookTime, expension ratio, and water ratio
		 *
		 * 
		 */
	}

	/**
	 * Changes a NodeList to an array of strings that are the name attribute of
	 * each node in the NodeList.
	 * 
	 * @param toConvert
	 *            An object of type NodeList to be converted into an array of
	 *            type string.
	 * @return grainslist
	 */
	public static String[] nodeListToArray(NodeList toConvert) {

		// creating array of Strings of length equal to the number of grain
		// nodes
		int listLength = toConvert.getLength();
		String[] initialNameArray = new String[listLength];
		// print the number of grains in the grainList
		for (int i = 0; i < listLength; i++) {
			// create a node reference, point to the i'th Node in the
			// grainNodeList
			Node tempNode = toConvert.item(i);
			// Print the name of the Node tag name that tempNode points to
			// then, if tempNode is pointing to an element
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// create an element reference, pointing to tempNode, caste
				// as an element
				Element tempElement = (Element) tempNode;
				// add that element's name attribute to the grainList
				initialNameArray[i] = tempElement.getAttribute("name");
			}
		}

		// counts the number of nulls, allowing the creation of an array with
		// length = the number of elements
		int numberNulls = 0;
		for (int i = 0; i < listLength; i++) {
			if (initialNameArray[i] == null) {
				numberNulls++;
			}
		}

		// create a new array to store the element data without nulls (allowing
		// access by index)
		int finalLength = listLength - numberNulls;
		String[] finalNameArray = new String[finalLength];

		// create variable to count index in new array
		int j = 0;
		// for each item in the original array...
		for (int i = 0; i < listLength; i++) {
			// if that element of the array has element data...
			if (initialNameArray[i] != null) {
				// set correct (j'th) element in the array equal to the element
				// in the original array
				finalNameArray[j] = initialNameArray[i];
				// increment j by 1 to account for the new array being filled
				j++;
			}
		}

		return finalNameArray;
	}

	// This static method opens the xml input document and returns a DOM
	// "document"
	public static Document openXMLMakeDoc(String inputXMLDoc) {
		try {
			// create a file object to be opened
			File inputFile = new File(inputXMLDoc);

			// Create a document builder factory, use factory to create a new
			// document builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// use builder to make a DOM
			Document inputDocument = builder.parse(inputFile);
			return inputDocument;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// This pulls data from the xml file, returns it as an array of Strings
	public static String[] MenuGrabber(boolean secondStepDown, String elementAttribute, Document inputDocument) {

		// get the root node to begin traversal
		Node root = inputDocument.getDocumentElement();

		// if secondStepDown is false...
		if (secondStepDown == false) {
			// get NodeList
			NodeList riceNodes = root.getChildNodes();
			// use nodeListToArray to convert to Array of Strings, return
			return nodeListToArray(riceNodes);
		}

		// TODO make this into a method that can be called from a class, like,
		// wherever
		// if secondStepDown is true...
		else {
			// get first child
			Node tempNode = root.getFirstChild();
			// keep getting next sibling until element with correct attribute
			// found
			while (tempNode.getNextSibling() != null) {
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					Element tempElement = (Element) tempNode;
					if (tempElement.getAttribute("name").equals(elementAttribute)) {
						break;
					} else {
						tempNode = tempNode.getNextSibling();
					}
				} else {
					tempNode = tempNode.getNextSibling();
				}
			}
			// get list of child nodes of that element
			NodeList cookTypeNodes = tempNode.getChildNodes();
			// use nodeListToArray to convert to Array of Strings, return it
			return nodeListToArray(cookTypeNodes);
			// return array of strings
		}

		// creating list of Nodes of TagName "Rice"
		// NodeList grainNodeList = inputDocument.getElementsByTagName("Rice");

		// //String[] grainsList = nodeListToArray(grainNodeList);
		// // once the above for loop generates an array of grains...
		// //for (int i = 0; i < grainsList.length; i++) {
		// // print each grain in the array
		// // System.out.println(grainsList[i]);
		// //}
		// // create a reference to the first node in the nodeList, so we can
		// // traverse the list for a
		// // specific, given type of grain
		// Node root = inputDocument.getDocumentElement();
		// // System.out.println("The current node type we are examining is: "
		// // + tempNode2.getNodeName());
		// // //get the first child node of the root (which should be a rice)
		// rices = root.getFirstChild();
		// // System.out.println("The current node type we are examining is: "
		// // + tempNode2.getNodeName());
		//
		// while (tempNode2.getNextSibling() != null) {
		// if (tempNode2.getNodeType() == tempNode2.ELEMENT_NODE) {
		// Element tempElement2 = (Element) tempNode2;
		// if (tempElement2.getAttribute("name").equals("Jasmine")) {
		// System.out.println("Yay");
		// break;
		// } else {
		// tempNode2 = tempNode2.getNextSibling();
		// }
		// } else {
		// tempNode2 = tempNode2.getNextSibling();
		// }
		// }
		// NodeList cookTypeList = tempNode2.getChildNodes();
		// String[] cookTypeStrings = nodeListToArray(cookTypeList);
		// for (int j = 0; j < cookTypeStrings.length; j++) {
		// System.out.println(cookTypeStrings[j]);
		// }

	}

}
