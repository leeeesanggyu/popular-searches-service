package com.salgu.search;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "popular_search_term")
public class PopularSearchTermEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "search_term")
    private String searchTerm;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static PopularSearchTermEntity save(SearchTerm searchTerm) {
        return new PopularSearchTermEntity(
                UUID.randomUUID(),
                searchTerm.getKeyword(),
                LocalDateTime.now()
        );
    }

    public SearchTerm toDomain() {
        return SearchTerm.of(searchTerm);
    }
}
