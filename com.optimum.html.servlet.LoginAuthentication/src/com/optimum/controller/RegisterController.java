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
import javax.servlet.http.Part;

import com.optimum.dao.UserDAOImpl;
import com.optimum.pojo.User;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
@MultipartConfig(maxFileSize = 16177215)
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private UserDAOImpl refUserDAO;
	private User refUser;

	private RequestDispatcher refRequestDispatcher;

	public RegisterController() {
		refUserDAO = new UserDAOImpl();
		refUser = new User();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		InputStream inputStream = null;

		Part filePart = request.getPart("certificate");

		String name = request.getParameter("name");
		String nric = request.getParameter("nric");
		String gender = request.getParameter("gender");
		String dob = request.getParameter("dob");
		String address = request.getParameter("address");
		String country = request.getParameter("country");
		String qualification = request.getParameter("qualification");
		String department = request.getParameter("department");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String empid = request.getParameter("employeeid");

		if (empid != null) {
			Integer empid1 = Integer.parseInt(empid);
			refUser.setEmpid(empid1);
		}
		refUser.setMobile(mobile);
		refUser.setName(name);
		refUser.setNric(nric);
		refUser.setGender(gender);
		refUser.setDob(dob);
		refUser.setCountry(country);
		refUser.setQualification(qualification);
		refUser.setDepartment(department);
		refUser.setEmail(email);
		refUser.setAddress(address);

		if (filePart != null) {
			inputStream = filePart.getInputStream();
			if (inputStream != null) {
				refUser.setImage(inputStream);
				if (refUserDAO.checkDups(refUser) == true) {
					if (refUserDAO.registration(refUser) == true) {

						out.println("<p><font color='blue'>Registration Succesful!</font></p>");
						refRequestDispatcher = request.getRequestDispatcher("register.jsp");
						refRequestDispatcher.forward(request, response);
					}
				}
				else {
					out.println("<p><font color='red'>User has already been registered</font></p>");
					refRequestDispatcher = request.getRequestDispatcher("register.jsp");
					refRequestDispatcher.forward(request, response);
				}
			}
		}

	}

}
