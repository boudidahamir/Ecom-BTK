package com.btk.bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.btk.entity.User;
import com.btk.services.UserDao;

@ManagedBean(name = "fgtPwdBean")
@SessionScoped
public class FgtPwdBean {
	private String uname,pwd,cpwd;
	private User user;
	
	@EJB
	private UserDao userDao;
	
	public String sendrequest() throws IOException
	{
		if(!userDao.getUser(uname).isEmpty())
		{	
			
			String fileName="C:\\Users\\amirb\\eclipse-workspace\\ProjectWeb\\WebContent\\pwdupdateEMAIL.txt";
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
			user=userDao.getUser(uname).get(0);
			user.setToken(1);
			userDao.update(user);
			userDao.sendmail(uname,text);
			return "login.xhtml?faces-redirect=true";
		}
		else if(userDao.getUser(uname).isEmpty())
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "no account with this email", "no account with this email"));
		}
		return "";
	}
	
	public String updatePwd() {
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
		user.setToken(0);
		user.setPwd(pwd);
		userDao.update(this.user);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login.xhtml?faces-redirect=true";
	}
		
		return "";
	}
	
	public void secnewPwd() {

			if(user==null)
			{
				FacesContext context = FacesContext.getCurrentInstance();
				context.getApplication().getNavigationHandler().handleNavigation(context, null,
						"login.xhtml?faces-redirect=true");
			}
				else if(this.user.getToken()==1)
			{
				pwd="";
				cpwd="";
			}
			
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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

	public String getCpwd() {
		return cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
