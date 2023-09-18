package com.btk.services;


import java.sql.Blob;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.btk.entity.Product;


@Stateless
@LocalBean
public class ProductDao {

	@PersistenceContext(unitName = "ProjectEJB")
	EntityManager em;

	public List<Product> getData() {
		
		Query query=em.createQuery("select u from Product u");
		
		return query.getResultList();
	}
	
public List<Product> getData0() {
		
		Query query=em.createQuery("select u from Product u where u.unit > 0 ");
		
		return query.getResultList();
	}
	
	public List<Product> getProdByData(String data) {

		Query query = em.createQuery("select u from Product u where u.name like :data and u.unit>0").setParameter("data", "%" + data + "%");
		
		System.out.println("query: "+query);
		
		return query.getResultList();
	}
	
	
	public List<Product> getProductById(int id) {
		
		Query query=em.createQuery("select u from Product u where u.id=:id").setParameter("id", id);
		return query.getResultList();
	}
	
	public void add(String name, String description, Blob image, 
			int unit,int sold) {
		Product prod = new Product();
		prod.setName(name);
		prod.setDescription(description);
		prod.setUnit(unit);
		prod.setImage(image);
		prod.setSold(sold);
		em.persist(prod);
	}
	
	
	public void delete(int id) {
		em.remove(getProductById(id).get(0));
	}

	public void update(Product prod) {
		em.merge(prod);
	}
	
	public Blob getImageDataById(int id) {
	    Product product = em.find(Product.class, id);
	    if (product != null) {
	        return product.getImage();
	    }
	    return null;
	}
	
}
