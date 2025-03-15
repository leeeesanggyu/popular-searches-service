package com.salgu.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PopularSearchTermRepository extends JpaRepository<PopularSearchTermEntity, UUID> {
}
