package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class MultiLayeredGraph extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public IndoorFeatures parents;
	
	private ArrayList<InterEdges> interEdges;
	private ArrayList<SpaceLayers> spaceLayers;
	
	public ArrayList<InterEdges> getInterEdges() {
		return interEdges;
	}
	public void setInterEdges(ArrayList<InterEdges> interEdges) {
		this.interEdges = interEdges;
	}
	public ArrayList<SpaceLayers> getSpaceLayers() {
		return spaceLayers;
	}
	public void setSpaceLayers(ArrayList<SpaceLayers> spaceLayers) {
		this.spaceLayers = spaceLayers;
	}
}
