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

    private ArrayList<Session> sessions = null;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public static void main (String [] args){
        tasklistService service = new tasklistService();
        try {
            service.registerUser("user2", "pass2");
            //service.registerUser("user2", "pass1");
            String token = service.loginUser("user2", "pass2");
            service.newTaskList("newtasklist", token);
            service.getLists(token);
            service.deleteTaskList("newtasklist", token);
            service.close();
            while(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public tasklistService() {
        sessions = new ArrayList<Session> ();
        
        try {
              ShutdownHelper shutdownHelper = new ShutdownHelper(this);
              Runtime.getRuntime().addShutdownHook(shutdownHelper);
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
              close();
        } 
    }

    // This function adds a new user to the database.
    // Throws Exception if user is already in use or if you pass a null argument.
    // Return the session token if the user has been created correctly.
    public String registerUser(String username, String password) throws Exception {
        String sessionToken = null;
        if (username != null && password != null) {
            try {
                sessionToken = UUID.randomUUID().toString().replaceAll("-", "");
                //Add new user to the database.
                preparedStatement = connect
                      .prepareStatement("INSERT INTO taskList.users VALUES (?,?,?);");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, sessionToken);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                
                //Add table to support new user lists.
                String query = "CREATE TABLE taskList." + username 
                        + "_taskLists (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, listname VARCHAR(30) NOT NULL);";
                preparedStatement = connect
                      .prepareStatement(query);
                preparedStatement.executeUpdate();
            } catch (MySQLIntegrityConstraintViolationException e){
                throw new Exception("Username is already in use.");
            } finally {
                if (preparedStatement != null)
                    preparedStatement.close();
            }           
            System.out.println(username + " has been registered.");
            sessions.add(new Session(username, sessionToken));
        } else {
            throw new Exception("You must provide a valid username and password.");
        }
        
        return sessionToken;
    }

    //This function lets the user to login in the system.
    //Throws Exception if the given user/password is incorrect or if you pass a null argument.
    //Return the session token if the user has been logged correctly.
    public String loginUser(String username, String password) throws Exception {
        String sessionToken = null;
        String user = null;
        String pass = null;
        
        if (username != null && password != null) { 
                preparedStatement = connect
                      .prepareStatement("SELECT * FROM taskList.users WHERE username=?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next() == false){
                    //The specified user doesn't exist.
                    throw new Exception("The specified username doesn't exist.");
                } else {
                    user = resultSet.getString("username");
                    pass = resultSet.getString("password");
                    if (pass.equals(password) && user.equals(username)){
                        //The given password is correct.
                        //Generate new session token.
                        sessionToken = UUID.randomUUID().toString().replaceAll("-", "");
                        //Update session token in the database.
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
                //Add session token to opened session list.
                sessions.add(new Session(username,sessionToken));
                System.out.println( username + " has been logged.");
                System.out.println("Session token: " + sessionToken);
                if (preparedStatement != null )
                    preparedStatement.close();
                if (resultSet != null)
                    resultSet.close();          
        } else {
            throw new Exception("You must provide a valid username and password.");
        }
        
        return sessionToken;
    }
    
    //This method add a new task list to the database of the user.
    //The session must be opened before call this method.
    //Throws exception is session is not opened.
    public void newTaskList (String taskList, String token) throws Exception {
        if (taskList != null && token != null){
            Session session = isSessionOpened(token);
            if (session != null){
                String query = "INSERT INTO taskList." + session.getUsername() 
                        + "_taskLists VALUES (default,?);";
                //Update task Lists in database.
                preparedStatement = connect
                          .prepareStatement(query);
                preparedStatement.setString(1, taskList);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                
                //Add table to support new list.
                query = "CREATE TABLE taskList." + session.getUsername()
                        + "_" + taskList + "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "task VARCHAR(256) NOT NULL, duedate VARCHAR(30), done BOOLEAN DEFAULT false);";
                preparedStatement = connect
                      .prepareStatement(query);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Task list " + taskList + " has been created for user " 
                + session.getUsername() + ".");
            } else {
                throw new Exception("Session is not opened.");
            }
        } else {
            throw new Exception("You must provide a valid taskList and session token.");
        }
    }
    
    //This method delete a list to the database of the user.
    //The session must be opened before call this method.
    //Throws exception is session is not opened.
    public void deleteTaskList (String taskList, String token) throws Exception {
        if (taskList != null && token != null){
            Session session = isSessionOpened(token);
            if (session != null){
                String query = "DROP TABLE " + session.getUsername() 
                        + "_" + taskList + ";";
                //Delete task List table in database.
                preparedStatement = connect
                          .prepareStatement(query);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                
                //Remove table from user taskLists table
                query = "DELETE FROM " + session.getUsername() 
                        + "_taskLists WHERE listname=?;"; 
                preparedStatement = connect
                      .prepareStatement(query);
                preparedStatement.setString(1,taskList);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                System.out.println("Task list " + taskList + " has been deleted for user " 
                        + session.getUsername() + ".");
            } else {
                throw new Exception("Session is not opened.");
            }
        } else {
            throw new Exception("You must provide a valid taskList and session token.");
        }
    }
    
    // This method return an array of strings with the names of the user task lists.
    // Return null if the user has not task lists.
    // Throws Exception if you pass a null argument.
    public String[] getLists (String token) throws Exception{
        ArrayList<String> lists = null;
        if (token != null){
            Session session = isSessionOpened(token);
            // Get user lists from the database.
            preparedStatement = connect
                  .prepareStatement("SELECT * FROM taskList." + session.getUsername() +
                          "_taskLists");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                lists = new ArrayList<String>();
                // The specified user has not any task list.
                resultSet.beforeFirst();
                // ResultSet is initially before the first data set
                // We go row by row adding the task list name to the list that will be returned
                System.out.println(session.getUsername() + " task lists:");
                while (resultSet.next()){
                	lists.add(resultSet.getString("listname"));
                	System.out.println("\t-" + resultSet.getString("listname"));
                }
            }
        } else {
            throw new Exception("You must provide a valid session token.");
        }
        
        if (lists != null) {
            String result[] = new String[lists.size()];
            for (int i = 0; i < lists.size(); i++) {
                result[i] = lists.get(i);
            }
            return result;
        }

        else 
            return null;
    }
    
    // This method check if the session with the given token is opened.
    // Return the session object if the session is opened or null in other case.
    private Session isSessionOpened(String token) {
        Session result = null;
        for (Session j: sessions)
            if (j.getSessionToken().equals(token))
                result = j;
        return result;
    }
    
    // This method close the session with the given session token.
    public void closeSession(String token){
        for (Session j: sessions)
            if (j.getSessionToken().equals(token))
                sessions.remove(j);
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
    
 // This method close all the connections with the database.
    public void close() {
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
          System.err.println("Disconnect error.");
      }
    }
}