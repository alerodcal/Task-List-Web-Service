package tasklistwebservice;

public class Task {
	private int id = 0;
	private String task = null;
	private String duedate = null;
	private Boolean done = false;
	
	public Task (String task, String duedate){
		this.task = task;
		this.duedate = duedate;
	}
	
	public String getId (){
		return id;
	}
	
	public String getTask (){
		return task;
	}

	public String getDueDate (){
		return duedate;
	}

	public Boolean getDone (){
		return done;
	}

	// There isn't a setId method because you can't set the id 
	
	public void setTask (String task){
		this.task = task;
	}
	
	public void setDueDate (String duedate){
		this.duedate = duedate;
	}

	public void setDone (Boolean done){
		this.done = done;
	}
}