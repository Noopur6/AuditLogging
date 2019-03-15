package com.training.dao;

import java.sql.Timestamp;
import java.util.List;

import com.training.model.Audit_log;
import com.training.model.User;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;

@Repository
public class AuditDAOImpl implements AuditDAO {

	@Autowired 
	private SessionFactory sessionFactory;
	
	int id;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	
	public int addUser(User user){
		// TODO Auto-generated method stub
		
		Session s = sessionFactory.getCurrentSession();
		id = (Integer) s.save(user);
		return id;
		
	}

	public User searchId(int userId) {
		// TODO Auto-generated method stub

		Session s = sessionFactory.getCurrentSession();
		User user = (User) s.get(User.class, userId);
		
		if(user!=null) {
			
			Audit_log log = new Audit_log();
			log.setDetail("userId: "+userId+" userName: "+user.getUserName());
			log.setOperation("Read");
			log.setTimestamp(new Timestamp(System.currentTimeMillis()));
			logIt(log);
		}
	    return user;
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
		Session s = sessionFactory.getCurrentSession();
		User u = (User) s.get(User.class, user.getUserId());
		if(!u.getUserName().equals(user.getUserName())||!u.getAddress().equals(user.getAddress())||!u.getMobile().equals(user.getMobile()))
			s.merge(user);
		
	}

	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		Session s = sessionFactory.getCurrentSession();
		
		User user = (User)s.get(User.class,userId);
		s.delete(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> retrieveUsers() {
		// TODO Auto-generated method stub
		
		List<User> users = null;
		Session s = sessionFactory.getCurrentSession();
		
		users = s.createQuery("from User").list();
		return users;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Audit_log> retrievelogs() {
		// TODO Auto-generated method stub
		
		List<Audit_log> logs = null;
		Session s = sessionFactory.getCurrentSession();
		logs = s.createQuery("from Audit_log").list();
		return logs;
		
		}

	public void logIt(Audit_log log) {
		// TODO Auto-generated method stub
		
		Session s = sessionFactory.getCurrentSession();
		if(log.getOperation().equals("Saved"))
			log.setDetail("userId: "+id+log.getDetail());
		s.save(log);
		
	}

}
