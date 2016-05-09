package tasklistwebservice;

import java.util.ArrayList;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class tasklistService {

    private ArrayList<String> sessions = null;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public static void main (String [] args){
    	tasklistService service = new tasklistService();
    	try {
			//service.registerUser("user1", "pass1");
			//service.registerUser("user2", "pass1");
			service.loginUser("user1", "pass1");
			service.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public tasklistService() {
    	sessions = new ArrayList<String> ();
    	
    	try {
    	      // This will load the MySQL driver, each DB has its own driver
    	      Class.forName("com.mysql.jdbc.Driver");
    	      // Setup the connection with the DB
    	      System.out.println("Connecting to a selected database...");
    	      connect = DriverManager
    	          .getConnection("jdbc:mysql://localhost/taskList?"
    	              + "user=taskList&password=taskList");
    	      System.out.println("Connected database successfully...");
    	} catch (Exception e) {
    	      System.err.println(e);
        } 
    }

    //This function adds a new user to the database.
    //Throws Exception if user is already in use or if you pass a null argument.
    //Return the session token if the user has been created correctly.
    public String registerUser(String username, String password) throws Exception {
    	String sessionToken = null;
    	if (username != null && password != null) {
    		try {
    			sessionToken = UUID.randomUUID().toString().replaceAll("-", "");
    			preparedStatement = connect
    		          .prepareStatement("INSERT INTO taskList.users VALUES (?,?,?);");
    			preparedStatement.setString(1, username);
    			preparedStatement.setString(2, password);
    			preparedStatement.setString(3, sessionToken);
    			preparedStatement.executeUpdate();
    		} catch (MySQLIntegrityConstraintViolationException e){
    			throw new Exception("Username is already in use.");
    		} finally {
    			if (preparedStatement != null)
    				preparedStatement.close();
    		}    		
    		System.out.println("\"" + username + "\" has been registered.");
    		sessions.add(sessionToken);
	    } else {
	    	throw new Exception("You must provide a valid username and password.");
	    }
    	
    	return sessionToken;
    }

    //This function lets the user to login in the system.
    //Throws Exception if user is already in use or if you pass a null argument.
    //Return the session token if the user has been created correctly.
    public String loginUser(String username, String password) throws Exception {
    	String sessionToken = null;
    	String user = null;
    	String pass = null;
    	if (username != null && password != null) {
    		try {
    			preparedStatement = connect
    		          .prepareStatement("select * from taskList.users where username=?");
    			preparedStatement.setString(1, username);
    			resultSet = preparedStatement.executeQuery();
    			if(resultSet.next() == false){
    				//The specified username doesn't exist.
    				throw new Exception("The specified username doesn't exist.");
    			} else {
    				user = resultSet.getString("username");
    				pass = resultSet.getString("password");
    				if (pass.equals(password) && user.equals(username)){
    					sessionToken = UUID.randomUUID().toString().replaceAll("-", "");
    					preparedStatement = connect
    		    		          .prepareStatement("UPDATE users SET token=? WHERE username=?");
    					preparedStatement.setString(1, sessionToken);
    					preparedStatement.setString(2, username);
    					preparedStatement.executeUpdate();
    				} else {
    					System.out.println("Incorrect password for user " + username);
    					throw new Exception("The specified password is not correct.");
    				}
    			}
    			sessions.add(sessionToken);
    			System.out.println("\"" + username + "\" has been logged.");
    			resultSet.beforeFirst();
    			writeUsers(resultSet);
    		} catch (MySQLIntegrityConstraintViolationException e){
    			throw new Exception("Username is already in use.");
    		} finally {
    			if (preparedStatement != null )
    				preparedStatement.close();
    			if (resultSet != null)
    				resultSet.close();
    		}    		
	    } else {
	    	throw new Exception("You must provide a valid username and password.");
	    }
    	
    	return sessionToken;
    }
    
    //This method check if the session with the 
    //given session token is opened.
    private Boolean isSessionOpened(String token) {
    	Boolean result = false;
    	for (String j: sessions)
    		if (j.equals(token))
    			result = true;
    	return result;
    }
    
    private void writeUsers(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
          String user = resultSet.getString("username");
          String pass = resultSet.getString("password");
          String token = resultSet.getString("token");
          System.out.println("Username: " + user);
          System.out.println("Password: " + pass);
          System.out.println("Session Token: " + token);
        }
      }
    
 // You need to close the resultSet
    private void close() {
      try {
        if (resultSet != null) {
          resultSet.close();
        }

        if (statement != null) {
          statement.close();
        }

        if (connect != null) {
          connect.close();
        }
      } catch (Exception e) {

      }
    }
}