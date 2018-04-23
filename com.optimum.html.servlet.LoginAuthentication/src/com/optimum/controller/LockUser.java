package com.optimum.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.dao.UserDAOImpl;

/**
 * Servlet implementation class LockUser
 */
@WebServlet("/LockUser")
public class LockUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	private UserDAOImpl refUserDAO;

	public LockUser() {
		refUserDAO = new UserDAOImpl();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String emp = request.getParameter("emp");
		
		refUserDAO.updateStatus(emp);
		response.sendRedirect("request.jsp");
	}

}
