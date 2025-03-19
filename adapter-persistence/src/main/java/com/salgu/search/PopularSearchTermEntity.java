package com.salgu.search;

import jakarta.persistence.*;
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
@Table(
        name = "popular_search_term"
//        indexes = {
//                @Index(name = "idx_keyword", columnList = "keyword")
//        }
)public class PopularSearchTermEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "keyword")
    private String keyword;

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
        return SearchTerm.of(keyword);
    }
}
