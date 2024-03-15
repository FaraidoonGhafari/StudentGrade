

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class delete
 */
@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete() {
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
			
			String show = "select * from studentGrade where id=?";
			PreparedStatement pstm = conn.prepareStatement(show);
			pstm.setInt(1,  id);
			
			String title = "Deleted Data";
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0" + //
			"trasitional//en\">\n"; //
			out.println(docType + //
					"<html>\n" + //
					"<head><title>" + title + "</title></head>\n" + //
					"<h1 align=\"center\">" + title + "</h1>\n");
			
			
			out.print("<table width=75% border=1>");
			ResultSet rs = pstm.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalColumn = rsmd.getColumnCount();
			out.print("<tr>");
			for (int i=1; i<=totalColumn; i++) {
				out.print("<th>" + rsmd.getColumnName(i) + "</th>");
			}
			out.print("</tr>");
			
			
			
			while (rs.next()) {
				id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String courses = rs.getString("courses");
				int score = rs.getInt("score");
				String grade = rs.getString("grade");
				out.print("<tr><td>" + id + "</td><td>" + name + "</td><td>" + gender + "</td><td>" + courses + "</td><td>" + score + "</td><td>" + grade + "</td></tr>");

			}
			out.print("</table>");
			out.println("<a href=home.html>Home</a>");
			out.println("</body></html>");
					
			String query = "delete from studentGrade where id=?";
			pstm = conn.prepareStatement(query);
		

			pstm.setInt(1, id);			
			pstm.executeUpdate();
			
			
			conn.close();
		
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
