package org.imperfect.autumn.adapters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.imperfect.autumn.model.User;
import org.imperfect.autumn.exceptions.DataAccessException;

public class UserAdapter extends AbstractAdapter<User, Integer> {
	
	private static final String TABLE_NAME = "users";
	
	private final Connection connection;
	
	public UserAdapter(Connection connection) {
		super(Integer.class);
		this.connection = connection;
	}
	
	public void createTable() {
		String sql = String.format("CREATE TABLE IF NOT EXISTS %s ("
				+ "id integer auto_increment,"
				+ " username text, PRIMARY KEY ( id ))", TABLE_NAME);
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public Integer insert(User user) {
		try(PreparedStatement statement = connection.prepareStatement(
				String.format("insert into %s(username) values(?)", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			return executeUpdateWithReturnedId(statement);
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public void update(User user) {
		try(PreparedStatement statement = connection.prepareStatement(
				String.format("update %s set username = ? where id = ?", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getUsername());
			statement.setInt(2, user.getId());
			
			statement.executeUpdate();
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public User findOne(int id) {
		try(PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s where id = ?", TABLE_NAME))) {
			statement.setInt(1, id);
			List<User> users = find(statement);
			return users.size() > 0? users.get(0) : null;
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public List<User> findAll() {
		try(PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s", TABLE_NAME))) {
			return find(statement);
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
		}
	}
	
	public void delete(int id) {
		try(PreparedStatement statement = connection.prepareStatement(
				String.format("delete from %s where id = ?", TABLE_NAME),
				Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, id);
			
			statement.executeUpdate();
		} catch(SQLException ex) {
			throw new DataAccessException(ex);
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
