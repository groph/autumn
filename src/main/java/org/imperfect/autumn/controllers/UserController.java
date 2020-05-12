package org.imperfect.autumn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.imperfect.autumn.model.User;
import org.imperfect.autumn.services.UserService;

public class UserController extends HttpServlet {
	
	private final ObjectMapper mapper = new ObjectMapper();
	private final UserService service;
	
	@Inject
	public UserController(UserService service) {
		this.service = service;
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		User userToCreate = mapper.readValue(req.getInputStream(), User.class);
		
		User createdUser = service.save(userToCreate);
		
		resp.getWriter().write(mapper.writeValueAsString(createdUser));
		resp.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String id = getIdFromPathInfo(req.getPathInfo());
		
		Object data = (id == null)? service.findAll()
				: service.findOne(Integer.parseInt(id));
		
		resp.getWriter().write(mapper.writeValueAsString(data));
		int responseCode = (data == null)? HttpServletResponse.SC_NOT_FOUND
				: HttpServletResponse.SC_OK;
		
		resp.setStatus(responseCode);
	}
	
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String id = getIdFromPathInfo(req.getPathInfo());
		User userToUpdate = mapper.readValue(req.getInputStream(), User.class);
		
		User updatedUser = service.update(Integer.parseInt(id), userToUpdate);
		
		resp.getWriter().write(mapper.writeValueAsString(updatedUser));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String id = getIdFromPathInfo(req.getPathInfo());
		
		User deletedUser = service.delete(Integer.parseInt(id));
		
		resp.getWriter().write(mapper.writeValueAsString(deletedUser));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private String getIdFromPathInfo(String pathInfo) {
		if(pathInfo == null || pathInfo.equals("/")) {
			return null;
		}
		return pathInfo.substring(Math.min(1, pathInfo.length()));
	}
	
}
