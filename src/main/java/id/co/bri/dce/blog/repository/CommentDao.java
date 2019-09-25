package id.co.bri.dce.blog.repository;

import id.co.bri.dce.blog.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentDao extends CrudRepository<Comment, Long> {

}
