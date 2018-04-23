package com.optimum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.dao.UserDAOImpl;

/**
 * Servlet implementation class ForgetPassword
 */
@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	private UserDAOImpl refUserDAO;
	private RequestDispatcher refRequestDispatcher;
	
	public ForgetPassword() {
		refUserDAO = new UserDAOImpl();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String email = request.getParameter("forgetpass");
  
		refUserDAO.getForgetPassword(email);
		out.println("<div class='header'><font color='blue'>Password Sent!</font></div>");
		refRequestDispatcher = request.getRequestDispatcher("login.html");
		refRequestDispatcher.include(request, response);
	}

}
