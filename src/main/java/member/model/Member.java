package member.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.google.gson.annotations.JsonAdapter;

public class Member implements Serializable{
	private static final long serialVersionUID =1L;
	private Integer id;
	private String account;
	private String password;
	private String name;
	private String gender;
	private String username;
	private String phone;
	
	private Date birthday;
	private Timestamp reg_date;
	private String address;
	private String intro;
	private byte[] img;
	private String imgBase64Str;
	private byte[] banner_img;
	private String bannerImgBase64Str;
	private String accessnum;
	
	public Member() {
	}
	
	
	public Member(Integer id, String account, String password, String name, String gender, String username,
			String phone, Date birthday, Timestamp reg_date, String address, String intro, byte[] img,
			String imgBase64Str, byte[] banner_img, String bannerImgBase64Str, String accseenum) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.username = username;
		this.phone = phone;
		this.birthday = birthday;
		this.reg_date = reg_date;
		this.address = address;
		this.intro = intro;
		this.img = img;
		this.imgBase64Str = imgBase64Str;
		this.banner_img = banner_img;
		this.bannerImgBase64Str = bannerImgBase64Str;
		this.accessnum = accseenum;
	}


	public String getImgBase64Str() {
		return imgBase64Str;
	}


	public void setImgBase64Str(String imgBase64Str) {
		this.imgBase64Str = imgBase64Str;
	}


	public String getBannerImgBase64Str() {
		return bannerImgBase64Str;
	}


	public void setBannerImgBase64Str(String bannerImgBase64Str) {
		this.bannerImgBase64Str = bannerImgBase64Str;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public byte[] getBanner_img() {
		return banner_img;
	}
	public void setBanner_img(byte[] banner_img) {
		this.banner_img = banner_img;
	}
	public String getAccessnum() {
		return accessnum;
	}
	public void setAccessnum(String accessnum) {
		this.accessnum = accessnum;
	}
	
		
}
