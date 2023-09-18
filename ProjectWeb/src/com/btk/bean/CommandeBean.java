package com.btk.bean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.btk.entity.Cart;
import com.btk.entity.Commande;
import com.btk.entity.Product;
import com.btk.entity.User;
import com.btk.services.CartDao;
import com.btk.services.CommDao;
import com.btk.services.ProductDao;
import com.btk.services.UserDao;

@ManagedBean(name = "commBean")
@ViewScoped
public class CommandeBean {
	private int id, prodid, userid, cartid;
	private Cart cart;
	private Product prod;
	private Commande comm;
	private int confirm;
	private int count;
	private float total;

	@EJB
	private ProductDao prodDao;
	@EJB
	private CartDao cartDao;
	@EJB
	private CommDao commDao;
	@EJB
	private UserDao userDao;
	

	public Map<Integer, List<Commande>> getGroupedCommandes() {
		return commDao.getCommandesGroupedByCart();
	}
	
	public List<Commande> commandeslist() {
		return commDao.commandelist();
	}

	public List<Commande> singlecommandes(int cartid) {
		return commDao.singlecommande(cartid);
	}
	
	public List<User> getuser(int userid) {
		return userDao.getUserById(userid) ;
	}
	
	public String deleteCommande(int id) {
		commDao.delete(id);
		commDao.delete(id);
		return "listcommande.xhtml";
	}
	
	public String updateCommande(int cartid) throws IOException {
		List<Commande> list=commDao.singlecommande(cartid);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setConfirm(1);
			commDao.update(list.get(i));
		}
		
		String fileName="C:\\Users\\amirb\\eclipse-workspace\\ProjectWeb\\WebContent\\orderEMAIL.txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
		
		String text = stringBuilder.toString();
		userDao.sendmail(userDao.getUserById(list.get(0).getUserid()).get(0).getUname(),text);
		return "listcommande.xhtml?faces-redirect=true";
	}

	public String addCommande(int userid, int cartid) {
		List<Cart> cart = cartDao.getData(userid);
		List<Product> prod = new ArrayList<>();
		float total=0;
		for (int i = 0; i < cart.size(); i++) {
			prod.add(prodDao.getProductById(cart.get(i).getProdid()).get(0));
			total=total+ ( cart.get(i).getCount() * prodDao.getProductById(cart.get(i).getProdid()).get(0).getPrice() );
		}
		
		for (int i = 0; i < prod.size(); i++) {
			commDao.add(prod.get(i).getId(), userid, cartid, 0,cart.get(i).getCount(),total);
			prod.get(i).setUnit(prod.get(i).getUnit()-cart.get(i).getCount());
			prod.get(i).setSold(prod.get(i).getSold() + cart.get(i).getCount());
			prodDao.update(prod.get(i));
		}
		
		cartDao.delete(userid);
		cartDao.delete(userid);
		return "listproduct.xhtml?faces-redirect=true";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProdid() {
		return prodid;
	}

	public void setProdid(int prodid) {
		this.prodid = prodid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getCartid() {
		return cartid;
	}

	public void setCartid(int cartid) {
		this.cartid = cartid;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProd() {
		return prod;
	}

	public void setProd(Product prod) {
		this.prod = prod;
	}

	public Commande getComm() {
		return comm;
	}

	public void setComm(Commande comm) {
		this.comm = comm;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ProductDao getProdDao() {
		return prodDao;
	}

	public void setProdDao(ProductDao prodDao) {
		prodDao = prodDao;
	}

	public CartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public CommDao getCommDao() {
		return commDao;
	}

	public void setCommDao(CommDao commDao) {
		this.commDao = commDao;
	}

}
