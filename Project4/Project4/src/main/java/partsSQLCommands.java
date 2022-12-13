import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*  Name:  Avriel Lyon 
Course: CNT 4714 – Fall 2022 – Project Three 
Assignment title:  A Three-Tier Distributed Web-Based Application 
Date:  December 4, 2022 
*/ 

public class partsSQLCommands extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private Statement statement;
    private String tableHTML = " ";
    private String pnum;
    private String pname;
    private String color;
    private String weight;
    private String city;

    //Calls getDBConnection to get connection
    //Creates the HTTP session
    //Only needs to perform update queries 
    //Sends HTML string to the jsp page via RequestDispatcher
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    	tableHTML = " ";
    	getDBConnection();
    	HttpSession session = request.getSession(); 
    	
        pnum = request.getParameter("pnum");
        pname = request.getParameter("pname");
        color = request.getParameter("color");
        weight = request.getParameter("weight");
        city = request.getParameter("city");

        //Constructs sqlStatement to be sent to updateQuery
        tableHTML = updateQuery("insert into parts values ('"+pnum+"','"+pname+"','"+color+"','"+weight+"','"+city+"')");
        
        session.setAttribute("tableHTML", tableHTML);
        
        RequestDispatcher view = session.getServletContext().getRequestDispatcher("/dataentryHome.jsp");

        try {
			view.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
        }

    }

    //updateQuery takes into account if the update involves the shipments table
    //If the shipments table is involved, a separate sql statement is sent to update the suppliers table
    //A string is created with HTML tags that is later sent to the jsp file to display the resulting change message
    //Business logic is not triggered here, so it was not included
    public String updateQuery(String sqlStatement) {
        try {
            statement.executeUpdate(sqlStatement);
            tableHTML = "<div style = 'background: #66FF99; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p>New parts record: (" + pnum + ", " + pname + ", " +  color + ", " + weight + ", " +  city + ") - successfully entered into database.</p></div>";
        } catch (SQLException e) {
        	tableHTML = "<div style = 'background: #E07676; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p style = 'font-weight: bold;'>Error excecuting the SQL statement:</p><p>"+ e.getMessage() +"</p></div>";
        }

        return tableHTML;
    }
    
    //Establishes a connection to the database with the respective properties file
    public void getDBConnection() {
        try {
            Properties properties = new Properties();
            FileInputStream filein = null;
            MysqlDataSource dataSource = null;

            filein = new FileInputStream("dataentry.properties");
            properties.load(filein);
            dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
            connection = dataSource.getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}