package com.btk.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.btk.entity.User;
import com.btk.entity.SendMail;
@Stateless
@LocalBean
public class UserDao {

	@PersistenceContext(unitName = "ProjectEJB")
	EntityManager em;

	public List<User> getData() {

		Query query = em.createQuery("select u from User u");

		return query.getResultList();
	}
	
	public List<User> getUserByData(String data) {

		Query query = em.createQuery("select u from User u where u.uname like :data or u.fname like :data or u.lname like :data or u.role like :data").setParameter("data", "%" + data + "%");
		
		System.out.println("query: "+query);
		
		return query.getResultList();
	}

	public List<User> getUser(String uname) {

		Query query = em.createQuery("select u from User u where u.uname=:uname").setParameter("uname", uname);

		return query.getResultList();
	}

	public List<User> checkLogIn(String uname, String pwd) {

		Query query = em.createQuery("SELECT u FROM User u WHERE u.uname=:uname AND u.pwd=:pwd")
				.setParameter("uname", uname).setParameter("pwd", pwd);
		return query.getResultList();
	}

	public List<User> getUserById(int id) {

		Query query = em.createQuery("select u from User u where u.id=:id").setParameter("id", id);
		return query.getResultList();
	}

	public void add(String uname, String pwd, String fname, String lname, String adress, String role, String tel,
			int token) {
		User user = new User();
		user.setAdress(adress);
		user.setFname(fname);
		user.setLname(lname);
		user.setRole(role);
		user.setTel(tel);
		user.setToken(token);
		user.setUname(uname);
		user.setPwd(pwd);
		em.persist(user);
	}

	public void delete(int id) {
		em.remove(getUserById(id).get(0));
	}

	public void update(User us) {
		em.merge(us);
	}
	
	public void sendmail(String to,String link) {
		SendMail mail=new SendMail();
		mail.send(to,link);
	}
}
