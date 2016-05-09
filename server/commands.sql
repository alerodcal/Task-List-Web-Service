CREATE TABLE users (username VARCHAR(30) NOT NULL PRIMARY KEY, 
	password VARCHAR(30) NOT NULL, 
	token VARCHAR(64) NOT NULL);

--Create new user
INSERT INTO taskList.users VALUES ('example','password','2a033e2244c94b72be83b4a8952a33f9');
--Create new lists table
CREATE TABLE taskList.example_taskLists (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	listname VARCHAR(30) NOT NULL);
--Insert new list
INSERT INTO taskList.example_taskLists VALUES (default,'exampleTaskList');
CREATE TABLE taskList.example_exampleTaskList (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	task VARCHAR(256) NOT NULL, 
	duedate VARCHAR(30), 
	done BOOLEAN DEFAULT false);
--Insert new task
INSERT INTO taskList.example_exampleTaskList VALUES (default,'example Task 2', '15/06/2016', true);
