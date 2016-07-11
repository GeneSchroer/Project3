package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Location;
import beans.UserAccount;
import utils.LoginUtils;
import utils.MyUtils;
import utils.RepresentativeUtils;

@WebServlet(urlPatterns = {"/representatives/doCreateClient"})
public class DoCreateClientServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DoCreateClientServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		
		
		// get all information from the Create Client form
		String firstName		= request.getParameter("firstName");
		String lastName 		= request.getParameter("lastName"); 
		String userName			= request.getParameter("userName");
		String password			= request.getParameter("password");
		String address 			= request.getParameter("address");
		String city				= request.getParameter("city");
		String state			= request.getParameter("state");
		String zipCodeStr		= request.getParameter("zipCode");
		String telephone		= request.getParameter("telephone");
		String email 			= request.getParameter("email");
		String ratingStr		= request.getParameter("rating");
		String creditCardNumber = request.getParameter("creditCardNumber");
		String idStr			= request.getParameter("id");
		Integer zipCode = null;
		Integer rating = null;
		Integer idParsed = null;
		
		boolean hasError=false;	// will be set to true if there's an error
		
		//list of strings to pass error messages
		String errorStrLastName, errorStrFirstName, errorStrUserName, errorStrPassword, errorStrAddress, 
		errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,
			errorStrEmail, errorStrRating, errorStrCreditCardNumber, errorStrId;
		String regex=null;	//these hold regular expressions
		String regex2=null;
		
		Client client = null;
		
		
		
		// check for errors before adding a Client to the database
		
		
		//LastName
		
		
		regex = "[a-zA-Z]+[\\s[a-zA-z]]*";
		errorStrLastName=null;
		// Throw an error if the name is invalid (filled with letters, symbols, etc.)
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";
		}
		//FirstName
		regex = "[a-zA-Z]+";
		errorStrFirstName=null;
		// repeate for first name
		if(firstName==null || !firstName.matches(regex)){
			hasError=true;
			errorStrFirstName="First Name invalid!";
		}
		
		//User Name
		regex = "[a-zA-Z]+";
		errorStrUserName=null;
		//throw an error if Username is invalid
		if(userName == null || !userName.matches(regex)){
			hasError=true;
			errorStrUserName="Error: User Name Invalid!";
		}
		
		//password
		regex="[^\\s]+";
		errorStrPassword=null;
		//throw an error if the password is invalid or empty
		if(password==null){
			hasError=true;
			errorStrPassword="Error: Password cannot be empty!";
		}
		else if( !password.matches(regex)){
			hasError=true;
			errorStrPassword="Error: Invalid password!";
		}
		
		//Address
		address = request.getParameter("address");
		regex="[0-9]+?[\\s[a-zA-Z_0-9]]{1,}[\\s[a-zA-Z]\\x2E]?";
		
		errorStrAddress=null;
		// throw an error if the address doesn't look like an address
		// (eg. 123 Success St. or the like)
		if(address==null || !address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		
		
		//Zip Code
		//throw error if the zipcode is invalid
		try{
			errorStrZipCode=null;
			zipCode = Integer.parseInt(zipCodeStr);
			//throw error if zipcode is negative or not 5 numbers
			if(zipCode<0 || zipCode>99999){
				hasError=true;
				errorStrZipCode = "Invalid Zip Code";
			}		
		}catch(Exception  e){
			hasError = true;
			errorStrZipCode="Invalid Zip Code!";
		}
		
		//City
		regex = "[[A-Z][a-zA-Z]]+[\\s[A-Z][a-zA-Z]]*";	//regex = One or more words, 
													  	//each beginning with a capital letter 
		errorStrCity=null;
		//throw error if city is not like a city name
		//(eg. New York City or the like)
		if(city==null|| !city.matches(regex)){
			hasError=true;
			errorStrCity = "Error: Invalid City!";
		}
		
		//State
		errorStrState=null;
		// Doesn't do anything now that State is a select tag
		// Don't want to touch the code, though
		if(state==null|| !state.matches(regex)){
			hasError=true;
			errorStrState = "Error: Invalid State!";
		}
		
		// Telephone
		regex="[0-9]{10}";
		regex2="[0-9]{3}\\x2D[0-9]{3}\\x2D[0-9]{4}";
		errorStrTelephone =null;
		// throw an error if the telephone number is not a full phone number
		// (eg. 631-632-0000 or the like)
		if(telephone==null || (!telephone.matches(regex)
									&&!telephone.matches(regex2) ) ){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		
		//Email
		regex="\\w+\\x40\\w+[\\x2E[a-z]+]+"; // this needs to be changed later
		errorStrEmail=null;
		// throw error if not a valid email address
		// eg (person32@something.com)
		if(email==null||!email.matches(regex)){
			hasError=true;
			errorStrEmail="Error: Invalid email!";
		}
		
		//Rating
		//throw error if not a valid number
		try{
			errorStrRating=null;
			rating = Integer.parseInt(request.getParameter("rating"));
			// throw error if not a positive number
			if(rating<0){
				hasError=true;
				errorStrRating="Error: Invalid Naming";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrRating="Invalid Rating!";
		}
		
		//Credit Card Number

		regex="[0-9]{16}";
		regex2="[0-9]{4}[\\2D[0-9]{4}]{3}";
		errorStrCreditCardNumber=null;
		//throw error if not a valid credit card number
		//(eg. 1111-1111-1111-1111 or the like)
		if(creditCardNumber == null || !creditCardNumber.matches(regex)){
			hasError=true;
			errorStrCreditCardNumber="Error: Invalid Credit Card!";
		}
		

		//Id
		regex2="[0-9]{3}\\2D[0-9]{2}\\2D[0-9]{4}";
		//this code doesn't actually work the way it should
		// I'm not touching it for the moment because I don't want to break anything
		if(idStr.matches(regex2)){
			StringTokenizer token = new StringTokenizer(idStr,"-");
			idStr=token.nextToken() + token.nextToken() + token.nextToken();
		}
		//throw error if not a valid number
		try{
			errorStrId=null;
			idParsed = Integer.parseInt(idStr);
			//throw error if not a valid ssn
			//(SSNs start at 001-01-0001
			if(idParsed<1010001 || idParsed>999999999){
				errorStrId="Error: Invalid Id";
			}
			//throw an error if that id already exists
			if(RepresentativeUtils.findPerson(conn, idParsed)!=null){
				hasError=true;
				errorStrId="Error: Id already exists";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrId="Invalid Id!";
		}

		//if everything went well then create a new client
		if(!hasError){
			HttpSession session = request.getSession();
			Integer brokerId = LoginUtils.getId(session);
			//place all the form info into a Client object
			client = new Client(idParsed, lastName, firstName, address, zipCode, telephone, email, rating, creditCardNumber, brokerId);
			// place location info into a Location object
			Location location = new Location(zipCode, city, state);
			//place user info into UserAccount object
			UserAccount user = new UserAccount(userName, password, "Customer", idParsed);
			try{
				
				//use all of that to add a new Client
				RepresentativeUtils.addClient(conn, client, location, user);
				
				
			}
			catch(SQLException e){
				e.printStackTrace();
				hasError = true;
			}
		}
		
		//If there's an error, stay on createClientView
		if(hasError){
		//set errorString and employee then forward to views
			request.setAttribute("errorStrLastName", errorStrLastName);
			request.setAttribute("errorStrFirstName", errorStrFirstName);
			request.setAttribute("errorStrUserName", errorStrUserName);
			request.setAttribute("errorStrPassword", errorStrPassword);
			request.setAttribute("errorStrAddress", errorStrAddress);
			request.setAttribute("errorStrCity", errorStrCity);
			request.setAttribute("errorStrState", errorStrState);
			request.setAttribute("errorStrZipCode", errorStrZipCode);
			request.setAttribute("errorStrTelephone", errorStrTelephone);
			request.setAttribute("errorStrEmail", errorStrEmail);
			request.setAttribute("errorStrRating", errorStrRating);	request.setAttribute("errorStrId", errorStrId);
			request.setAttribute("errorStrCreditCardNumber", errorStrCreditCardNumber);
			request.setAttribute("errorStrId", errorStrId);
				
			request.setAttribute("lastName", lastName);
			request.setAttribute("firstName", firstName);
			request.setAttribute("userName", userName);
			request.setAttribute("password", password);
			request.setAttribute("address", address);
			request.setAttribute("city", city);
			request.setAttribute("state", state);
			request.setAttribute("zipCode", zipCodeStr);
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("rating", ratingStr);
			request.setAttribute("creditCardNumber", creditCardNumber);
			request.setAttribute("id", idStr);
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/representatives/createClientView.jsp");
			dispatcher.forward(request, response);
			}
		
		//If everything worked, redirect to the clientList
		else{
			response.sendRedirect(request.getContextPath() + "/representatives/clientList");
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }
	
}