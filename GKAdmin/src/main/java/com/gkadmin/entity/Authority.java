/**
 * 
 */
package com.gkadmin.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.gkadmin.enums.AuthorityName;

/**
 * @author clovis
 *
 */
@Entity
public class Authority {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
	private Long id;
	
	@NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityName name;
	
	@ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<Users> users;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public AuthorityName getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(AuthorityName name) {
		this.name = name;
	}

	/**
	 * @return the users
	 */
	public List<Users> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Users> users) {
		this.users = users;
	}
}
