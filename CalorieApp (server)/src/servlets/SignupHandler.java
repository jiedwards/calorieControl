package servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Users;
import models.UsersDAO;

/**
 * This servlet contains the functionality required to add new user accounts to
 * the database.
 * 
 * @author Jacob Edwards
 */

public class SignupHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Method is used to retrieve the necessary information to insert a new user,
	 * 
	 * @author Jacob Edwards
	 * @throws ServletException an exception a servlet will commonly throw when it
	 *                          encounters a problem
	 * @throws IOException      throws up an error message if there is a problem
	 *                          with an input or output
	 * @param HttpServletRequest  provides request information for HTTP servlets.
	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
	 *                            response. For example, it has methods to access
	 *                            HTTP headers and cookies.
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("signup.jsp");
		view.forward(req, resp);
	}

	/*
	 * The function below produces a random alpha-numeric string from the characters
	 * specified. The function also ensures that the output is not longer than 18
	 * characters.
	 */

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	/**
	 * This doPost method parses the necessary information into the insert method
	 * specified in in the DAO to execute the insert query which adds a new user.
	 * 
	 * @author Jacob Edwards
	 * @throws ServletException an exception a servlet will commonly throw when it
	 *                          encounters a problem
	 * @throws IOException      throws up an error message if there is a problem
	 *                          with an input or output
	 * @param HttpServletRequest  provides request information for HTTP servlets.
	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
	 *                            response. For example, it has methods to access
	 *                            HTTP headers and cookies.
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UsersDAO dao = new UsersDAO();

		String firstname = (String) req.getParameter("firstname");
		String lastname = (String) req.getParameter("lastname");
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("password");
		String email = (String) req.getParameter("email");

		/*
		 * The passwords in the database for all user's are hashed, therefore any
		 * information sent through must also be encrypted.
		 */

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] messageDigest = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String pwhash = number.toString(16);
		System.out.println(pwhash);

		/*
		 * The method insertUser below is declared in the DAO class and contains the
		 * code necessary to insert a new user into the database.
		 */

		Users u = new Users(firstname, lastname, username, pwhash, email, 0, 0.0, 0.0, 0.0, true, 0);
		try {
			boolean done = dao.insertUser(u);
			System.out.println(done);
			if (done) {
				resp.sendRedirect("signup");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
