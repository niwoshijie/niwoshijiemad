/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 */

package mainpagers.cpackage.selfserviceapp.beans;
/**
 * @author 
 * @version 1.0
 * @since 1.0
 * 自助机模板类
 */
 
public class BusSelfModel {
	
	//columns START
	private Integer id;
	private String modelName;//模板名称
	private Integer printId;//小票打印样式id
	private Integer isDefault;//是否为默认  0否，1是 只能有一个默认模板
	private Integer playTime;//图片播放间隔
	private String logo;
	//columns END
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getPrintId() {
		return printId;
	}
	public void setPrintId(Integer printId) {
		this.printId = printId;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getPlayTime() {
		return playTime;
	}
	public void setPlayTime(Integer playTime) {
		this.playTime = playTime;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	
}

