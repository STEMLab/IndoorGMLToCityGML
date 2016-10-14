package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class Edges extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public SpaceLayer parents;
	
	private ArrayList<Transition> transitionMember;

	public ArrayList<Transition> getTransitionMember() {
		return transitionMember;
	}
	public void setTransitionMember(ArrayList<Transition> transitionMember) {
		this.transitionMember = transitionMember;
	}
}
