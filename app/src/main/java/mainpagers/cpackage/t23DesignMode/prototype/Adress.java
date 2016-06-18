package mainpagers.cpackage.t23DesignMode.prototype;

/**
 * Created by LiuShao on 2016/6/13.
 * 用户地址类，存储地址的详细信息
 */
public class Adress {
    //城市
    public String city;
    //区域
    public String district;
    //街道
    public String street;

    public Adress(String city, String district, String street) {
        this.city = city;
        this.district = district;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
