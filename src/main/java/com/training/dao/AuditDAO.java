package com.training.dao;

import java.util.List;

import com.training.model.Audit_log;
import com.training.model.User;

public interface AuditDAO {

	public int addUser(User user) throws Exception;
	public User searchId(int userId) throws Exception;
	public void updateUser(User user) throws Exception;
	public void deleteUser(int userId) throws Exception;
	public List<User> retrieveUsers() throws Exception;
	public List<Audit_log> retrievelogs() throws Exception;
	public void logIt(Audit_log log);
}
