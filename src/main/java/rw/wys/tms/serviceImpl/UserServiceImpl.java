package rw.wys.tms.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.wys.tms.config.SecurityUtility;
import rw.wys.tms.dao.RoleDao;
import rw.wys.tms.dao.UserDao;
import rw.wys.tms.dao.UserRoleDao;
import rw.wys.tms.domain.User;
import rw.wys.tms.domain.security.Role;
import rw.wys.tms.domain.security.UserRole;
import rw.wys.tms.service.UserService;
import rw.wys.tms.util.EmailService;
import rw.wys.tms.util.Mail;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	private EmailService emailService;

	public UserServiceImpl() {
	}

	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void isEnabled(long userId, boolean enabled) throws Exception {
		User user = userDao.findById(userId);
		if (user != null) {
			user.setEnabled(enabled);
			userDao.save(user);
		} else {
			throw new Exception("User not exists");
		}
	}

	@Transactional
	public User createUser(User user, Set<UserRole> userRoles) {

		User localUser = userDao.findByUsername(user.getUsername());

		if (localUser != null) {

			LOG.info("Username {} already exists. Please try another one. " + user.getUsername());

		} else {

			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			localUser = (User) userDao.save(user);
		}

		return localUser;
	}

	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public boolean checkUserExists(String username, String email) {
		if ((checkUsernameExists(username)) || (checkEmailExists(email))) {
			return true;
		}
		return false;
	}

	public boolean checkUsernameExists(String username) {
		if (findByUsername(username) != null) {
			return true;
		}
		return false;
	}

	public boolean checkEmailExists(String email) {
		if (findByEmail(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> userRoles(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		return roles;
	}

	@Override
	public boolean checkUserContainsRole(User user, String roleName) {
		Role role = roleDao.findByName(roleName);
		if (!userRoleDao.findByUserAndRole(user, role)) {
			return false;
		}
		return true;
	}

	@Transactional
	public User updateProfile(User user) throws Exception {
		User localUser = userDao.findByUsername(user.getUsername());
		if (localUser == null) {
			LOG.info("Username {} not found. " + user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);
			localUser = userDao.save(user);
		}

		return localUser;
	}

	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public List<User> findByEnabled(boolean b) {

		return userDao.findByEnabled(b);
	}

	@Override
	public User findbyId(long id) throws Exception {
		return userDao.findById(id);
	}

	@Override
	public void resetPassword(String email) throws Exception {
		User user = userDao.findByEmail(email);

		if (user != null) {
			String password = SecurityUtility.randomPassword();
			String encryptedPassword = passwordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			userDao.save(user);
			Mail mail = new Mail();
			mail.setTo(user.getEmail());
			mail.setSubject("Reseting TMS Password");
			mail.setContent("Dear " + user.getFname() + ","
					+ "Somebody (hopefully you) requested to reset your password for Task Management System(TMS) account,"
					+ "\nPlease use the following credentials to log in and edit your password.\n" + "Username:"
					+ user.getUsername() + "\nPassword:" + password + "\n\nBest Regards!\nWYS Team");
			emailService.sendSimpleMessage(mail);

		} else {
			throw new Exception("Account not exists");
		}
	}

	@Override
	public long countUsers() {
		return userDao.count();
	}

}