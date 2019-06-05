package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jetty.server.Authentication.User;

import models.Users;

public class UsersDAO {

	private Scanner scanner;

	/**
	 * @author Jacob Edwards This is a declaration for the database connection,
	 *         declaring it once at the beginning prevents having to repeatedly
	 *         create a connection.
	 * @return conn, this is the connection which has been declared
	 */

	private static Connection getDBConnection() {

		Connection conn = null;

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			String url = "jdbc:sqlite:calories.sqlite";
			conn = DriverManager.getConnection(url);
			return conn;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * The method below is used to insert a user's information into the database
	 * from the form on the webpage.
	 * 
	 * @param u : This is the object sub-created from the Users class to store the
	 *          users information which is being inserted.
	 * @return true when the query is executed, this results in a user being pulled
	 *         from the database
	 * @throws SQLException if there is an error with the SQL query implemented.
	 */

	public Boolean insertUser(Users u) throws SQLException {
		// Initialisation of variables which will be used in this method
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		String query1 = "INSERT INTO users ( " + "firstname," + "surname," + "username," + "password," + "email," + 
		"calories," + "activitylevel," + "height," + "weight," + "age," +"gender) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

		try {
			conn = getDBConnection();
			conn.setAutoCommit(false);
			System.out.println("Database opened successfully");
			preparedStatement = conn.prepareStatement(query1);
			// Each ? in the query represents a prepared statement in order, which is
			// replaced by the corresponding number below.
			preparedStatement.setString(1, u.getFirstname());
			preparedStatement.setString(2, u.getSurname());
			preparedStatement.setString(3, u.getUsername());
			preparedStatement.setString(4, u.getPassword());
			preparedStatement.setString(5, u.getEmail());
			preparedStatement.setInt(6, u.getCalories());
			preparedStatement.setDouble(7, u.getActivitylevel());
			preparedStatement.setDouble(8, u.getHeight());
			preparedStatement.setDouble(9, u.getWeight());
			preparedStatement.setInt(10, u.getAge());
			preparedStatement.setBoolean(11, u.getGender());

			preparedStatement.executeUpdate();
			// Execution of the query above with the use of prepared statements
			preparedStatement.close();
			conn.commit();
			// Error message checking if there is a problem with the query

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			// The variables are then closed, ready to be used in another method so they do
			// not overload.
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("-------");
		System.out.println("User successfully created, reload program to display results.");
		return true;
	}

	/**
	 * The method below is used to retrieve a user's corresponding API from the
	 * database.
	 * 
	 * @param username, this is the username which the user has input.
	 * @param password, this is the password which the user has input.
	 * @return apiUser when the query is executed, this results in a user
	 *         information being pulled from the database
	 * @throws SQLException if there is an error with the SQL query implemented.
	 */

	public Users getAccount(String username, String password) throws SQLException {
		// Initialisation of variables which will be used in this method
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		Users userAcc = null;
		ResultSet rs = null;

		String query = "SELECT * FROM users where username = ? AND password = ? ";

		try {
			conn = getDBConnection();
			conn.setAutoCommit(false);
			System.out.println("Search for User Account -database successfully opened");
			System.out.println("-------");

			preparedStatement = conn.prepareStatement(query);

			// The ? in the query represents a prepared statement in order, which is
			// replaced by the corresponding number below.

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			rs = preparedStatement.executeQuery();
			// Execution of the query above
			// The while loop below displays the returned user's information from the
			// database
			while (rs.next()) {
				String usernameAcc = rs.getString("username");
				String passwordAcc = rs.getString("password");
				String firstname = rs.getString("firstname");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				Integer calories = rs.getInt("calories");
				Double activitylevel = rs.getDouble("activitylevel");
				Double height = rs.getDouble("height");
				Double weight = rs.getDouble("weight");
				Boolean gender = rs.getBoolean("gender");
				Integer age = rs.getInt("age");


				userAcc = new Users(usernameAcc, passwordAcc, firstname, surname, email, calories,
						activitylevel, height, weight, gender, age);

				System.out.print(userAcc);
			}

			// Error message checking if there is a problem with the query

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			// The variables are then closed, ready to be used in another method so they do
			// not overload.
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("-------");
		System.out.println("Search operation completed, reload program to display results.");
		return userAcc;
	}
	
	/**
	 * The method below is used to retrieve a user's corresponding API from the
	 * database.
	 * 
	 * @param username, this is the username which the user has input.
	 * @param password, this is the password which the user has input.
	 * @return apiUser when the query is executed, this results in a user
	 *         information being pulled from the database
	 * @throws SQLException if there is an error with the SQL query implemented.
	 */

	public Users getUser(String username) throws SQLException {
		// Initialisation of variables which will be used in this method
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		Users userAcc = null;
		ResultSet rs = null;

		String query = "SELECT * FROM users where username = ?";

		try {
			conn = getDBConnection();
			conn.setAutoCommit(false);
			System.out.println("Search for User Account -database successfully opened");
			System.out.println("-------");

			preparedStatement = conn.prepareStatement(query);

			// The ? in the query represents a prepared statement in order, which is
			// replaced by the corresponding number below.

			preparedStatement.setString(1, username);

			rs = preparedStatement.executeQuery();
			// Execution of the query above
			// The while loop below displays the returned user's information from the
			// database
			while (rs.next()) {
				String usernameAcc = rs.getString("username");
				String passwordAcc = rs.getString("password");
				String firstname = rs.getString("firstname");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				Integer calories = rs.getInt("calories");
				Double activitylevel = rs.getDouble("activitylevel");
				Double height = rs.getDouble("height");
				Double weight = rs.getDouble("weight");
				Boolean gender = rs.getBoolean("gender");
				Integer age = rs.getInt("age");


				userAcc = new Users(usernameAcc, passwordAcc, firstname, surname, email, calories,
						activitylevel, height, weight, gender, age);

			}
			System.out.print(userAcc);


			// Error message checking if there is a problem with the query

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			// The variables are then closed, ready to be used in another method so they do
			// not overload.
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("-------");
		System.out.println("Search operation completed, reload program to display results.");
		return userAcc;
	}
	
	
	/**
	 * The method below executes an update statement to edit an existing vehicle's
	 * information in the database.
	 * 
	 * @param Update   : This is the object sub-created from the Vehicle class to
	 *                 store all the vehicles in the database.
	 * @param chosenID : This variable which is parsed in the method is the user
	 *                 input of which vehicle ID they have chosen to update.
	 * @return true when the query is executed, this results in an existing
	 *         vehicle's information being updated in the database.
	 * @throws SQLException if there is an error with the SQL query implemented.
	 */

	public Boolean updateVehicle(Users Update, String username) throws SQLException {
		// Initialisation of variables which will be used in this method

		Connection conn = null;
		PreparedStatement preparedStatement = null;

		scanner = new Scanner(System.in);

		String query1 = "UPDATE users SET Firstname= ? , " + "Surname=? ," + "Username=? ," + "Password=? ,"
				+ "Activitylevel=? ," + "Calories=? ," + "Email=? ," + "Height=? ," + "Weight=? ," + "Age=? ,"
				+ "Gender=? " + "where Username = ?";

		try {
			conn = getDBConnection();
			conn.setAutoCommit(false);
			System.out.println("Update operation - database successfully opened");
			preparedStatement = conn.prepareStatement(query1);

			// Each ? in the query represents a prepared statement in order, which is
			// replaced by the corresponding number below.

			preparedStatement.setString(1, Update.getFirstname());
			preparedStatement.setString(2, Update.getSurname());
			preparedStatement.setString(3, Update.getUsername());
			preparedStatement.setString(4, Update.getPassword());
			preparedStatement.setDouble(5, Update.getActivitylevel());
			preparedStatement.setInt(6, Update.getCalories());
			preparedStatement.setString(7, Update.getEmail());
			preparedStatement.setDouble(8, Update.getHeight());
			preparedStatement.setDouble(9, Update.getWeight());
			preparedStatement.setInt(10, Update.getAge());
			preparedStatement.setBoolean(11, Update.getGender());
			preparedStatement.setString(12, Update.getUsername());

			preparedStatement.executeUpdate();
			// Execution of the query above with the use of prepared statements

			conn.commit();
			preparedStatement.close();
			// Error message checking if there is a problem with the query

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			// The variables are then closed, ready to be used in another method so they do
			// not overload.
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("-------");
		System.out.println("Update operation successfully done, reload program to display results.");
		return true;
	}

}
