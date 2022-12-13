import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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


public class rootSQLCommands extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;
    private Statement statement;
    private ResultSetMetaData metaData;
    private ResultSet rs;
    private int res;
    private String tableHTML = " ";

    //Calls getDBConnection to get connection
    //Creates the HTTP session
    //Calls a function based on if the sqlStatement is a select statement or not
    //Sends HTML string to the jsp page via RequestDispatcher
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    	tableHTML = " ";
    	getDBConnection();
    	HttpSession session = request.getSession(); 	
        String sqlStatement = request.getParameter("sqlStatement");

        if (sqlStatement.contains("select")) {
            tableHTML = selectQuery(sqlStatement);
        } else {
        	tableHTML = updateQuery(sqlStatement);
        }
        
        session.setAttribute("tableHTML", tableHTML);
        
        RequestDispatcher view = session.getServletContext().getRequestDispatcher("/rootHome.jsp");

		try {
			view.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
    }
    
    //updateQuery takes into account if the update involves the shipments table
    //If the shipments table is involved, a separate sql statement is sent to update the suppliers table
    //A string is created with HTML tags that is later sent to the jsp file to display the resulting change message
    public String updateQuery(String sqlStatement) {
        if (sqlStatement.contains("shipments")) {
            try {
                res = statement.executeUpdate(sqlStatement);
                tableHTML = "<div style = 'background: #66FF99; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p>The statment executed successfully. " + res + " row(s) affected.</p>";
                
                if (res != 0) {
                	tableHTML += ("<p>Business Logic Detected! - Updating Supplier Status</p>");
                	//sql statement for the suppliers table
                    int supplierRes = statement.executeUpdate("update suppliers left join shipments on suppliers.snum = shipments.snum set suppliers.status = suppliers.status + 5 where shipments.quantity >= 100;");
                    tableHTML += ("<p>Business Logic updated " + supplierRes + " supplier status marks.");
                }
                tableHTML += "</div>";
            } catch (SQLException e) {
            	tableHTML = "<div  style = 'background: #E07676; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p>"+ e.getMessage() +"</p></div>";
            }
        } else {
            try {
                res = statement.executeUpdate(sqlStatement);
                tableHTML = "<div style = 'background: #66FF99; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p>The statment executed successfully. " + res + " row(s) affected.</p><p>Business Logic Not Triggered!</p></div>";

            } catch (SQLException e) {
            	tableHTML = "<div style = 'background: #E07676; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p style = 'font-weight: bold;'>Error excecuting the SQL statement:</p><p>"+ e.getMessage() +"</p></div>";
            }
        }

        return tableHTML;
    }

    //selectQuery performs the query then creates a string to format the results into an HTML table
    //This string is later returned to the jsp page to display the table
    public String selectQuery(String sqlStatement) {
    	int numberOfColumns;
    	
        try {
            rs = statement.executeQuery(sqlStatement);   
            metaData = rs.getMetaData();
            numberOfColumns = metaData.getColumnCount();

            tableHTML += "<table><thead><tr>";

            //For each column name
            for (int i = 1; i <= numberOfColumns; i++) {
            	tableHTML += "<th>" + metaData.getColumnName(i) + "</th>";
            }

            tableHTML += "</tr></thead><tbody>";

            //Creates the rows and puts in the string for each cell
            while (rs.next()) {
            	tableHTML += "<tr>";
                for (int i = 1; i <= numberOfColumns; i++) {
                	tableHTML += "<td>" + rs.getString(i) + "</td>";
                }
                tableHTML += "</tr>";
            }

            tableHTML += "</tbody></table>";
            
        } catch (SQLException e) {
        	//Prints out the SQL error if one occurred
        	tableHTML = "<div style = 'background: #E07676; width: 30%; margin: auto; padding: 40px; border-radius: 25px;'><p style = 'font-weight: bold;'>Error excecuting the SQL statement:</p><p>"+ e.getMessage() +"</p></div>";;
        }
        
        return tableHTML;
    }
    
    //Establishes a connection to the database with the respective properties file
    public void getDBConnection() {
        try {
            Properties properties = new Properties();
            FileInputStream filein = null;
            MysqlDataSource dataSource = null;

            filein = new FileInputStream("root.properties");
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