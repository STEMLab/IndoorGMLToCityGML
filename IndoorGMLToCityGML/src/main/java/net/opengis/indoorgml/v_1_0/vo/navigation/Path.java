package net.opengis.indoorgml.v_1_0.vo.navigation;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;

public class Path extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Route parents;
	
	private ArrayList<RouteSegment> routeMember;

	public ArrayList<RouteSegment> getRouteMember() {
		return routeMember;
	}

	public void setRouteMember(ArrayList<RouteSegment> routeMember) {
		this.routeMember = routeMember;
	}
}
