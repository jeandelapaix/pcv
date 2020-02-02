package rw.wys.tms.service;

import java.util.List;

import rw.wys.tms.domain.security.Role;

public interface RoleService {
	Role createRole(Role role);
	Role findByName(String name);
	List<Role> findAll();
	Role update(Role role);
	void deleteRole(long roleId);
}
