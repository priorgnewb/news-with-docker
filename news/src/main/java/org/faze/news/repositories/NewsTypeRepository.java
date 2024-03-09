package org.faze.news.repositories;

import org.faze.news.models.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsTypeRepository extends JpaRepository<NewsType, Integer> {
    Optional<NewsType> findByType(String type);
}
