

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class edit
 */
@WebServlet("/edit")
public class edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();

		res.setContentType("text/html");
		int id = Integer.parseInt(req.getParameter("id"));
		//read the form data
		String name = req.getParameter("name");
		// courses
		String courses = req.getParameter("sub");
		int score = Integer.parseInt(req.getParameter("score"));
		String grade = "";
		if(score >= 97 && score <= 100) {
			grade = "A+";
		} else if (score >= 93 && score < 97) {
			grade = "A";
		}
		else if(score >= 90 && score < 93){
			grade = "A-";
			
		}
		else if(score >= 90 && score < 93){
			grade = "A-";
			
		}
		
		else if(score >= 87 && score < 90){
			grade = "B+";
			
		}
		else if(score >= 83 && score < 87){
			grade = "B";
			
		}
		else if(score >= 80 && score < 83){
			grade = "B-";
			
		}
		else if(score >=77 && score <80) {
			grade = "C+";
		}
		else if(score >=73 && score <77) {
			grade = "C";
		}
		else if(score >=70 && score <73) {
			grade = "C-";
		}
		else if(score >=67 && score <70) {
			grade = "D+";
		}
		else if(score >=63 && score <67) {
			grade = "D";
		}
		else if(score >=60 && score <63) {
			grade = "D-";
		}
		else if(score >=0 && score <60) {
			grade = "F";
		}
		
		try 
		{
			String url = "jdbc:mysql://ec2-3-19-30-149.us-east-2.compute.amazonaws.com:3306/studentGradeSystem";
			String userName = "fghafari_mysql_remote"; 
			String password = "1qaz12345";
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection(url, userName, password);
			System.out.println("CONNECTION SUCCESS!");
			System.out.println("URL:  " + url);
			System.out.println("User: " + userName);
			System.out.println("Pass: \n" + password);
			
			String query = "SELECT * from studentGrade where id=?";
			PreparedStatement pstm = conn.prepareStatement(query);
			pstm.setInt(1, id);
			
			ResultSet rs = pstm.executeQuery();
			
			while (rs.next()) {
				if (name == "") {
					name = rs.getString(2);
				}
				if (courses == "") {
					courses = rs.getString(4);
				}

			}
			String queryName = "UPDATE studentGrade set name=? where id=?";
			pstm = conn.prepareStatement(queryName);
			
			
			pstm.setString(1, name);
			pstm.setInt(2, id);
			
			pstm.execute();
			
			String queryCourse = "UPDATE studentGrade set courses=? where id=?";
			pstm = conn.prepareStatement(queryCourse);
			
			
			pstm.setString(1, courses);
			pstm.setInt(2, id);
			
			pstm.execute();
			
			String queryScore = "UPDATE studentGrade set score=? where id=?";
			pstm = conn.prepareStatement(queryScore);
			
			
			pstm.setInt(1, score);
			pstm.setInt(2, id);
			
			pstm.execute();
			
			String queryGrade = "UPDATE studentGrade set grade=? where id=?";
			pstm = conn.prepareStatement(queryGrade);
			
			
			pstm.setString(1, grade);
			pstm.setInt(2, id);
			
			pstm.execute();
			
			
			String show = "select * from studentGrade where id=?";
			pstm = conn.prepareStatement(show);
			pstm.setInt(1,  id);
			
			rs = pstm.executeQuery();
			
			String title = "Edited Data";
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0" + //
			"trasitional//en\">\n"; //
			out.println(docType + //
					"<html>\n" + //
					"<head><title>" + title + "</title></head>\n" + //
					"<h1 align=\"center\">" + title + "</h1>\n");
			
			
			out.print("<table width=75% border=1>");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalColumn = rsmd.getColumnCount();
			out.print("<tr>");
			for (int i=1; i<=totalColumn; i++) {
				out.print("<th>" + rsmd.getColumnName(i) + "</th>");
			}
			out.print("</tr>");
			
			
			
			while (rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				String gender = rs.getString("gender");
				courses = rs.getString("courses");
				score = rs.getInt("score");
				grade = rs.getString("grade");
				out.print("<tr><td>" + id + "</td><td>" + name + "</td><td>" + gender + "</td><td>" + courses + "</td><td>" + score + "</td><td>" + grade + "</td></tr>");

			}
			out.print("</table>");
			out.println("<a href=home.html>Home</a>");
			out.println("</body></html>");
			
			conn.close();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			}

		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
