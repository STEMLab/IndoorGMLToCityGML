package net.opengis.indoorgml.v_1_0.vo.core;

public class IndoorObject {
	private Integer id;
	private String indoorObjectType;
	private String gmlID;
	private String name;
	private String nameCodeSpace;
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIndoorObjectType() {
		return indoorObjectType;
	}
	public void setIndoorObjectType(String indoorObjectType) {
		this.indoorObjectType = indoorObjectType;
	}
	public String getGmlID() {
		return gmlID;
	}
	public void setGmlID(String gmlID) {
		this.gmlID = gmlID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameCodeSpace() {
		return nameCodeSpace;
	}
	public void setNameCodeSpace(String nameCodeSpace) {
		this.nameCodeSpace = nameCodeSpace;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
