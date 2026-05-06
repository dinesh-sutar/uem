package com.uem.uem_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.ApplicationPolicy;

public interface ApplicationPolicyRepository
        extends JpaRepository<ApplicationPolicy, Long> {
}