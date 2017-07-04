package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */

	private static final long serialVersionUID = 1L;
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSourse;

	@Override
	public void init() throws ServletException {
		super.init();
	
		//create object of studentDbUtil and pass the dataSourse into it
		try{
		studentDbUtil = new StudentDbUtil(dataSourse);
		} catch (Exception ex){
			throw new ServletException();
		}	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//list student in MVC form
		try {
			//read the "command" parameter coming from add-student-from.jsp
			String theCommand = request.getParameter("command");
			
			//if the command is missing then assign some default
			if(theCommand == null) {
				theCommand = "LIST";
			}
			//route to the appropriate method
			switch(theCommand){
			
			case "LIST" :
				listStudent(request, response);
				break;
				
			case "ADD":
				addStudent(request, response);
				break;
			
			case "LOAD": 
				loadStudent(request, response);
				break;	
				
			case "UPDATE":
				updateStudent(request, response);
				break;

			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default :
				listStudent(request, response);
				
			}
			
			listStudent(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from the request
		String theStudentId = request.getParameter("studentId");
		
		//delete student from the database
		studentDbUtil.deleteStudent(theStudentId);
		
		//send the request back to "list-student.jsp"
		listStudent(request, response);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		//read the information form the form 
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create the new student object
		Student theStudent = new Student(id, firstName, lastName, email);
		
		//perform update database
		studentDbUtil.updateStudent(theStudent);
		
		//send the request back to "list student"
		listStudent(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

			// read student id from form data
			String theStudentId = request.getParameter("studentId");
			
			// get student from database (db util)
			Student theStudent = studentDbUtil.getStudent(theStudentId);
			
			// place student in the request attribute
			request.setAttribute("THE_STUDENT", theStudent);
			
			// send to jsp page: update-student-form.jsp
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/update-student-form.jsp");
			dispatcher.forward(request, response);		
		}

	/*my method
	 private void loadStudent(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		//read student id from form data
		String studentId = request.getParameter("studentId");
		
		//get student from StudentDbUtil
		Student theStudent = studentDbUtil.getStudent(studentId);
		
		//place student in request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		//send to update-student-form.jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}*/

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//read the student info from data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create new student object
		Student theStudent = new Student(firstName, lastName, email);
	
		//add the student to database
		studentDbUtil.addStudent(theStudent);
		
		//send back to the main page (as student list)
		listStudent(request, response);
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		//get student from studentDbUtil
		List<Student> students = studentDbUtil.getStudent();
		
		//add student to the request
		request.setAttribute("STUDENT_LIST", students);
		
		//pass to jsp page with RequetDispatcher method
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
	}
}
