package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class InterEdges extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public MultiLayeredGraph parents;

	private ArrayList<InterLayerConnection> interLayerConnectionMember;

	public ArrayList<InterLayerConnection> getInterLayerConnectionMember() {
		return interLayerConnectionMember;
	}

	public void setInterLayerConnectionMember(ArrayList<InterLayerConnection> interLayerConnectionMember) {
		this.interLayerConnectionMember = interLayerConnectionMember;
	}	
}
