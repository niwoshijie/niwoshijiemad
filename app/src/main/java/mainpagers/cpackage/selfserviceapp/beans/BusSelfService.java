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
 */
 
public class BusSelfService {
	
	//columns START
	private Integer id;
	private Integer deviceId;//设备id
	private Integer storeId;//店铺id
	private Integer isPrint;//是否打印票
	private Integer isSound;//是否支持语音
	private Integer isOfflinePay;//是否离线支付
	private Integer isOnlinePay;//是否在线支付
	private Integer eatIn;//在店吃
	private Integer packaging;//打包带走
	private String closeTime ;//机器工作时间
	
	//columns END
	
	public Integer getDeviceId() {
		return deviceId;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getIsSound() {
		return isSound;
	}
	public void setIsSound(Integer isSound) {
		this.isSound = isSound;
	}
	public Integer getIsOfflinePay() {
		return isOfflinePay;
	}
	public void setIsOfflinePay(Integer isOfflinePay) {
		this.isOfflinePay = isOfflinePay;
	}
	public Integer getIsOnlinePay() {
		return isOnlinePay;
	}
	public void setIsOnlinePay(Integer isOnlinePay) {
		this.isOnlinePay = isOnlinePay;
	}
	public Integer getEatIn() {
		return eatIn;
	}
	public void setEatIn(Integer eatIn) {
		this.eatIn = eatIn;
	}
	public Integer getPackaging() {
		return packaging;
	}
	public void setPackaging(Integer packaging) {
		this.packaging = packaging;
	}


}

