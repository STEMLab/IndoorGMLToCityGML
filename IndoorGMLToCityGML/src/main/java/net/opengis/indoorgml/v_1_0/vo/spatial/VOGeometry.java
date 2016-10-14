/**
 * 
 */
package net.opengis.indoorgml.v_1_0.vo.spatial;

/**
 * @author hgryoo
 *
 */
public class VOGeometry {
	
	private Integer id;
	private String gmlId;
	private String geometryType;
	private String srsName;
	private Integer srsDimension;
	private boolean isXLink;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the gmlId
	 */
	public String getGmlId() {
		return gmlId;
	}
	/**
	 * @param gmlId the gmlId to set
	 */
	public void setGmlId(String gmlId) {
		this.gmlId = gmlId;
	}
	/**
	 * @return the geometryType
	 */
	public String getGeometryType() {
		return geometryType;
	}
	/**
	 * @param geometryType the geometryType to set
	 */
	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}
	
	/**
	 * @return the srsName
	 */
	public String getSrsName() {
		return srsName;
	}
	/**
	 * @param srsName the srsName to set
	 */
	public void setSrsName(String srsName) {
		this.srsName = srsName;
	}
	/**
	 * @return the srsDimension
	 */
	public Integer getSrsDimension() {
		return srsDimension;
	}
	/**
	 * @param srsDimension the srsDimension to set
	 */
	public void setSrsDimension(Integer srsDimension) {
		this.srsDimension = srsDimension;
	}
	/**
	 * @return the isXLink
	 */
	public boolean getIsXLink() {
		return isXLink;
	}
	/**
	 * @param isXLink the isXLink to set
	 */
	public void setIsXLink(boolean isXLink) {
		this.isXLink = isXLink;
	}
	
}
