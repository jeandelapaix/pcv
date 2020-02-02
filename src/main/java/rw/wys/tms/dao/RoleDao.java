package rw.wys.tms.dao;

import org.springframework.data.repository.*;
import rw.wys.tms.domain.security.*;

public interface RoleDao extends CrudRepository<Role, Long>
{
    Role findByName(String role);
}