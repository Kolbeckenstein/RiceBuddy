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
