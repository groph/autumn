package org.imperfect.autumn.services;

import javax.inject.Inject;
import org.imperfect.autumn.adapters.UserAdapter;
import org.imperfect.autumn.model.User;

public class UserService {
	
	private final UserAdapter userAdapter;
	
	@Inject
	public UserService(UserAdapter userAdapter) {
		this.userAdapter = userAdapter;
		userAdapter.createTable();
	}
	
	public User save(User user) {
		int id = userAdapter.insert(user);
		return userAdapter.findOne(id);
	}
	
	public User update(int id, User user) {
		user.setId(id);
		userAdapter.update(user);
		return userAdapter.findOne(id);
	}
	
	public User findOne(int id) {
		return userAdapter.findOne(id);
	}
	
	public Iterable<User> findAll() {
		return userAdapter.findAll();
	}
	
	public User delete(int id) {
		User userToDelete = userAdapter.findOne(id);
		userAdapter.delete(id);
		return userToDelete;
	}
	
}
