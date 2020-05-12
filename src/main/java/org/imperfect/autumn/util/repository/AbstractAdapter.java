package org.imperfect.autumn.util.repository;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAdapter<T, ID extends Serializable> {
	
	private final Class<ID> serializableClass;
	
	public AbstractAdapter(Class<ID> serializableClass) {
		this.serializableClass = serializableClass;
	}
	
	protected abstract T resultSetsMapper(ResultSet resultSet) throws SQLException;
	
	protected ID executeUpdateWithReturnedId(PreparedStatement statement)
			throws SQLException {
		
		statement.executeUpdate();
		try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if(generatedKeys.next()) {
				return generatedKeys.getObject(1, serializableClass);
			}
			throw new PersistenceException("Failed to persist data!");
		}
	}
	
	protected List<T> find(PreparedStatement statement) throws SQLException {
		List<T> findings = new ArrayList<>();
		try(ResultSet resultSet = statement.executeQuery()) {
			while(resultSet.next()) {
				findings.add(resultSetsMapper(resultSet));
			}
			return findings;
		}
	}
	
}
