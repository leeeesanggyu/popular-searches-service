package com.salgu.search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PopularSearchTermRepository extends JpaRepository<PopularSearchTermEntity, UUID> {

    @Query("SELECT p.keyword, COUNT(p.keyword) as count " +
            "FROM PopularSearchTermEntity p " +
            "GROUP BY p.keyword " +
            "ORDER BY count DESC")
    List<Object[]> findKeywordsOrderedByCount(Pageable pageable);
}
