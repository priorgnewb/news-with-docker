package org.faze.news.repositories;

import org.faze.news.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByOrderByIdDesc();

    @Query("SELECT n FROM News n " +
            "JOIN NewsType nt ON n.newsType = nt WHERE nt.type = :type")
    List<News> findAllNewsByNewsType(@Param("type") String type);
}
