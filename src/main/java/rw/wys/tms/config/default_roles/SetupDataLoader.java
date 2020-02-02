package rw.wys.tms.config.default_roles;

import rw.wys.tms.dao.*;
import rw.wys.tms.domain.*;
import rw.wys.tms.domain.security.Role;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SetupDataLoader implements  ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		if (alreadySetup) {
			System.out.println("What the fuck??");
            return;
        }

        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        System.out.println("What the fuck??");
        // == create initial user
        //createUserIfNotFound("test@test.com", "Test", "Test", "test", new ArrayList<Role>(Arrays.asList(adminRole)));

        alreadySetup = true;
	}
	
	@Transactional
    private final Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
        }
        role = roleRepository.save(role);
        
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFname(firstName);
            user.setLname(lastName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
        }
        //user.setUserRoles(roles);
        user = userRepository.save(user);
        return user;
    }


}
