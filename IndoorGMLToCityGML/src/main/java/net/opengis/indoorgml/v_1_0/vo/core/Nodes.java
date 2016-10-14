package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class Nodes extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public SpaceLayer parents;
	
	private ArrayList<State> stateMember;

	public ArrayList<State> getStateMember() {
		return stateMember;
	}
	public void setStateMember(ArrayList<State> stateMember) {
		this.stateMember = stateMember;
	}
}
