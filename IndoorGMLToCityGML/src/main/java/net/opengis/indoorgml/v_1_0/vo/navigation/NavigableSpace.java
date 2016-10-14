package net.opengis.indoorgml.v_1_0.vo.navigation;

public class NavigableSpace {
	public Object parents;
	
	private String usage;
	private String usageCodeSpace;
	private String function;
	private String functionCodeSpace;
	private String clazz;
	private String classCodeSpace;
	
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
}
