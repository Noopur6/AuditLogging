package com.training.interceptor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.dao.AuditDAO;
import com.training.entity.Entity;
import com.training.model.Audit_log;
import com.training.model.User;

@Component("interceptor")
public class AuditInterceptor extends EmptyInterceptor {

	@Autowired
	private AuditDAO auditDAO;
	
	private Set<Object> addSet = new HashSet<Object>();
	private Set<Object> updateSet = new HashSet<Object>();
	private Set<Object> deleteSet = new HashSet<Object>();
	private static final long serialVersionUID = 1L;

	@Override
	public void onDelete(Object object, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		// TODO Auto-generated method stub
		User user = (User) object;
		Entity entity = new Entity();
		entity.setDetail("userId: "+user.getUserId()+" userName: "+user.getUserName());
		entity.setTime(new Timestamp(System.currentTimeMillis()));
		deleteSet.add(entity);
		
	}

	@Override
	public boolean onFlushDirty(Object object, Serializable id, Object[] currentState, Object[] previousState, 
			String[] propertyNames, Type[] types) throws CallbackException {
		
			
		User user = (User) object;
			
		String detail = "";
		for (int i = 0; i < currentState.length; i++) {
			Object p = previousState[i];
			Object c = currentState[i];
			if(!p.equals(c))
				detail+=""+propertyNames[i]+": "+p+" changed to "+c+" for userId: "+user.getUserId()+" and username: "+user.getUserName();
		}
		
		Entity entity = new Entity();
		entity.setDetail(detail);
		entity.setTime(new Timestamp(System.currentTimeMillis()));
		updateSet.add(entity);
		return false;
	}

	@Override
	public boolean onSave(Object object, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		// TODO Auto-generated method stub
		if(object instanceof User) {
			
			User user = (User) object;
			Entity entity = new Entity();
			entity.setDetail(" userName: "+user.getUserName());
			entity.setTime(new Timestamp(System.currentTimeMillis()));
			addSet.add(entity);
		}
		return false;
	}

	@Override
	@Transactional
	public void postFlush(Iterator iterator) throws CallbackException {
		// TODO Auto-generated method stub
		
		try {
			Iterator<Object> itr = addSet.iterator();
			while (itr.hasNext()) {

				Entity entity = (Entity) itr.next();
				Audit_log log = new Audit_log();
				log.setOperation("Saved");
				log.setDetail(entity.getDetail());
				log.setTimestamp(entity.getTime());
				auditDAO.logIt(log);

			}
			itr = updateSet.iterator();
			while (itr.hasNext()) {

				Entity entity = (Entity) itr.next();
				Audit_log log = new Audit_log();
				log.setOperation("Updated");
				log.setDetail(entity.getDetail());
				log.setTimestamp(entity.getTime());
				auditDAO.logIt(log);

			}
			itr = deleteSet.iterator();
			while (itr.hasNext()) {

				Entity entity = (Entity) itr.next();
				Audit_log log = new Audit_log();
				log.setOperation("Deleted");
				log.setDetail(entity.getDetail());
				log.setTimestamp(entity.getTime());
				auditDAO.logIt(log);

			} 
		} finally {
			// TODO: handle finally clause
			
			addSet.clear();
			updateSet.clear();
			deleteSet.clear();
		}
		
	}

}
