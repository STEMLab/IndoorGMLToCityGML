package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.opengis.indoorgml.v_1_0.vo.spatial.VOSolid;
import net.opengis.indoorgml.v_1_0.vo.spatial.VOSurface;

import org.opengis.geometry.primitive.Surface;

public class CellSpace extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public PrimalSpaceFeatures parents;
	public Integer dualityID;
	public Integer partialBoundedByID;
	
	private VOSolid geometry3D;
	private VOSurface geometry2D;
	
	private String hrefDuality;
	private ArrayList<ExternalReference> externalReference;
	private ArrayList<String> hrefPartialBoundedBy;
	
	private State duality;
	private ArrayList<CellSpaceBoundary> partialBoundedBy;
	
	private String navigableType;
	private String usage;
	private String usageCodeSpace;
	private String function;
	private String functionCodeSpace;
	private String clazz;
	private String classCodeSpace;
	
	private List<Surface> facets;
	private Map<Surface, List<CellSpaceBoundary>> facetBoundaryMap;
	private Map<CellSpaceBoundary, List<CellSpace>> boundaryCellSpaceMap;
	
	private String estimatedType;
	// Estimated Type
	// 1. ROOM
	// 2. WALL
	// 3. CORRIDOR
	// 4. STAIR
	// 5. DOOR
	

	public VOSolid getGeometry3D() {
		return geometry3D;
	}
	public void setGeometry3D(VOSolid geometry3d) {
		geometry3D = geometry3d;
	}
	public VOSurface getGeometry2D() {
		return geometry2D;
	}
	public void setGeometry2D(VOSurface geometry2d) {
		geometry2D = geometry2d;
	}
	public String getHrefDuality() {
		return hrefDuality;
	}
	public void setHrefDuality(String duality) {
		this.hrefDuality = duality;
	}
	public ArrayList<ExternalReference> getExternalReference() {
		return externalReference;
	}
	public void setExternalReference(ArrayList<ExternalReference> externalReference2) {
		this.externalReference = externalReference2;
	}
	public ArrayList<String> getHrefPartialBoundedBy() {
		return hrefPartialBoundedBy;
	}
	public void setHrefPartialBoundedBy(ArrayList<String> hrefPartialBoundedBy) {
		this.hrefPartialBoundedBy = hrefPartialBoundedBy;
	}
	public String getNavigableType() {
		return navigableType;
	}
	public void setNavigableType(String navigableType) {
		this.navigableType = navigableType;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getUsageCodeSpace() {
		return usageCodeSpace;
	}
	public void setUsageCodeSpace(String usageCodeSpace) {
		this.usageCodeSpace = usageCodeSpace;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getFunctionCodeSpace() {
		return functionCodeSpace;
	}
	public void setFunctionCodeSpace(String functionCodeSpace) {
		this.functionCodeSpace = functionCodeSpace;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getClassCodeSpace() {
		return classCodeSpace;
	}
	public void setClassCodeSpace(String classCodeSpace) {
		this.classCodeSpace = classCodeSpace;
	}
	/**
	 * @return the duality
	 */
	public State getDuality() {
		return duality;
	}
	/**
	 * @param duality the duality to set
	 */
	public void setDuality(State duality) {
		this.duality = duality;
	}
	/**
	 * @return the partialBoundedBy
	 */
	public ArrayList<CellSpaceBoundary> getPartialBoundedBy() {
		return partialBoundedBy;
	}
	/**
	 * @param partialBoundedBy the partialBoundedBy to set
	 */
	public void setPartialBoundedBy(ArrayList<CellSpaceBoundary> partialBoundedBy) {
		this.partialBoundedBy = partialBoundedBy;
	}
	/**
	 * @return the surfaces
	 */
	public List<Surface> getFacets() {
		return facets;
	}
	/**
	 * @param facets the surfaces to set
	 */
	public void setFacets(List<Surface> facets) {
		this.facets = facets;
	}
	/**
	 * @return the facetBoundaryMap
	 */
	public Map<Surface, List<CellSpaceBoundary>> getFacetBoundaryMap() {
		return facetBoundaryMap;
	}
	/**
	 * @param facetBoundaryMap the facetBoundaryMap to set
	 */
	public void setFacetBoundaryMap(Map<Surface, List<CellSpaceBoundary>> facetBoundaryMap) {
		this.facetBoundaryMap = facetBoundaryMap;
	}
	/**
	 * @return the facetCellMap
	 */
	public Map<CellSpaceBoundary, List<CellSpace>> getBoundaryCellSpaceMap() {
		return boundaryCellSpaceMap;
	}
	/**
	 * @param boundaryCellSpaceMap the facetCellMap to set
	 */
	public void setBoundaryCellSpaceMap(Map<CellSpaceBoundary, List<CellSpace>> boundaryCellSpaceMap) {
		this.boundaryCellSpaceMap = boundaryCellSpaceMap;
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
