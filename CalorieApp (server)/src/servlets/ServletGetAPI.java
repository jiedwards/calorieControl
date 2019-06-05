package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Authentication.User;

import com.google.gson.Gson;

import models.UsersDAO;
import models.Users;

/**
 * This servlet handles the functionality for a search operation to be executed
 * in order to retrieve a user's corresponding API key from the database.
 * 
 * @author Jacob Edwards
 */

public class ServletGetAPI extends HttpServlet {

	private static final long serialVersionUID = 1L;
	UsersDAO dao = new UsersDAO();
	models.Users Users = null;
	Gson gson = new Gson();
	public String postType;
	public String reqType;
	Writer writer = new PrintWriter(System.out);

	
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	Users done = null;
	
	reqType = req.getParameter("username");
	

	System.out.println("WORKING");
	System.out.println(postType);


			Users u = gson.fromJson(req.getParameter("json"), Users.class);

			try {

				done = dao.getUser(reqType);

				if (done != null) {
					System.out.println("Successful Request: Username supplied is "+ reqType);
					resp.setStatus(200);
					writer.close();
				} 
				else {
					System.out.println("FAILED");
					resp.setStatus(403);
					writer = resp.getWriter();
					writer.write("No Access. Supply a valid API Key with your request");
					System.out.println("Failed " + resp);
					writer.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			resp.setContentType("application/json");
			resp.getWriter().write(gson.toJson(done));

	}


	/**
	 * Upon recieving the inputted credentials, they are MD5 hashed before they are
	 * used to execute the function. This doPost method parses the user entered
	 * login information into the getAPI method in the DAO to execute a search query
	 * which will then retrieve their unique corresponding API key if it exists.
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

		/*
		 * The username and password variables are being set to the parameters sent
		 * through in the HTTP request.
		 */
		
		postType = req.getHeader("postType");

		if (postType.contentEquals("newUser")) {


					Users u = gson.fromJson(req.getParameter("json"), Users.class);
					try {

						/*
						 * The method insertVehicle below is declared in the DAO class, it contains the
						 * code necessary to insert a vehicle into the database by passing in a vehicle
						 * object.
						 */
						System.out.println("TESTING" + u);


						boolean insertDone = dao.insertUser(u);

						if (insertDone) {
							System.out.println(insertDone);
							resp.setStatus(200);
							System.out.println("TESTING" + u);

						}
						else {
							resp.setStatus(403);
							System.out.println("TESTING" + u);

						}
					} catch (SQLException e) {
						e.printStackTrace();

					}

					resp.setContentType("application/json");
					resp.getWriter().write(gson.toJson(u));

		} 
		if (postType.contentEquals("login")) {
Users u = gson.fromJson(req.getParameter("json"), Users.class);
			
			String username = u.getUsername();
			String password = u.getPassword();
			
			System.out.println("TESTERSON7" + username);

			/*
			 * The password in the database for all user's are hashed, therefore any
			 * information sent through to match existing data must also be encrypted.
			 */


			/*
			 * The method getAPI below is declared in the DAO class and contains the code
			 * necessary to retrieve the API key from the database if it exists, when
			 * supplied with the correct information.
			 */

			try {
				Users done = dao.getAccount(username, password);
				
				if (done != null) {
					System.out.println("WORKING");
					resp.setStatus(200);
				} 
				else {
					System.out.println("FAILED");
					resp.setStatus(403);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(403);
			}

			
			resp.setContentType("application/json");
			resp.getWriter().write(gson.toJson(u));
		}

	}
	
	/**
	 * Method is used to update specific vehicles in the database, the request is
	 * only executable when the request is supplied with a verified API key.
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

	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/*
		 * The method checkAPI below is declared in the DAO class, it contains the code
		 * necessary to verify whether the API key supplied matches an existing entry in
		 * the database. If yes, the authorisation is approved and requests can be made,
		 * alternatively the request will be denied.
		 */

		System.out.println("request recieved");

				Users u = gson.fromJson(req.getParameter("json"), Users.class);

				System.out.println("request recieved" + u);

				try {

					/*
					 * The method updateVehicle below is declared in the DAO class and contains the
					 * code necessary to update a specific vehicle from the database by passing in
					 * an updated vehicle object.
					 */

					boolean updateDone = dao.updateVehicle(u, u.getUsername());
					if (updateDone) {
						System.out.println("User Successfully Updated");
						resp.setStatus(200);

					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("User Failed To Update.");
					resp.setStatus(403);


				}

				resp.setContentType("application/json");
				resp.getWriter().write(gson.toJson(u));
			
		
	}

}


