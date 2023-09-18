package com.btk.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.btk.entity.User;
import com.btk.services.UserDao;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	private User user;
	private int id;
	private String uname;
	private String pwd;
	private String fname;
	private String lname;
	private String adress;
	private String role;
	private String tel;
	private int token;
	private String cpwd;
	private String search;
	@EJB
	private UserDao userDao;
	

	public List<User> testGetData() {
		System.out.println("search: "+search);
		if(search==null || search.equals("") )
		{
			List<User> lisUsers = userDao.getData();
			System.out.println("if: "+lisUsers);
			return lisUsers;
			
		}
		else if(search!=null && !search.equals("") )
		{
			List<User> lisUsers = userDao.getUserByData(search);
			System.out.println("else: "+lisUsers);
			return lisUsers;
		}
		
		
		
		return null;
	}

	public String deleteuser(int id) {
		userDao.delete(id);
		return "";
	}

	public String deleteProfile(int id) {
		deleteuser(id);
		return logout();
	}

	public String updateuser(User us) {
		this.user = us;
		return "update.xhtml?faces-redirect=true";
	}

	public String updateuser1() {
		this.userDao.update(this.user);
		if (this.user.getRole().equals("client")) {
			return "profile.xhtml?faces-redirect=true";
		} else if (this.user.getRole().equals("admin")) {
			return "profileBack.xhtml?faces-redirect=true";
		}
		return "";
	}
	
	public String updatePwd() throws IOException {
		String fileName="pwdupdateEmail.txt";
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
		this.user.setToken(1);
		this.userDao.update(this.user);
		this.userDao.sendmail(this.user.getUname(),text);
		if (this.user.getRole().equals("client")) {
			return "profile.xhtml?faces-redirect=true";
		} else if (this.user.getRole().equals("admin")) {
			return "profileBack.xhtml?faces-redirect=true";
		}
		return "";
	}
	
	public String updatePwd1() {
		FacesContext context = FacesContext.getCurrentInstance();
		Pattern pwdpattern = Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		Matcher pwdmatch = pwdpattern.matcher(pwd);
		if (pwd.isEmpty()) {
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
	}else {
		this.user.setToken(0);
		this.user.setPwd(pwd);
		this.userDao.update(this.user);
		if (this.user.getRole().equals("client")) {
			return "profile.xhtml?faces-redirect=true";
		} else if (this.user.getRole().equals("admin")) {
			return "profileBack.xhtml?faces-redirect=true";
		}
	}
		
		
		return "";
	}
	
	public void secupdatePwd() {
		if (user != null) {

			if(this.user.getToken()==0)
			{
				if (this.user.getRole().equals("client")) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.getApplication().getNavigationHandler().handleNavigation(context, null,
							"profile.xhtml?faces-redirect=true");
				} else if (this.user.getRole().equals("admin")) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.getApplication().getNavigationHandler().handleNavigation(context, null,
							"profileBack.xhtml?faces-redirect=true");
				}
			}else if(this.user.getToken()==1)
			{
				pwd="";
				cpwd="";
			}
			
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"login.xhtml?faces-redirect=true");
		}
	}
	
	public void Security() {
		if (user != null) {

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String uri = request.getRequestURI();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					uri + "?faces-redirect=true");
		} else if(user == null || user.getUname().isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"login.xhtml?faces-redirect=true");
		}
	}
	
	public void testclient() {
		System.out.println(user.getRole());
		if (user.getRole().equals("client")) {

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String uri = request.getRequestURI();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					uri + "?faces-redirect=true");
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"listproductAdmin.xhtml?faces-redirect=true");
		}
	}
	
	public void testadmin() {
		if (user.getRole().equals("admin")) {

			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String uri = request.getRequestURI();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					uri + "?faces-redirect=true");
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"listproduct.xhtml?faces-redirect=true");
		}
	}

	public String updateGoBack() {
		if (this.user.getRole().equals("client")) {
			return "profile.xhtml?faces-redirect=true";
		} else if (this.user.getRole().equals("admin")) {
			return "profileBack.xhtml?faces-redirect=true";
		}
		return "";
	}

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (uname.isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter username", "please enter username"));
		} else if (pwd.isEmpty() == true) {

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "please enter password", "please enter password"));
		} else if (userDao.checkLogIn(uname, pwd).isEmpty() == true) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "User dosent exists", "User dosent exists"));
		} else if (pwd.isEmpty() == false && uname.isEmpty() == false
				&& userDao.checkLogIn(uname, pwd).isEmpty() == false) {
			User us = userDao.checkLogIn(uname, pwd).get(0);
			user = us;
			uname = us.getUname();
			fname = us.getFname();
			lname = us.getLname();
			adress = us.getAdress();
			id = us.getId();
			tel = us.getTel();
			token = us.getToken();
			pwd = us.getPwd();
			role = us.getRole();
			if (us.getRole().equals("client")) {
				return "profile.xhtml?faces-redirect=true";
			} else if (us.getRole().equals("admin")) {
				return "profileBack.xhtml?faces-redirect=true";
			}
		}
		return "none";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}	
}
