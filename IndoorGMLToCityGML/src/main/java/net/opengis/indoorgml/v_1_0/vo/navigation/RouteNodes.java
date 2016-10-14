package net.opengis.indoorgml.v_1_0.vo.navigation;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;

public class RouteNodes extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Route parents;
	
	private ArrayList<RouteNode> nodeMember;


	public ArrayList<RouteNode> getNodeMember() {
		return nodeMember;
	}

	public void setNodeMember(ArrayList<RouteNode> nodeMember) {
		if(nodeMember.size() >= 2)
			this.nodeMember = nodeMember;
	}
}
