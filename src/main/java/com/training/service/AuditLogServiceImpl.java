package com.training.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.dao.AuditDAO;
import com.training.model.Audit_log;
import com.training.model.User;

@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditDAO auditDAO; 

	public int addUser(User user) throws Exception {
		// TODO Auto-generated method stub
		
		return auditDAO.addUser(user);
	}

	public User searchId(int userId) throws Exception {
		// TODO Auto-generated method stub
		return auditDAO.searchId(userId);
	}

	public void updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		auditDAO.updateUser(user);
	}

	public void deleteUser(int userId) throws Exception {
		// TODO Auto-generated method stub
		auditDAO.deleteUser(userId);
	}

	public List<User> retrieveUsers() throws Exception {
		// TODO Auto-generated method stub
		return auditDAO.retrieveUsers();
	}

	public List<Audit_log> retrievelogs() throws Exception {
		// TODO Auto-generated method stub
		return auditDAO.retrievelogs();
	}

}
