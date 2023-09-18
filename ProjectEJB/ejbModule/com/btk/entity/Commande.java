package com.btk.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Commandes")
public class Commande implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int userid;
	private int prodid;
	private int cartid;
	private int confirm;
	private int count;
	private float total;

	public Commande() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Commande(int id, int userid, int prodid, int cartid) {
		super();
		this.id = id;
		this.userid = userid;
		this.prodid = prodid;
		this.cartid = cartid;
	}

	public Commande(int userid, int prodid, int cartid) {
		super();
		this.userid = userid;
		this.prodid = prodid;
		this.cartid = cartid;
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

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public int getCartid() {
		return cartid;
	}

	public void setCartid(int cartid) {
		this.cartid = cartid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Commande [id=" + id + ", userid=" + userid + ", prodid=" + prodid + ", cartid=" + cartid + ", confirm="
				+ confirm + ", count=" + count + "]";
	}

}
