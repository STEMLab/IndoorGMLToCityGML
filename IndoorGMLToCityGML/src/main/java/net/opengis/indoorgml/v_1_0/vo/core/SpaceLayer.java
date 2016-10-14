package net.opengis.indoorgml.v_1_0.vo.core;

import java.util.ArrayList;
import java.util.Date;

public class SpaceLayer extends IndoorObject {
	public ArrayList<IndoorObject> indoorObject = new ArrayList<IndoorObject>();
	public SpaceLayers parents;
	
	private String usage;
	private String usageCodeSpace;
	private Date teminationDate;
	private String function;
	private String functionCodeSpace;
	private Date creationDate;
	private String clazz;
	private String classCodeSpace;
	
	private ArrayList<Nodes> nodes;
	private ArrayList<Edges> edges;
	
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
	public Date getTeminationDate() {
		return teminationDate;
	}
	public void setTeminationDate(Date teminationDate) {
		this.teminationDate = teminationDate;
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public ArrayList<Nodes> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Nodes> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<Edges> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Edges> edges) {
		this.edges = edges;
	}
}
