package id.co.bri.dce.blog.repository;

import id.co.bri.dce.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends CrudRepository<Article, Long> {
    public List<Article> findByStatus(String status);
    public Page<Article> findByStatus(String status, Pageable pageable);
    public Article findById(long id);
}
