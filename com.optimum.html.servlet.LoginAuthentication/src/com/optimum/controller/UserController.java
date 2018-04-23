
package com.optimum.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.optimum.dao.UserDAO;
import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDAO refUserDAO;
	private User refUser;
	private UserDAOImpl refDAO;

	private RequestDispatcher refRequestDispatcher;

	public UserController() {
		refUserDAO = new UserDAOImpl();
		refDAO = new UserDAOImpl();
		refUser = new User();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		// get data from html page

		String userName = request.getParameter("uname");
		String password = request.getParameter("pwd");

		// set data to POJO class

		refUser.setUserName(userName);
		refUser.setUserPassword(password);

		if (refUserDAO.loginAuthentication(refUser) == true) {
			
			HttpSession session = request.getSession(true);
			session.setAttribute("user", userName);
			
			if (userName.equals("admin@admin.com")) {
					response.sendRedirect("Welcome.jsp");
			} 
			else {
				if(refDAO.statusCheck(refUser)==true) {
					response.sendRedirect("user.jsp");
					
				}
				else {
					session.invalidate();
					out.println("<div class='header'><font color='red'>Your Account is Locked!</font></div>");
					refRequestDispatcher = request.getRequestDispatcher("login.html");
					refRequestDispatcher.include(request, response);
				}
			}
			// true
		} else {

			out.println("<div class='header'><font color='red'>Invalid Credentials</font></div>");
			refRequestDispatcher = request.getRequestDispatcher("login.html");
			refRequestDispatcher.include(request, response);

			// false
		}
	}

}
