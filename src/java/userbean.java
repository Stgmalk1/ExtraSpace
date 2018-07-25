/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 *
 * @author xhunter
 */
@Named(value = "userbean")
@SessionScoped
public class userbean implements Serializable {
    private String username;
    private String password;
    private String repassword;
    private String secans;
    private int sectype;
    private Part uploadedFile;
    private ArrayList<String> list = new ArrayList<String>();
    private String path = System.getProperty("user.dir");
    private String filename;
   
    /**
     * Creates a new instance of userbean
     */
    public userbean() {
        username = "";
        password = "";
        repassword = "";
        secans = "";
        sectype = 1;
        
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the repassword
     */
    public String getRepassword() {
        return repassword;
    }

    /**
     * @param repassword the repassword to set
     */
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    /**
     * @return the secans
     */
    public String getSecans() {
        return secans;
    }

    /**
     * @param secans the secans to set
     */
    public void setSecans(String secans) {
        this.secans = secans;
    }

    /**
     * @return the sectype
     */
    public int getSectype() {
        return sectype;
    }

    /**
     * @param sectype the sectype to set
     */
    public void setSectype(int sectype) {
        this.sectype = sectype;
    }

    public String validateUsernamePassword() {
		boolean valid = Login.validate(username, password);
                String sessionusername = null;
		if (valid) {
			HttpSession session = SessionUtils.getSession();
                        if(SessionEntry.check(username)){
                            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"User has already logged in via another access. Please logout first.",
							"Please logout out first."));
                        logout();
			return "index";
                        }
                        else{
			session.setAttribute("username", username);
                        boolean insert = SessionEntry.insert(username);
                        listfilename();
			return "home";}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			return "index";
		}
	}

    //logout event, invalidate session
    public String logout() {
            HttpSession session = SessionUtils.getSession();
            session.invalidate();
            SessionEntry.delete(username);
            return "index";
	}
    public String register(){
        boolean doesExist = Register.checkExist(username);
        if(doesExist){
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Username already exists",
							"Please enter different username"));
			return "register";
        }
        else{
           int uid = Register.countUID();
           uid = uid + 1;
           System.out.print(uid);
           System.out.print(sectype);
           String strsectype = Integer.toString(sectype);
           Register.insert(uid, username, password, strsectype, secans);
           new File(path+'/'+username).mkdir();
        }
        return "index";
    }
    
    public String forgot(){
        String strsectype = Integer.toString(sectype);
        System.out.print(strsectype);
        System.out.print(secans);
        boolean valid = Forgot.validateUsernameSecans(username, strsectype, secans);
        if(valid){
           HttpSession session = SessionUtils.getSession();
	   session.setAttribute("username", username);
           return "reset";
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Either wrond username or security",
							"Please enter correct information"));
			
        }
        return "forgot";
    }
    public String reset(){
        if(password.equals(repassword)){
        Reset.resetPassword(username, password);
        logout();
        }
        else{
        FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Password mismatch",
                                                    "Please enter correct information"));
        }
        return "index";
    }
    public String add()
    {
        return "add";
    }

    /**
     * @return the uploadedFile
     */
    public Part getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public String saveFile(){
    try (InputStream input = uploadedFile.getInputStream()) {
    String fileName = uploadedFile.getSubmittedFileName();
            Files.copy(input, new File(path+'/'+username, fileName).toPath());
            listfilename();
            return "home";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }
    public void listfilename(){
        String dirPath = path+'/'+username;
        list.removeAll(list);
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files.length == 0) {
            list.add("No photos uploaded");
        } else {
            for (File aFile : files) {
            list.add(aFile.getName() + " - " + aFile.length() +"Kb");
            }
        }
    }

    /**
     * @return the list
     */
    public ArrayList<String> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    
    

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String deleteFile(){
        try{
    		File file = new File(path+'/'+username+'/'+filename);
    		if(file.delete()){
                        listfilename();
    			return "home";
    		}else{
    			FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Something went wrong!",
                                                    "Please enter correct information"));
                } 	   
    	}catch(Exception e){
    		e.printStackTrace();
    		
    	}
        return "home";
    }
}    
