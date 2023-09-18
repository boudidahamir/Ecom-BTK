package com.btk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	/**
 * 
 */
private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
	private int id;
    
    private String fname;
    private String lname;
    private String adress;
    private String role;
    private String tel;
    private int token;
	private String uname;
	private String pwd;
	
	public User() {
	}

	public User(int id, String fname, String lname, String adress, String role, String tel, int token, String uname,
			String pwd) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.adress = adress;
		this.role = role;
		this.tel = tel;
		this.token = token;
		this.uname = uname;
		this.pwd = pwd;
	}

	public User(String fname, String lname, String adress, String role, String tel, int token, String uname,
			String pwd) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.adress = adress;
		this.role = role;
		this.tel = tel;
		this.token = token;
		this.uname = uname;
		this.pwd = pwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fname=" + fname + ", lname=" + lname + ", adress=" + adress + ", role=" + role
				+ ", tel=" + tel + ", token=" + token + ", uname=" + uname + ", pwd=" + pwd + "]";
	}

}
