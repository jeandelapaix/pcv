package rw.wys.tms.dao;

import org.springframework.data.repository.*;

import rw.wys.tms.domain.User;
import rw.wys.tms.domain.security.*;

public interface UserRoleDao extends CrudRepository<UserRole, Long>
{
    Iterable<UserRole> findByRole(Role userRole);
    boolean findByUserAndRole(User user, Role role);
}