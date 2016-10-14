package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;

import net.opengis.indoorgml.v_1_0.vo.spatial.VOCurve;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSurface;

public class CellSpaceBoundary extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public PrimalSpaceFeatures parents;
	public Integer dualityID;
	
	private VOSurface geometry3D;
	private VOCurve geometry2D;
	
	private String hrefDuality;
	private ArrayList<ExternalReference> externalReference;
	private String navigableType;
	
	private Transition duality;
	
	private String estimatedType;
	// Estimated Type
	// 1. CONNECTION : DOOR, VIRTUALWALL
	// 2. WALL
	
	public VOSurface getGeometry3D() {
		return geometry3D;
	}
	public void setGeometry3D(VOSurface geometry3d) {
		geometry3D = geometry3d;
	}
	public VOCurve getGeometry2D() {
		return geometry2D;
	}
	public void setGeometry2D(VOCurve geometry2d) {
		geometry2D = geometry2d;
	}
	public String getHrefDuality() {
		return hrefDuality;
	}
	public void setHrefDuality(String hrefDuality) {
		this.hrefDuality = hrefDuality;
	}
	public String getNavigableType() {
		return navigableType;
	}
	public void setNavigableType(String navigableType) {
		this.navigableType = navigableType;
	}
	public ArrayList<ExternalReference> getExternalReference() {
		return externalReference;
	}
	public void setExternalReference(ArrayList<ExternalReference> externalReferenceList) {
		this.externalReference = externalReferenceList;
	}
	/**
	 * @return the duality
	 */
	public Transition getDuality() {
		return duality;
	}
	/**
	 * @param duality the duality to set
	 */
	public void setDuality(Transition duality) {
		this.duality = duality;
	}
	/**
	 * @return the esimatedType
	 */
	public String getEstimatedType() {
		return estimatedType;
	}
	/**
	 * @param esimatedType the esimatedType to set
	 */
	public void setEstimatedType(String esimatedType) {
		this.estimatedType = esimatedType;
	}
	
}
