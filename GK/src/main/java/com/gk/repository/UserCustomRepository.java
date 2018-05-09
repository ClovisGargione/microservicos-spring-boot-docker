/**
 * 
 */
package com.gk.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.gk.entity.Users;

/**
 * @author clovis
 *
 */
@Repository
@Transactional
public class UserCustomRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	public Users findByUsername(String username) {
		
		TypedQuery<Users> query = em.createQuery("select u from Users u where u.username = :username", Users.class);
		query.setParameter("username", username);
		try {
			Users user = query.getSingleResult();
			return user;
		}catch(NoResultException e) {
			return null;
		}
	}

}
