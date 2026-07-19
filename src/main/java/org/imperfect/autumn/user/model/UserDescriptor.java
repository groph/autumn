package org.imperfect.autumn.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDescriptor {

	private String username;

	public String getUsername() {
		return username;
	}

	@NotNull
	public void setUsername(String username) {
		this.username = username;
	}

}
