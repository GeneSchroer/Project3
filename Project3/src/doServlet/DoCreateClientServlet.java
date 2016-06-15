package doServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Client;
import beans.Location;
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
		
		
		//declare and define variables
		String firstName		= request.getParameter("lastName");
		String lastName 		= request.getParameter("firstName"); 
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
		Integer id = null;
		
		boolean hasError=false;
		String errorStrLastName, errorStrFirstName, errorStrAddress, 
		errorStrCity, errorStrState, errorStrZipCode, errorStrTelephone,
			errorStrEmail, errorStrRating, errorStrCreditCardNumber, errorStrId;
		String regex=null;	//these hold regular expressions
		String regex2=null;
		
		Client client = null;
		
		// check for errors before adding a Client to the database
		
		
		//LastName
		
		regex="[a-zA-Z]+";
		errorStrLastName=null;
		if(lastName==null || !lastName.matches(regex)){
			hasError=true;
			errorStrLastName="Last Name invalid!";
		}
		//FirstName
		errorStrFirstName=null;
		if(firstName==null || !firstName.matches(regex)){
			hasError=true;
			errorStrFirstName="First Name invalid!";
		}
		
		//Address
		address = request.getParameter("address");
		regex="[0-9]+?[\\s[a-zA-Z]]{1,}[\\s[a-zA-Z]\\x2E]?";
		errorStrAddress=null;
		if(address==null || !address.matches(regex)){
			hasError=true;
			errorStrAddress="Address invalid!";
		}
		
		//Zip Code
		try{
			errorStrZipCode=null;
			zipCode = Integer.parseInt(zipCodeStr);
			if(zipCode<0 || zipCode>99999){
				hasError=true;
				errorStrZipCode = "Invalid Zip Code";
			}		
		}catch(Exception  e){
			hasError = true;
			errorStrZipCode="Invalid Zip Code!";
		}
		
		//City
		regex = "[[A-Z][a-zA-z]]+[\\s[A-Z][a-zA-Z]]*";	//regex = One or more words, 
													  	//each beginning with a capital letter 
		errorStrCity=null;
		if(city==null|| !city.matches(regex)){
			hasError=true;
			errorStrCity = "Error: Invalid City!";
		}
		
		//State
		errorStrState=null;
		if(state==null|| !state.matches(regex)){
			hasError=true;
			errorStrState = "Error: Invalid State!";
		}
		
		// Telephone
		regex="[0-9]{10}";
		regex2="[0-9]{3}\\x2D[0-9]{3}\\x2D[0-9]{4}";
		errorStrTelephone =null;
		if(telephone==null || (!telephone.matches(regex)
									&&!telephone.matches(regex2) ) ){
			hasError=true;
			errorStrTelephone="Invalid Telephone Number!";
		}
		
		//Email
		regex="\\w+\\x40\\w+[\\x2E[a-z]+]+"; // this needs to be changed later
		errorStrEmail=null;
		if(email==null||!email.matches(regex)){
			hasError=true;
			errorStrEmail="Error: Invalid email!";
		}
		
		//Rating
		try{
			errorStrRating=null;
			rating = Integer.parseInt(request.getParameter("rating"));
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
		errorStrCreditCardNumber=null;
		if(creditCardNumber == null || !creditCardNumber.matches(regex)){
			hasError=true;
			errorStrCreditCardNumber="Error: Invalid Credit Card!";
		}
		

		//Id
		try{
			errorStrId=null;
			id = Integer.parseInt(request.getParameter("id"));
			if(id<0 || id>999999999){
				errorStrId="Error: Invalid Id";
			}
			if(RepresentativeUtils.findPerson(conn, id)!=null){
				hasError=true;
				errorStrId="Error: Id already exists";
			}
		}
		catch(Exception e){
			hasError=true;
			errorStrId="Invalid Id!";
		}

		if(!hasError){
			client = new Client(id, firstName, lastName, address, zipCode, telephone, email, rating, creditCardNumber);
			Location location = new Location(zipCode, city, state);
			try{
				RepresentativeUtils.addClient(conn, client, location);
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