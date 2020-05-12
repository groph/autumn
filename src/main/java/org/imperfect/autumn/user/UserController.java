package org.imperfect.autumn.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imperfect.autumn.user.model.User;
import org.imperfect.autumn.user.model.UserDescriptor;
import org.imperfect.autumn.util.exception.MethodNotAllowedException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {
	
	private final ObjectMapper mapper;
	private final UserService service;
	
	@Inject
	public UserController(UserService service, ObjectMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		assertNotEmptyPath(req.getPathInfo());
		
		UserDescriptor userToCreate = mapper.readValue(req.getInputStream(),
				UserDescriptor.class);
		
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
			throws ServletException, IOException {
		
		assertEmptyPath(req.getPathInfo());
		
		String id = getIdFromPathInfo(req.getPathInfo());
		UserDescriptor userToUpdate = mapper.readValue(req.getInputStream(),
				UserDescriptor.class);
		
		User updatedUser = service.update(Integer.parseInt(id), userToUpdate);
		
		resp.getWriter().write(mapper.writeValueAsString(updatedUser));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		assertEmptyPath(req.getPathInfo());
		
		String id = getIdFromPathInfo(req.getPathInfo());
		
		User deletedUser = service.delete(Integer.parseInt(id));
		
		resp.getWriter().write(mapper.writeValueAsString(deletedUser));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private boolean isEmptyPath(String pathInfo) {
		return pathInfo == null  || pathInfo.isEmpty() || pathInfo.equals("/");
	}
	
	private void assertEmptyPath(String pathInfo)
			throws MethodNotAllowedException {
		
		if(isEmptyPath(pathInfo)) {
			throw new MethodNotAllowedException();
		}
	}
	
	private void assertNotEmptyPath(String pathInfo)
			throws MethodNotAllowedException {
		
		if(!isEmptyPath(pathInfo)) {
			throw new MethodNotAllowedException();
		}
	}
	
	private String getIdFromPathInfo(String pathInfo) {
		if(pathInfo == null || pathInfo.equals("/")) {
			return null;
		}
		return pathInfo.substring(Math.min(1, pathInfo.length()));
	}
	
}
