 package rw.wys.tms.dao;

import org.springframework.data.repository.*;
import rw.wys.tms.domain.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;

public interface UserDao extends CrudRepository<User, Long> {
	User findByUsername(String username);

	User findByEmail(String email);

	User findById(long id);

	@Query("SELECT u from User u WHERE u.id=?1")
	User findOne(long id);
	
	List<User> findByEnabled(boolean enabled);
}