/**
 * 
 */
package com.gkadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.gkadmin.entity.Users;

/**
 * @author clovis
 *
 */
@RepositoryRestResource(collectionResourceRel="Users",path="/usuario/lista")
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
}