package com.btk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Carts")
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userid;
	private int prodid;
	private int count;
	
	
	
	public Cart() {
		super();
	}

	public Cart(int id, int userid, int prodid, int count) {
		super();
		this.id = id;
		this.userid = userid;
		this.prodid = prodid;
		this.count = count;
	}

	public Cart(int userid, int prodid, int count) {
		super();
		this.userid = userid;
		this.prodid = prodid;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getProdid() {
		return prodid;
	}

	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", userid=" + userid + ", prodid=" + prodid + ", count=" + count + "]";
	}

	
}
