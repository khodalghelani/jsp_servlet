package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSourse;
	
	public StudentDbUtil(DataSource theDataSourse){
		dataSourse = theDataSourse;
	}
	
	public List<Student> getStudent() throws Exception{
		
		List<Student> Students = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try{
		//get connection
		myConn = dataSourse.getConnection();				
		
		//create sql statement
		String sql = "select * from student order by first_name";
		myStmt = myConn.createStatement();
		
		//execute query
		myRs = myStmt.executeQuery(sql);
		
		//process the result
		while(myRs.next()){
			
			//retrieve the data from result set row
			//"id", "forst_name", "last_name", "email" - must be same as column name given in database
			int id = myRs.getInt("id");
			String firstName = myRs.getString("first_name");
			String lastName = myRs.getString("last_name");
			String email = myRs.getString("email");
			
			//create new student object
			Student tempStudent = new Student(id, firstName, lastName, email);
			
			//add it to student list
			Students.add(tempStudent);
		}
		
			return Students;
		
		}finally {
			//close the connection
			close(myConn, myRs, myStmt);
				
		}
	}

	private void close(Connection myConn, ResultSet myRs, Statement myStmt) {
		try{
		if(myRs != null){
			myRs.close();
		}
		if(myStmt != null){
			myStmt.close();
		}
		if(myConn != null){
			myConn.close(); //does not close complitly, it is available to someone to use
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public void addStudent(Student theStudent) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try{
			//get db connection
			myConn = dataSourse.getConnection();	
				
			//create sql for insert
			String sql = "insert into student " +
					"(first_name, last_name, email)" + "values(?, ?, ?)";
			
			//String sql = "INSERT INTO STUDENT " +
			//		"(fiesr_name, last_name, email)" + "values(?, ?, ?)";
					
			myStmt = myConn.prepareStatement(sql);
			
			//set the parameter for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			//execute sql
			myStmt.execute();
			
			}
			finally{
			//close theJDBC connections
				close(myConn, null, myStmt);
			}
		
	}

	public Student getStudent(String theStudentId) throws Exception {
		
		Student theStudent = null;
		Connection myConn = null; 
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try{
			//convert string student id to integer
			studentId = Integer.parseInt(theStudentId);
			
			//get connection to database
			myConn = dataSourse.getConnection();
			
			//create sql query to get selected student from database
			String sql = "select * from student where id=?";
			
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			//set parameter
			myStmt.setInt(1, studentId);
			
			//execute statement
			myRs = myStmt.executeQuery();
			
			//Retrieve data from result set row
			if(myRs.next()){
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				//use the constructor with studentid of Student class
				theStudent = new Student(studentId, firstName, lastName, email);
			} else{
				throw new Exception("We coulden't find the student " + theStudentId);
			}
			
			return theStudent;

		}finally{
			//close the JDBC connection
			close(myConn, myRs, myStmt);
		}
	}

	public void updateStudent(Student theStudent) throws Exception {
	
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			//get db connection
			myConn = dataSourse.getConnection();
			
			//create sql statement
			String sql = "update student " +
							"set first_name =?, last_name =?, email =? " +
							"where id=? ";
			
			//prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			//set parameter
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			//execute sql
			myStmt.execute();
			
		}finally{
			//close the JDBC statement
			close(myConn, null, myStmt);
			
		}
	}

	public void deleteStudent(String theStudentId) throws Exception{
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{
			//convert String student id into Integer
			int studentId = Integer.parseInt(theStudentId);
			
			//get connection to databse
			myConn = dataSourse.getConnection();
			
			//create sql query to delet student from database
			String sql = "delete from student where id=? ";
			
			//prepare the statement
			myStmt = myConn.prepareStatement(sql);
			
			//set parameter
			myStmt.setInt(1, studentId);

			//execute sql statement
			myStmt.execute();
			
			
			
		} finally{
			close(myConn, null, myStmt);
		}
	}
}

