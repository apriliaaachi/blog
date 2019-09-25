package id.co.bri.dce.blog.repository;

import id.co.bri.dce.blog.entity.Article;
import id.co.bri.dce.blog.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment, Long> {
    @Query("select c from Comment c left join c.article a where c.id = :id")
    public Comment findByArticleId(long id);
}
