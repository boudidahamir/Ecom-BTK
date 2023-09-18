package com.btk.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.btk.entity.Cart;
import com.btk.entity.Product;

@Stateless
@LocalBean
public class CartDao {
	@PersistenceContext(unitName = "ProjectEJB")
	EntityManager em;

	public List<Cart> getData(int id) {
		
		Query query=em.createQuery("select u from Cart u where u.userid=:id").setParameter("id", id);
		
		return query.getResultList();
	}
	
public List<Cart> testExist(int userid,int prodid) {
		
		Query query=em.createQuery("select u from Cart u where u.userid=:userid AND u.prodid=:prodid").setParameter("userid", userid).setParameter("prodid", prodid);
		
		return query.getResultList();
	}
	
	public void add( int prodid,int userid,int count) {
		Cart cart = new Cart();
		cart.setProdid(prodid);
		cart.setUserid(userid);
		cart.setCount(count);
		em.persist(cart);
	}
	
	
	public void delete(int id) {
		for (int i = 0; i < getData(id).size(); i++) {
			em.remove(getData(id).get(i));
       }
		
	}
	
	public void delete2(int userid,int prodid) {
		em.remove(testExist(userid, prodid).get(0));
	}
	
	public void update(Cart cart) {
		em.merge(cart);
	}

}
