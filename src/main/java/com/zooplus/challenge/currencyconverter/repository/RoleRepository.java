package com.zooplus.challenge.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zooplus.challenge.currencyconverter.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	/**
	 * find role by role name.
	 * 
	 * @param role
	 * @return matched role.
	 */
	Role findByRole(String role);

}
