package com.btk.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.btk.entity.Cart;
import com.btk.entity.Commande;
import com.btk.entity.Product;
import com.btk.entity.SendMail;

@Stateless
@LocalBean
public class CommDao {
	@PersistenceContext(unitName = "ProjectEJB")
	EntityManager em;

	public List<Commande> testExist(int id) {

		Query query = em.createQuery("select u from Commande u where u.cartid=:id")
				.setParameter("id", id);

		return query.getResultList();
	}

	public Map<Integer, List<Commande>> getCommandesGroupedByCart() {
		Query query = em.createQuery("SELECT u FROM Commande u ");

		List<Commande> commandes = query.getResultList();
		System.out.println(commandes);
		System.out.println(commandes.stream().collect(Collectors.groupingBy(Commande::getCartid)));
		return commandes.stream().collect(Collectors.groupingBy(Commande::getCartid));
	}
	
	public List<Commande> commandelist() {

		Query query = em.createQuery("select u from Commande u group by cartid");
		return query.getResultList();
	}
	
	public List<Commande> singlecommande(int cartid) {

		Query query = em.createQuery("select u from Commande u where u.cartid=:cartid").setParameter("cartid", cartid);
		return query.getResultList();
	}

	public void add(int prodid, int userid, int cartid,int confirm,int count,float total) {
		Commande commande = new Commande();
		commande.setProdid(prodid);
		commande.setUserid(userid);
		commande.setCartid(cartid);
		commande.setConfirm(confirm);
		commande.setCount(count);
		commande.setTotal(total);
		em.persist(commande);
	}

	public void delete(int id) {
		for (int i = 0; i < testExist(id).size(); i++) {
			em.remove(testExist(id).get(0));
		}
	}
	
	public void update(Commande comm) {
		em.merge(comm);
	}
	

}
