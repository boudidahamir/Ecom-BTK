package com.btk.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.btk.entity.Cart;
import com.btk.entity.Product;
import com.btk.services.CartDao;
import com.btk.services.ProductDao;


@ManagedBean(name = "cartBean")
@ViewScoped
public class CartBean {
	private int id,prodid,userid,count;
	private Cart cart;
	private Product prod;
	
	@EJB
	private ProductDao ProdDao;
	@EJB
	private CartDao cartDao;
	
	public List<Product> testGetDataProd(int userid) {
		
			List<Cart> listCart = cartDao.getData(userid);
			List<Product> listProd = new ArrayList<Product>();
			for (int i = 0; i < listCart.size(); i++) {
				 listProd.add(ProdDao.getProductById(listCart.get(i).getProdid()).get(0));
	        }
			
			System.out.println("else: "+listProd);
			return listProd;
	}
	
	public List<Cart> testGetDataCart(int userid) {
		
		List<Cart> listCart = cartDao.getData(userid);
		return listCart;
	}
	
	public String deletecart(int id) {
		cartDao.delete(id);
		cartDao.delete(id);
		return "listproduct.xhtml?faces-redirect=true";
	}
	
	public List<Pair<Cart,Product>> combineLists(List<Cart> cart, List<Product> prod) {
	    List<Pair<Cart,Product>> combinedList = new ArrayList<>();
	    
	    int maxSize = Math.max(cart.size(), prod.size());
	    for (int i = 0; i < maxSize; i++) {
	        Cart obj1 = i < cart.size() ? cart.get(i) : null;
	        Product obj2 = i < prod.size() ? prod.get(i) : null;
	        combinedList.add(new Pair<Cart, Product>(obj1, obj2));
	    }
	    
	    return combinedList;
	}
	
	public void addtocart(int userid,int prodid)
	{
		if(cartDao.testExist(userid, prodid).isEmpty())
		{
			cartDao.add(prodid, userid, 1);
		}else if(!cartDao.testExist(userid, prodid).isEmpty())
		{
			Cart cart=new Cart();
			cart=cartDao.testExist(userid, prodid).get(0);
			cart.setCount(cart.getCount()+1);
			cartDao.update(cart);
		}
	}
	
	public void substructfromcart(int userid,int prodid)
	{
		if(!cartDao.testExist(userid, prodid).isEmpty())
		{
			Cart cart=new Cart();
			cart=cartDao.testExist(userid, prodid).get(0);
			cart.setCount(cart.getCount()-1);
			System.out.println("aaaaa "+cartDao.testExist(userid, prodid).get(0));
			if(cartDao.testExist(userid, prodid).get(0).getCount()==1)
			{
				
				cartDao.delete2(userid, prodid);
			}else if(cartDao.testExist(userid, prodid).get(0).getCount()>1)
			{
				cartDao.update(cart);
			}
			
		}
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public ProductDao getProdDao() {
		return ProdDao;
	}

	public void setProdDao(ProductDao prodDao) {
		ProdDao = prodDao;
	}

	public CartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}
	
	
}
