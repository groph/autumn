package org.imperfect.autumn.user;

import javax.inject.Inject;

import org.imperfect.autumn.user.model.User;
import org.imperfect.autumn.user.model.UserDescriptor;

public class UserService {
	
	private final UserRepository userRepository;
	
	@Inject
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		userRepository.createTable();
	}
	
	public User save(UserDescriptor user) {
		int id = userRepository.insert(user);
		return userRepository.findOne(id);
	}
	
	public User update(int id, UserDescriptor user) {
		userRepository.update(id, user);
		return userRepository.findOne(id);
	}
	
	public User findOne(int id) {
		return userRepository.findOne(id);
	}
	
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
	
	public User delete(int id) {
		User userToDelete = userRepository.findOne(id);
		userRepository.delete(id);
		return userToDelete;
	}
	
}
