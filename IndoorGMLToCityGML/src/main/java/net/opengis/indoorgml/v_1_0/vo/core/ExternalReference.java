package net.opengis.indoorgml.v_1_0.vo.core;

public class ExternalReference {
	public Object parents;
	
	private String informationSystem;
	private String name;
	private String uri;
	
	public String getInformationSystem() {
		return informationSystem;
	}
	public void setInformationSystem(String informationSystem) {
		this.informationSystem = informationSystem;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
