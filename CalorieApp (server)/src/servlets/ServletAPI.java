//package servlets;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.google.gson.Gson;
//import models.Users;
//import models.Vehicle;
//import models.VehicleDAO;
//
///**
// * This Java Class servlet specifically interacts with the database and assists
// * in the execution REST requests.
// * 
// * @author Jacob Edwards
// */
//
//public class ServletAPI extends HttpServlet {
//
//	/* Declaration of global variables required throughout the methods below. */
//
//	public String API;
//	public String postType;
//
//	private static final long serialVersionUID = 1L;
//	VehicleDAO dao = new VehicleDAO();
//	Gson gson = new Gson();
//	Writer writer = new PrintWriter(System.out);
//
//	/**
//	 * Method is used to return the arraylist of all the vehicles, accessible only
//	 * with a verified API key.
//	 * 
//	 * @author Jacob Edwards
//	 * @throws ServletException an exception a servlet will commonly throw when it
//	 *                          encounters a problem
//	 * @throws IOException      throws up an error message if there is a problem
//	 *                          with an input or output
//	 * @param HttpServletRequest  provides request information for HTTP servlets.
//	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
//	 *                            response. For example, it has methods to access
//	 *                            HTTP headers and cookies.
//	 */
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		/*
//		 * The API variable is set to the value sent in the URL for the GET request.
//		 */
//
//		API = req.getParameter("Authorization");
//		ArrayList<Vehicle> allCons = null;
//
//		/*
//		 * The method checkAPI below is declared in the DAO class, it contains the code
//		 * necessary to verify whether the API key supplied matches an existing entry in
//		 * the database. If yes, the authorisation is approved and requests can be made,
//		 * alternatively the request will be denied.
//		 */
//
//		try {
//			Users done = dao.checkAPI(API);
//			System.out.println(done);
//			;
//			if (done != null) {
//				System.out.println("Success");
//				System.out.println(req + " / " + resp);
//				try {
//
//					/*
//					 * The method getAllVehicles below is declared in the DAO class and contains the
//					 * code necessary to retrieve all the vehicle information from the database.
//					 */
//
//					allCons = dao.getAllVehicles();
//					System.out.println(allCons);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			} else {
//
//				/*
//				 * If the API key supplied is wrong, the status code is changed and the server
//				 * returns an unauthorised API key server error.
//				 */
//
//				resp.setStatus(403);
//				writer = resp.getWriter();
//				writer.write("No Access. Supply a valid API Key with your request");
//				System.out.println("Failed " + resp);
//				writer.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		resp.setContentType("application/json");
//		resp.getWriter().write(gson.toJson(allCons));
//	}
//
//	/**
//	 * Method is used to delete specific vehicles from the database, the request is
//	 * only executable when the request is supplied with a verified API key.
//	 * 
//	 * @author Jacob Edwards
//	 * @throws ServletException an exception a servlet will commonly throw when it
//	 *                          encounters a problem
//	 * @throws IOException      throws up an error message if there is a problem
//	 *                          with an input or output
//	 * @param HttpServletRequest  provides request information for HTTP servlets.
//	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
//	 *                            response. For example, it has methods to access
//	 *                            HTTP headers and cookies.
//	 */
//
//	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		/*
//		 * The API variable is set to the value sent in the header for the DELETE
//		 * request.
//		 */
//
//		API = req.getHeader("Authorization");
//
//		/*
//		 * The method checkAPI below is declared in the DAO class, it contains the code
//		 * necessary to verify whether the API key supplied matches an existing entry in
//		 * the database. If yes, the authorisation is approved and requests can be made,
//		 * alternatively the request will be denied.
//		 */
//
//		try {
//			Users done = dao.checkAPI(API);
//			System.out.println(done);
//			;
//			if (done != null) {
//
//				/*
//				 * The vehicle id variable is set to the value sent in the URL from the delete
//				 * request.
//				 */
//
//				int vehicle_id = Integer.parseInt(req.getParameter("vehicle_id"));
//
//				try {
//
//					/*
//					 * The method deleteVehicle below is declared in the DAO class and contains the
//					 * code necessary to delete a specific vehicle from the database.
//					 */
//
//					boolean deleteDone = dao.deleteVehicle(vehicle_id);
//					System.out.println(deleteDone);
//					if (deleteDone) {
//						System.out.println("WORKS!");
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			} else {
//
//				/*
//				 * If the API key supplied is wrong, the status code is changed and the server
//				 * returns an unauthorised API key server error.
//				 */
//
//				resp.setStatus(403);
//				writer = resp.getWriter();
//				writer.write("No Access. Supply a valid API Key with your request");
//				System.out.println("Failed " + resp);
//				writer.close();
//			}
//		} catch (SQLException e) {
//			System.out.println("Failed");
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Method is used to insert vehicles into the database, the request is only
//	 * executable when the request is supplied with a verified API key.
//	 * 
//	 * This doPost also handles the method which verifies the API key which is being
//	 * supplied from the App.
//	 * 
//	 * @author Jacob Edwards
//	 * @throws ServletException an exception a servlet will commonly throw when it
//	 *                          encounters a problem
//	 * @throws IOException      throws up an error message if there is a problem
//	 *                          with an input or output
//	 * @param HttpServletRequest  provides request information for HTTP servlets.
//	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
//	 *                            response. For example, it has methods to access
//	 *                            HTTP headers and cookies.
//	 */
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		/*
//		 * This doPost is an example of a method which can handle multiple post
//		 * requests, the different requests are categorised by a property set in the
//		 * header. Depending on the contents of the property, different requests are
//		 * executed.
//		 */
//
//		postType = req.getHeader("postType");
//
//		if (postType.contentEquals("apiCheck")) {
//
//			/*
//			 * The API variable is being set to the parameter sent through in the HTTP
//			 * request.
//			 */
//
//			String API = req.getParameter("json");
//
//			/*
//			 * The method checkAPI below is declared in the DAO class, it contains the code
//			 * necessary to verify whether the API key supplied matches an existing entry in
//			 * the database.
//			 */
//
//			try {
//				Users done = dao.checkAPI(API);
//				System.out.println(done);
//				;
//				if (done != null) {
//					System.out.println("Success");
//
//				} else {
//
//					/*
//					 * If the API key supplied is wrong, the status code is changed and the server
//					 * returns an error.
//					 */
//					resp.setStatus(403);
//					System.out.println("Failed");
//
//				}
//			} catch (SQLException e) {
//				System.out.println("Failed");
//				e.printStackTrace();
//			}
//
//			resp.setContentType("application/json");
//			resp.getWriter().write(gson.toJson(API));
//
//		} else {
//
//			/*
//			 * The API variable is set to the value sent in the header for the Insert
//			 * request.
//			 */
//
//			API = req.getHeader("Authorization");
//
//			/*
//			 * The method checkAPI below is declared in the DAO class, it contains the code
//			 * necessary to verify whether the API key supplied matches an existing entry in
//			 * the database. If yes, the authorisation is approved and requests can be made,
//			 * alternatively the request will be denied.
//			 */
//
//			try {
//				Users done = dao.checkAPI(API);
//				System.out.println(done);
//				;
//				if (done != null) {
//
//					/*
//					 * The vehicle v object is set to the values of the object which was sent in the
//					 * request
//					 */
//
//					Vehicle v = gson.fromJson(req.getParameter("json"), Vehicle.class);
//					try {
//
//						/*
//						 * The method insertVehicle below is declared in the DAO class, it contains the
//						 * code necessary to insert a vehicle into the database by passing in a vehicle
//						 * object.
//						 */
//
//						boolean insertDone = dao.insertVehicle(v);
//
//						if (insertDone) {
//							System.out.println(insertDone);
//						}
//					} catch (SQLException e) {
//						e.printStackTrace();
//
//					}
//
//					resp.setContentType("application/json");
//					resp.getWriter().write(gson.toJson(v));
//				} else {
//
//					/*
//					 * If the API key supplied is wrong, the status code is changed and the server
//					 * returns an unauthorised API key server error.
//					 */
//
//					resp.setStatus(403);
//					writer = resp.getWriter();
//					writer.write("No Access. Supply a valid API Key with your request");
//					System.out.println("Failed " + resp);
//					writer.close();
//				}
//			} catch (SQLException e) {
//				System.out.println("Failed");
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * Method is used to update specific vehicles in the database, the request is
//	 * only executable when the request is supplied with a verified API key.
//	 * 
//	 * @author Jacob Edwards
//	 * @throws ServletException an exception a servlet will commonly throw when it
//	 *                          encounters a problem
//	 * @throws IOException      throws up an error message if there is a problem
//	 *                          with an input or output
//	 * @param HttpServletRequest  provides request information for HTTP servlets.
//	 * @param HttpServletResponse provide HTTP-specific functionality in sending a
//	 *                            response. For example, it has methods to access
//	 *                            HTTP headers and cookies.
//	 */
//
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		/*
//		 * The API variable is set to the value sent in the header for the DELETE
//		 * request.
//		 */
//
//		API = req.getHeader("Authorization");
//
//		/*
//		 * The method checkAPI below is declared in the DAO class, it contains the code
//		 * necessary to verify whether the API key supplied matches an existing entry in
//		 * the database. If yes, the authorisation is approved and requests can be made,
//		 * alternatively the request will be denied.
//		 */
//
//		try {
//			Users done = dao.checkAPI(API);
//			System.out.println(done);
//			;
//			if (done != null) {
//				System.out.println("Success");
//
//				/*
//				 * The vehicle v object is set to the values of the object which was sent in the
//				 * request
//				 */
//
//				Vehicle v = gson.fromJson(req.getParameter("json"), Vehicle.class);
//
//				try {
//
//					/*
//					 * The method updateVehicle below is declared in the DAO class and contains the
//					 * code necessary to update a specific vehicle from the database by passing in
//					 * an updated vehicle object.
//					 */
//
//					boolean updateDone = dao.updateVehicle(v, v.getVehicle_id());
//					if (updateDone) {
//						System.out.println("Vehicle Successfully Updated");
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//					System.out.println("Vehicle Failed To Update.");
//
//				}
//
//				resp.setContentType("application/json");
//				resp.getWriter().write(gson.toJson(v));
//			} else {
//
//				/*
//				 * If the API key supplied is wrong, the status code is changed and the server
//				 * returns an unauthorised API key server error.
//				 */
//
//				resp.setStatus(403);
//				writer = resp.getWriter();
//				writer.write("No Access. Supply a valid API Key with your request");
//				System.out.println("Failed " + resp);
//				writer.close();
//			}
//		} catch (SQLException e) {
//			System.out.println("Failed");
//			e.printStackTrace();
//		}
//	}
//}
