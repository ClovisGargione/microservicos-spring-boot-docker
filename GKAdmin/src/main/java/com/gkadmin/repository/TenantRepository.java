package com.gkadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gkadmin.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
