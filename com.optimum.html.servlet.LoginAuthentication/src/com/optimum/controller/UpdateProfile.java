package com.optimum.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;


/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile")
@MultipartConfig(maxFileSize = 16177215)
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private UserDAOImpl refUserDAO;

	public UpdateProfile() {
		refUserDAO = new UserDAOImpl();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		InputStream inputStream = null;

		String userName = request.getParameter("userName");
		String checker = request.getParameter("button");
		System.out.println(checker);
		if(checker.equals("Update Password")) {
			String password = request.getParameter("password");
			refUserDAO.updateUserPass(password, "password", userName);
			out.println("<p><font color='blue'>Password Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Address")) {
			String address = request.getParameter("address");
			refUserDAO.updateUserText(address, "address", userName);
			out.println("<p><font color='blue'>Address Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Qualification")) {
			String qualification = request.getParameter("qualification");
			refUserDAO.updateUserText(qualification, "qualification", userName);
			out.println("<p><font color='blue'>Qualification Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Certificates")) {
			Part filePart = request.getPart("certificate");
			inputStream = filePart.getInputStream();
			refUserDAO.updateEmpImage(inputStream, "certificate", userName);
			out.println("<p><font color='blue'>Certification Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Picture")) {
			Part filePart = request.getPart("profilepic");
			inputStream = filePart.getInputStream();
			refUserDAO.updateEmpImage(inputStream, "profilepic", userName);
			out.println("<p><font color='blue'>Profile Picture Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Email")) {
			String email = request.getParameter("email");
			refUserDAO.updateUserText(email, "email", userName);
			out.println("<p><font color='blue'>Email Update Succesful!</font></p>");
		}
		else if(checker.equals("Update Mobile")) {
			String mobile = request.getParameter("mobile");
			refUserDAO.updateUserText(mobile, "mobile", userName);
			out.println("<p><font color='blue'>Mobile Update Succesful!</font></p>");
		}
		else {
			
		}

	}

}
