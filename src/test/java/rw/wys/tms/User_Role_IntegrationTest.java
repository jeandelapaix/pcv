package rw.wys.tms;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.constraints.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import rw.wys.tms.dao.RoleDao;
import rw.wys.tms.dao.UserDao;
import rw.wys.tms.domain.User;
import rw.wys.tms.domain.security.Role;

@RunWith(SpringRunner.class)
@SpringBootTest
public class User_Role_IntegrationTest {
	private boolean alreadySetup = false;

	@Autowired
	private UserDao userRepository;

	@Autowired
	private RoleDao roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testingAssignmentAdd() {
		// TODO Auto-generated method stub
		if (alreadySetup) {
			System.out.println("What the fuck??");
			return;
		}

		Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");

		Set roles = new HashSet<Role>();
		roles.add(adminRole);
		// == create initial user
		createUserIfNotFound("test@test.com", "Test","Test", "Test", "test", roles);

		alreadySetup = true;
	}

	@Transactional
	private final Role createRoleIfNotFound(String name) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
		}
		role = roleRepository.save(role);

		return role;
	}

	@Transactional
	private final User createUserIfNotFound(final String email, final String username, final String firstName, final String lastName,
			final String password, Set<Role> roles) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setFname(firstName);
			user.setLname(lastName);
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setEmail(email);
			user.setEnabled(true);
		}
		//user.setUserRoles(roles);
		user = userRepository.save(user);
		return user;
	}
}