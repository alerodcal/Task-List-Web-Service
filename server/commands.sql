CREATE TABLE "Users" (
  "Id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "Name" text(20,0) NOT NULL,
  "Password" text(20,0) NOT NULL);

--Create new user
INSERT INTO users VALUES ('example','password','123456789abcdef');
--Create new lists table
CREATE TABLE example_taskLists (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	listname VARCHAR(30) NOT NULL);
--Insert new list
INSERT INTO example_taskLists VALUES (default,'exampleTaskList');
CREATE TABLE example_exampleTaskList (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	task VARCHAR(256) NOT NULL, 
	duedate VARCHAR(30), 
	done BOOLEAN DEFAULT false);
--Insert new task
INSERT INTO example_exampleTaskList VALUES (default,'example Task 2', '15/06/2016', true);
