package tasklistwebservice;

public class Session {
	private String username = null;
	private String sessionToken = null;
	
	public Session (String username, String sessionToken){
		this.username = username;
		this.sessionToken = sessionToken;
	}
	
	public String getUsername (){
		return username;
	}
	
	public String getSessionToken (){
		return sessionToken;
	}
	
	public void setUsername (String username){
		this.username = username;
	}
	
	public void setSessionToken (String sessionToken){
		this.sessionToken = sessionToken;
	}
}
