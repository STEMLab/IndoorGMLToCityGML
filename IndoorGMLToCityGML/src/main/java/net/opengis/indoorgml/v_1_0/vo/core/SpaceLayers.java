package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

public class SpaceLayers extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public MultiLayeredGraph parents;

	private ArrayList<SpaceLayer> spaceLayerMember;

	public ArrayList<SpaceLayer> getSpaceLayerMember() {
		return spaceLayerMember;
	}

	public void setSpaceLayerMember(ArrayList<SpaceLayer> spaceLayerMember) {
		this.spaceLayerMember = spaceLayerMember;
	}
	
}
