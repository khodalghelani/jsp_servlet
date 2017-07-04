package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       //Define datasourse/connection pool for Resource Injectin
       @Resource(name="jdbc/web_student_tracker")
       private DataSource datasource;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//step 1: setting up printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//Step 2: get connection to databse
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try{
			myConn = datasource.getConnection();
			
		//step 3: create SQL Statement
			String sql = "select* from student";
			myStmt = myConn.createStatement();
		
		//step 4: Execute SQL query
			myRs = myStmt.executeQuery(sql);
		
		//Step 5: Process the result set
			while(myRs.next()){
				String email = myRs.getString("email");
				out.println(email);
			}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
}
