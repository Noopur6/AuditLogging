package com.training.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.training.model.Audit_log;
import com.training.model.User;
import com.training.service.AuditLogService;

@Controller
public class AuditController {

	@Autowired
	private AuditLogService auditLogService;
	
	private static final Logger logger = Logger.getLogger(AuditController.class);
 
    public AuditController() {}
	
    @RequestMapping(value="/welcome",method=RequestMethod.POST)
	public ModelAndView welcomeMessage(HttpServletRequest req,HttpServletResponse res) {
		// Name of your jsp file as parameter		
		
    	ModelAndView view = null;
		String user=req.getParameter("username");
		String pass=req.getParameter("password");
		
		if(user.equals("admin") && pass.equals("admin")) {
			
			view = new ModelAndView("welcome");
			}
		else {
			view = new ModelAndView("error");
			view.addObject("msg","wrong login id or password");
		}	
		return view;
	}
	 @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	    public ModelAndView method() {
	            return new ModelAndView("redirect:" + "/");

	    }
	 
	 @RequestMapping(value = "/goback")
	    public ModelAndView back() {
	            return new ModelAndView("welcome");

	    }
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add() {
		// Name of your jsp file as parameter
		ModelAndView view = new ModelAndView("adduser");
		return view;
	}
	
	@RequestMapping(value= {"/read","/update"},method=RequestMethod.GET)
	public ModelAndView read(@RequestParam(value = "flag", required = true) String flag) {
		// Name of your jsp file as parameter
		
		ModelAndView view = new ModelAndView("searchuser");
		view.addObject("flag", flag);
		return view;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public ModelAndView delete() {
		// Name of your jsp file as parameter
		
		List<User> usersList;
		ModelAndView view = null;
		try {
			usersList = auditLogService.retrieveUsers();
			
			if(usersList.isEmpty())
				view = new ModelAndView("message","msg","no accounts to delete.");
			else {
			view = new ModelAndView("deleteuser");
			view.addObject("usersList", usersList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("retreival of Users failed");
			view = new ModelAndView("error","msg","Some error occurred.");
			
		}
		
		return view;
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest req,HttpServletResponse res) {
		// Name of your jsp file as parameter
		
		ModelAndView view = null;
		User user = new User();
		
		user.setUserName(req.getParameter("userName"));
		user.setAddress(req.getParameter("address"));
		user.setMobile(req.getParameter("mobile"));
		 try {
			auditLogService.addUser(user);
			
			view = new ModelAndView("adduser");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("adding user failed");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		}
		
		return view;
	}
	
	@RequestMapping(value="/searchUser",method=RequestMethod.GET)
	public ModelAndView searchUser(HttpServletRequest req,HttpServletResponse res) {
		// Name of your jsp file as parameter
		ModelAndView view = null;
		
		User user;
		try {
			String userId = req.getParameter("userId");
			user = auditLogService.searchId(Integer.parseInt(userId));
			
			if(user==null)
				view = new ModelAndView("message","msg","Invalid UserId.");
			else {
			String flag = req.getParameter("flag");
			if(flag.equals("1"))
				view = new ModelAndView("searchspec");
			else
				view = new ModelAndView("updatespec");
			view.addObject("user", user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("searching user failed");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		
		}
		
		return view;
	} 
	
	
	@RequestMapping(value="/saveChanges",method=RequestMethod.POST)
	public ModelAndView saveChanges(HttpServletRequest req,HttpServletResponse res) {
		// Name of your jsp file as parameter
		
		User user = new User();
		String userId = req.getParameter("userId");
		user.setUserId(Integer.parseInt(userId));
		user.setUserName(req.getParameter("userName"));
		user.setAddress(req.getParameter("address"));
		user.setMobile(req.getParameter("mobile"));
		ModelAndView view = null;
		try {
			
			auditLogService.updateUser(user);
			
			view = new ModelAndView("updatespec");
			view.addObject("user", user); 
				 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			logger.info("updation of user failed");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		}
		
		
		return view;
	}
	
	
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam(value = "Id", required = true) String Id) {
		// Name of your jsp file as parameter
		
		ModelAndView view = null;
		try {
			auditLogService.deleteUser(Integer.parseInt(Id));
			
			List<User> usersList = auditLogService.retrieveUsers();
			if(usersList.isEmpty())
				view = new ModelAndView("message","msg","no accounts to delete.");
			else {
			view = new ModelAndView("deleteuser");
			view.addObject("usersList", usersList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			logger.info("deletion of user failed");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		}
		
		return view;
	}
	
	@RequestMapping(value="/allUsers",method=RequestMethod.GET)
	public ModelAndView viewUsers() {
		// Name of your jsp file as parameter
		ModelAndView view = null;
		List<User> usersList;
		try {
			usersList = auditLogService.retrieveUsers();
			
			if(usersList.isEmpty())
				view = new ModelAndView("message","msg","No users here!");
			else {
				
				view = new ModelAndView("allUsers");
				view.addObject("usersList", usersList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			logger.info("retreival of users failed");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		}
		
		
		return view;
	}
	
	@RequestMapping(value="/logsList",method=RequestMethod.GET)
	public ModelAndView viewLogs() {
		// Name of your jsp file as parameter
		ModelAndView view = null;
		List<Audit_log> logsList;
		try {
			logsList = auditLogService.retrievelogs();
			
			if(logsList.isEmpty())
				view = new ModelAndView("message","msg","No logs here!");
			else {
				
			view = new ModelAndView("allLogs");
			view.addObject("logsList", logsList);
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("could'nt failed ");
			view = new ModelAndView("error","msg","Some error occurred. Try later.");
		}
		
		return view;
	}
	
}