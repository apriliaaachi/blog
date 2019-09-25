package id.co.bri.dce.blog.repository;

import id.co.bri.dce.blog.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);

}
