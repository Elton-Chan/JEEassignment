package com.optimum.dao;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import com.optimum.controller.DatabaseUtility;
import com.optimum.pojo.User;

public class UserDAOImpl implements UserDAO {
	static Logger log4j;
	private boolean loginStatus;
	private boolean lockedStatus;
	private boolean registrationStatus;
	private boolean dupStatus;
	private Connection conn = null;
	String name, mobile, nric;
	PreparedStatement preparedStatement = null;

	public UserDAOImpl() {
		conn = DatabaseUtility.getConnection();
		new User();
		log4j = Logger.getLogger(UserDAOImpl.class);
	}


	@Override
	public boolean loginAuthentication(User refUser) {	//this is check login details
		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select email,password from loginweb where email='" + refUser.getUserName() + "'");

			while (rs.next()) {
				String email = rs.getString("email");
				String password = rs.getString("password");

				if (refUser.getUserName().equals(email) && getMD5(refUser.getUserPassword()).equals(password)) {
					loginStatus = true;
					System.out.println(password);
					System.out.println(getMD5(refUser.getUserPassword()));
					log4j.warn(refUser.getUserName() + " has logged in");

				} else {
					loginStatus = false;
					log4j.warn("Invalid Login Occured");
				}

			}
		} catch (SQLException e) {
			log4j.warn("Error occured during login");
			e.printStackTrace();
		}
		return loginStatus;

	}//end of loginAuthentication

	public boolean statusCheck(User refUser) {	//this method is to check if user account is locked
		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select status from loginweb where email='" + refUser.getUserName() + "'");

			while (rs.next()) {
				String email = rs.getString("status");

				if (email.equals("unlocked")) {
					lockedStatus = true;

				} else {
					lockedStatus = false;

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			log4j.warn("Error retrieving status");
		}
		return lockedStatus;

	}//end of statusCheck

	public static String getMD5(String input) {		//this method is to hash the password
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}//end of getMD5

	public boolean checkDups(User refUser) {	//this method is to check if registration has any duplicates
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select email,mobile,nric,employeeid from loginweb where email <> 'admin@admin.com' ");
			while (rs.next()) {

				String mobileRs = rs.getString("mobile");
				String nricRs = rs.getString("nric");
				String emailRs = rs.getString("email");
				Integer empId = rs.getInt("employeeid");

				if (mobileRs.equals(refUser.getMobile())) {
					dupStatus = false;
				} else if (nricRs.equals(refUser.getNric())) {
					dupStatus = false;
				} else if (emailRs.equals(refUser.getEmail())) {
					dupStatus = false;
				} else if (empId == refUser.getEmpid()) {
					dupStatus = false;
				} else {
					dupStatus = true;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dupStatus;
	}//end of checkDups

	public boolean registration(User refUser) {	//this method is to register new users
		try {

			String temppass = refUser.getNric().substring(1, 5) + refUser.getMobile().toString().substring(4, 8);

			String query = "INSERT INTO loginweb(name,nric,gender,dob,address,country,qualification,department,email,mobile,employeeid,certificate,password,status)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,'unlocked')";
			System.out.println(temppass);
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getName());
			preparedStatement.setString(2, refUser.getNric());
			preparedStatement.setString(3, refUser.getGender());
			preparedStatement.setString(4, refUser.getDob());
			preparedStatement.setString(5, refUser.getAddress());
			preparedStatement.setString(6, refUser.getCountry());
			preparedStatement.setString(7, refUser.getQualification());
			preparedStatement.setString(8, refUser.getDepartment());
			preparedStatement.setString(9, refUser.getEmail());
			preparedStatement.setString(10, refUser.getMobile());
			preparedStatement.setInt(11, refUser.getEmpid());
			preparedStatement.setBlob(12, refUser.getImage());
			preparedStatement.setString(13, getMD5(temppass));
			preparedStatement.executeUpdate();
			registrationStatus = true;
			sendMail(refUser.getName(), refUser.getEmail(), temppass);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return registrationStatus;
	}//end of registration

	public ArrayList<String> viewName() {	//this method is to retrieve user name from database for admin

		ArrayList<String> nameA = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" select name from loginweb where serial_no <> 1 ORDER BY serial_no ASC");

			while (rs.next()) {

				String name = rs.getString("name");
				nameA.add(name);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameA;
	}//end of viewName

	public ArrayList<String> viewEmp() {	//this method is to retrieve user Employee ID from database for admin

		ArrayList<String> empA = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select employeeid from loginweb where serial_no <> 1 ORDER BY serial_no");

			while (rs.next()) {

				String empid = rs.getString("employeeid");
				empA.add(empid);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empA;
	}//end of viewEmp

	public ArrayList<String> viewNric() {	//this method is to retrieve user NRIC from database for admin

		ArrayList<String> nricA = new ArrayList<String>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select nric from loginweb where serial_no <> 1 ORDER BY serial_no");

			while (rs.next()) {

				String nric = rs.getString("nric");
				nricA.add(nric);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nricA;
	}//end of viewNric

	public ArrayList<String> viewDepartment() {	//this method is to retrieve user department from database for admin

		ArrayList<String> departA = new ArrayList<String>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select department from loginweb where serial_no <> 1 ORDER BY serial_no");

			while (rs.next()) {

				String depart = rs.getString("department");
				departA.add(depart);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return departA;
	}//end of viewDepartment

	public ArrayList<String> viewEmpName(String refUsername) {	//this method is to retrieve user name from database for admin

		ArrayList<String> nameA = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" select name from loginweb where email = '" + refUsername + "'");

			while (rs.next()) {

				String name = rs.getString("name");
				nameA.add(name);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameA;
	}//end of viewEmpName

	public ArrayList<String> viewEmpID(String refUsername) {	//this method is to select EMPid for normal user

		ArrayList<String> empA = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select employeeid from loginweb where email = '" + refUsername + "'");

			while (rs.next()) {

				String empid = rs.getString("employeeid");
				empA.add(empid);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empA;
	}//end of viewEmpID

	public ArrayList<String> viewEmpNric(String refUsername) { //this method is to select EMP nric for normal user

		ArrayList<String> nricA = new ArrayList<String>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select nric from loginweb where email = '" + refUsername + "'");

			while (rs.next()) {

				String nric = rs.getString("nric");
				nricA.add(nric);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nricA;
	}//end of viewEmpNric

	public ArrayList<String> viewEmpDepartment(String refUsername) {//this method is to select EMP department for normal user

		ArrayList<String> departA = new ArrayList<String>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select department from loginweb where email = '" + refUsername + "'");

			while (rs.next()) {

				String depart = rs.getString("department");
				departA.add(depart);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return departA;
	}//end of viewEmpDepartment

	public void updateUserText(String info, String field, String refUsername) {//this method is to update normal user info 
		try {

			String query = "UPDATE loginweb set " + field + " = '" + info + "' where email = '" + refUsername + "'";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.executeUpdate();
			System.out.println("You done now");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end of updateUserText

	public void updateUserPass(String info, String field, String refUsername) {//this method is to update normal user's password
		try {

			String query = "UPDATE loginweb set " + field + " = '" + getMD5(info) + "' where email = '" + refUsername
					+ "'";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.executeUpdate();
			System.out.println("You done now");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end of updateUserPass


	public void updateEmpImage(InputStream picture, String field, String refUsername) {//this method is to update image for user
		try {

			String query = "UPDATE loginweb set " + field + " = ? where email = '" + refUsername + "'";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setBlob(1, picture);
			preparedStatement.executeUpdate();
			System.out.println("You done now");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end of updateEmpImage

	public ArrayList<String> lockName() {	//this is to select name to show in lock/unlock admin page

		ArrayList<String> nameL = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" select name from loginweb where serial_no <> 1 ORDER BY serial_no ");

			while (rs.next()) {

				String name = rs.getString("name");
				nameL.add(name);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameL;
	}//end of lockName

	public ArrayList<String> lockEmp() {	//this is to select employee id to show in lock/unlock admin page

		ArrayList<String> empL = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select employeeid from loginweb where serial_no <> 1 ORDER BY serial_no");

			while (rs.next()) {

				String empid = rs.getString("employeeid");
				empL.add(empid);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empL;
	}//end of lockEmp

	public ArrayList<String> lockStatus() {//this is to select status to show in lock/unlock admin page

		ArrayList<String> statusL = new ArrayList<String>();

		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select status from loginweb where serial_no <> 1 ORDER BY serial_no");

			while (rs.next()) {

				String status = rs.getString("status");
				statusL.add(status);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return statusL;
	}//end of lockStatus

	public void updateStatus(String refEmp) { //this is to lock/unlock the user's account admin page
		try {

			Statement stmt = conn.createStatement();
			String mainQuery = "SELECT status from loginweb where employeeid =" + refEmp;
			ResultSet rs = stmt.executeQuery(mainQuery);
			System.out.println(refEmp);
			while (rs.next()) {
				String status = rs.getString("status");
				System.out.println(status);
				if (status.equals("locked")) {
					String query1 = "UPDATE loginweb set status='unlocked' where employeeid = " + refEmp;
					preparedStatement = conn.prepareStatement(query1);
					preparedStatement.executeUpdate();
				} else {
					String query2 = "UPDATE loginweb set status='locked' where employeeid = " + refEmp;
					preparedStatement = conn.prepareStatement(query2);
					preparedStatement.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end updateStatus

	public void getForgetPassword(String email) {//this method is to send and update password when user forgets

		try {
			Statement stmt = conn.createStatement();
			String query = "select email from loginweb";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String emailrs = rs.getString("email");
				if (email.equals(emailrs)) {

					String temppass = nric.substring(1, 5) + mobile.substring(4, 8);
					System.out.println(nric);
					System.out.println(mobile);
					sendMail(name, email, temppass);

					try {
						String query2 = "UPDATE loginweb set password= ? where email = '" + email + "'";
						preparedStatement = conn.prepareStatement(query2);
						preparedStatement.setString(1, getMD5(temppass));
						preparedStatement.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}//end of getForgetPassword

	public static void sendMail(String regName, String regEmail, String regPassword) {// this method is for admin to
																						// send temp pass to user's
																						// email

		String to = regEmail; // change accordingly
		String from = "optimum.batch5@gmail.com";
		String passwordEmail = "Optimum2018";

		// Get the session object
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); // SMTP Port

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, passwordEmail);
			}
		};

		Session session = Session.getDefaultInstance(props, auth);

		// compose the message
		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Temp message");
			message.setText("Hello " + regName + "! This is the admin, the following is your temporary password: "
					+ regPassword);

			// Send message
			Transport.send(message);
			System.out.println("Temporary password has been send to " + regEmail + "!");

		} catch (Exception mex) {
			mex.printStackTrace();
		}

	}

	public byte[] getPic(String email) throws SQLException {

		byte[] imgData = null;

		PreparedStatement stmnt = conn.prepareStatement("SELECT profilepic from loginweb WHERE email=?");
		stmnt.setString(1, email);

		ResultSet rs = stmnt.executeQuery();

		while (rs.next()) {
			imgData = rs.getBytes("profilepic");
		}

		return imgData;
	}

}
