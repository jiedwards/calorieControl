package models;

/**
 * This class is used to control and call upon the variables necessary for
 * logging into the system. The only notable variables are username, password
 * and apikey getter and setter methods are made for each in order to call upon
 * these values.
 * 
 * @author Jacob
 * @return password if getPassword method is called.
 * @return username if the getUsername method is called.
 * @return apikey if the getApikey method is called.
 * 
 * @version 1.0
 */

public class Users {

	private String username;
	private String password;
	private String firstname;
	private String surname;
	private String email;
	private Integer calories;
	private Double activitylevel;
	private Double height;
	private Double weight;
	private Boolean gender;
	private Integer age;

	public Users(String username, String password, String firstname, String surname, String email, Integer calories,
			Double activitylevel, Double height, Double weight, Boolean gender, Integer age) {

		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.calories = calories;
		this.activitylevel = activitylevel;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.age = age;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the calories
	 */
	public Integer getCalories() {
		return calories;
	}

	/**
	 * @param calories the calories to set
	 */
	public void setCalories(Integer calories) {
		this.calories = calories;
	}

	/**
	 * @return the activitylevel
	 */
	public Double getActivitylevel() {
		return activitylevel;
	}

	/**
	 * @param activitylevel the activitylevel to set
	 */
	public void setActivitylevel(Double activitylevel) {
		this.activitylevel = activitylevel;
	}

	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the gender
	 */
	public Boolean getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + ", firstname=" + firstname + ", surname="
				+ surname + ", email=" + email + ", calories=" + calories + ", activitylevel=" + activitylevel
				+ ", height=" + height + ", weight=" + weight + ", gender=" + gender + ", age=" + age + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	

}
