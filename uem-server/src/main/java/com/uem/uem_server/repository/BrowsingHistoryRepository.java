package com.uem.uem_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uem.uem_server.entity.BrowsingHistory;

public interface BrowsingHistoryRepository
        extends JpaRepository<BrowsingHistory, Long> {

}
