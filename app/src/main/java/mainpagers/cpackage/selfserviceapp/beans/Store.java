package mainpagers.cpackage.selfserviceapp.beans;

public class Store {
	
	public static Integer ORDER_STORE = 1;
	public static Integer BUSSELF_STORE = 2; 
	
	private int id;
	private int userId;//用户id
	private String headShop;//总店名称
	private String branchShop;//分店名称
	private String shopAdress;//店铺地址
	private String shopTel;//联系电话
	private String linkMan;//联系人
	private double consumeAvg;//人均价格
	private int baseId;//叫号服务设置id
	private String product;//服务
	private String weiNum;//叫号服务设置名称
	private String weiName;//公众号
	private String weiNameId;//被关注的微信openId
	private String shopImg;//店铺图片
	private int merchantId;//商户id
	private String shopTime;//店铺营业时间
	private int status;//店铺标识排队叫号  1叫号，2自助
	private int modelId ; //店铺ID
	private int menuId ; //类目ID
	
	
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getShopTime() {
		return shopTime;
	}
	public void setShopTime(String shopTime) {
		this.shopTime = shopTime;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public String getWeiNameId() {
		return weiNameId;
	}
	public void setWeiNameId(String weiNameId) {
		this.weiNameId = weiNameId;
	}
	public String getWeiName() {
		return weiName;
	}
	public void setWeiName(String weiName) {
		this.weiName = weiName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	public int getBaseId() {
		return baseId;
	}
	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}
	public String getWeiNum() {
		return weiNum;
	}
	public void setWeiNum(String weiNum) {
		this.weiNum = weiNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getHeadShop() {
		return headShop;
	}
	public void setHeadShop(String headShop) {
		this.headShop = headShop;
	}
	public String getBranchShop() {
		return branchShop;
	}
	public void setBranchShop(String branchShop) {
		this.branchShop = branchShop;
	}
	public String getShopAdress() {
		return shopAdress;
	}
	public void setShopAdress(String shopAdress) {
		this.shopAdress = shopAdress;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public double getConsumeAvg() {
		return consumeAvg;
	}
	public void setConsumeAvg(double consumeAvg) {
		this.consumeAvg = consumeAvg;
	}
	
}
