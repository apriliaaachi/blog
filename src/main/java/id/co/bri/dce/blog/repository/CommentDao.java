package id.co.bri.dce.blog.repository;

import id.co.bri.dce.blog.entity.Article;
import id.co.bri.dce.blog.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment, Long> {
//    @Query("select c from Comment c where c.article.articleid = :id")
//    public List<Comment> findByArticleId(@RequestParam long id);
}
