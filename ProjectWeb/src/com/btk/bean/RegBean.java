package com.btk.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.btk.services.UserDao;

@ManagedBean(name = "regBean")
@RequestScoped
public class RegBean {

	@EJB
	private UserDao userDao;

	private int id;
	private String uname;
	private String pwd;
	private String cpwd;
	private String fname;
	private String lname;
	private String adress;
	private String role;
	private String tel;
	private int token;

	public String register() {
		FacesContext context = FacesContext.getCurrentInstance();
		Pattern telpattern = Pattern.compile("\\d{8}");
		Matcher telmatch = telpattern.matcher(tel);
		Pattern emailpattern = Pattern.compile("^(.+)@(.+)$");
		Matcher emailmatch = emailpattern.matcher(uname);
		Pattern pwdpattern = Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		Matcher pwdmatch = pwdpattern.matcher(pwd);

		if (fname.isEmpty() == true) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter First name",
					"please enter First name"));
		} else if (lname.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter Last name", "please enter Last name"));
		} else if (adress.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter adress", "please enter adress"));
		} else if (tel.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter telephone", "please enter telephone"));
		} else if (tel.length() < 8) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimum length of telephone is 8",
					"Minimum length of telephone is 8"));
		} else if (!telmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid telephone",
					"please enter a valid telephone"));
		} else if (uname.isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter email", "please enter email"));
		} else if (!emailmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid email",
					"please enter a valid email"));
		} else if (pwd.isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter password", "please enter password"));
		} else if (pwd.length() < 8) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "password length > 8", "password length > 8"));
		} else if (!pwdmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid password",
					"please enter a valid password"));
		} else if (cpwd.isEmpty() == true) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter confirm password",
					"please enter confirm password"));
		} else if (pwd.equals(cpwd) == false) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "password dosent match", "password dosent match"));
		} else if (userDao.getUser(uname).isEmpty() == false) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User exists", "User exists"));
		} else if (pwd.isEmpty() == false && uname.isEmpty() == false) {
			userDao.add(uname, pwd, fname, lname, adress, "client", tel, 0);
			context.addMessage(null, new FacesMessage("Done!!"));
			return "login.xhtml?faces-redirect=true";
		}
		return "none";
	}
	
	public String addAdmin() {
		FacesContext context = FacesContext.getCurrentInstance();
		Pattern telpattern = Pattern.compile("\\d{8}");
		Matcher telmatch = telpattern.matcher(tel);
		Pattern emailpattern = Pattern.compile("^(.+)@(.+)$");
		Matcher emailmatch = emailpattern.matcher(uname);
		Pattern pwdpattern = Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		Matcher pwdmatch = pwdpattern.matcher(pwd);

		if (fname.isEmpty() == true) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter First name",
					"please enter First name"));
		} else if (lname.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter Last name", "please enter Last name"));
		} else if (adress.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter adress", "please enter adress"));
		} else if (tel.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter telephone", "please enter telephone"));
		} else if (tel.length() < 8) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minimum length of telephone is 8",
					"Minimum length of telephone is 8"));
		} else if (!telmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid telephone",
					"please enter a valid telephone"));
		} else if (uname.isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter email", "please enter email"));
		} else if (!emailmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid email",
					"please enter a valid email"));
		} else if (pwd.isEmpty()) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter password", "please enter password"));
		} else if (pwd.length() < 8) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "password length > 8", "password length > 8"));
		} else if (!pwdmatch.matches()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter a valid password",
					"please enter a valid password"));
		} else if (cpwd.isEmpty() == true) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter confirm password",
					"please enter confirm password"));
		} else if (pwd.equals(cpwd) == false) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "password dosent match", "password dosent match"));
		} else if (userDao.getUser(uname).isEmpty() == false) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User exists", "User exists"));
		} else if (pwd.isEmpty() == false && uname.isEmpty() == false) {
			userDao.add(uname, pwd, fname, lname, adress, "admin", tel, 0);
			context.addMessage(null, new FacesMessage("Done!!"));
			return "list.xhtml?faces-redirect=true";
		}
		return "none";
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

}
