package org.imperfect.autumn.user;

import org.imperfect.autumn.user.model.User;
import org.imperfect.autumn.user.model.UserDescriptor;
import org.imperfect.autumn.util.repository.AbstractAdapter;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.lang.String.format;

public class UserRepository extends AbstractAdapter<User, Integer> {
	
	private static final String TABLE_NAME = "users";
	
	private final Connection connection;
	
	public UserRepository(Connection connection) {
		super(Integer.class);
		this.connection = connection;
	}
	
	public void createTable() {
		String sql = format("CREATE TABLE IF NOT EXISTS %s ("
				+ "id integer auto_increment,"
				+ " username text, PRIMARY KEY ( id ))", TABLE_NAME);
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
		} catch(SQLException ex) {
			throw new PersistenceException("Failed to create user table!", ex);
		}
	}
	
	public Integer insert(UserDescriptor user) {
		try(PreparedStatement statement = connection.prepareStatement(
				format("insert into %s(username) values(?)", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			return executeUpdateWithReturnedId(statement);
		} catch(SQLException ex) {
			throw new PersistenceException("Failed to add user!", ex);
		}
	}
	
	public void update(int id, UserDescriptor user) {
		try(PreparedStatement statement = connection.prepareStatement(
				format("update %s set username = ? where id = ?", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			statement.setInt(2, id);
			
			int updatedCount = statement.executeUpdate();
			if(updatedCount != 1) {
				throw new EntityNotFoundException(
						format("No user with ID '%d' updated!", id));
			}
		} catch(SQLException ex) {
			throw new PersistenceException(
					format("Failed to update user with ID '%d'!", id), ex);
		}
	}
	
	public User findOne(int id) {
		try(PreparedStatement statement = connection.prepareStatement(
				format("select * from %s where id = ?", TABLE_NAME))) {
			statement.setInt(1, id);
			List<User> users = find(statement);
			if(users.size() != 1) {
				throw new EntityNotFoundException(
						format("No user with ID '%d' found!", id));
			}
			return users.get(0);
		} catch(SQLException ex) {
			throw new PersistenceException(
					format("Failed to find user with ID '%d'!", id));
		}
	}
	
	public List<User> findAll() {
		try(PreparedStatement statement = connection.prepareStatement(
				format("select * from %s", TABLE_NAME))) {
			return find(statement);
		} catch(SQLException ex) {
			throw new PersistenceException("Failed to query user list!", ex);
		}
	}
	
	public void delete(int id) {
		try(PreparedStatement statement = connection.prepareStatement(
				format("delete from %s where id = ?", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, id);
			
			int deleteCount = statement.executeUpdate();
			if(deleteCount != 1) {
				throw new EntityNotFoundException(
						format("No user with ID '%d' deleted!", id));
			}
		} catch(SQLException ex) {
			throw new PersistenceException(
					format("Failed to delete user with ID '%d'!", id), ex);
		}
	}
	
	@Override
	protected User resultSetsMapper(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		return user;
	}
	
}
