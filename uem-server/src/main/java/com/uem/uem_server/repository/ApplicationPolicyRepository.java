package com.uem.uem_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.constant.OSType;
import com.uem.uem_server.entity.ApplicationPolicy;

public interface ApplicationPolicyRepository
                extends JpaRepository<ApplicationPolicy, Long> {

        Optional<ApplicationPolicy> findByProcessNameAndOsType(
                        String processName,
                        OSType osType);

}