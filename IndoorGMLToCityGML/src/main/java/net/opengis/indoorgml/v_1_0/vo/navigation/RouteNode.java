package net.opengis.indoorgml.v_1_0.vo.navigation;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.core.IndoorObject;
import net.opengis.indoorgml.v_1_0.vo.core.State;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOPoint;

public class RouteNode extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public Object parents;
	public Integer referencedStateID;
	public Integer geometryID;
	
	private State referencedState;
	private VOPoint geometry;
	
	private String geometryHref;
	private String referencedStateHref;
	
	public State getReferencedState() {
		return referencedState;
	}
	public void setReferencedState(State referencedState) {
		this.referencedState = referencedState;
	}
	public VOPoint getGeometry() {
		return geometry;
	}
	public void setGeometry(VOPoint geometry) {
		this.geometry = geometry;
	}
	public String getGeometryHref() {
		return geometryHref;
	}
	public void setGeometryHref(String geometry) {
		this.geometryHref = geometry;
	}
	public String getReferencedStateHref() {
		return referencedStateHref;
	}
	public void setReferencedStateHref(String referencedStateHref) {
		this.referencedStateHref = referencedStateHref;
	}

	
}
