<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
   <head>
		<!--  Name:  Avriel Lyon 
		Course: CNT 4714 – Fall 2022 – Project Three 
		Assignment title:  A Three-Tier Distributed Web-Based Application 
		Date:  December 4, 2022  -->
      <title>CNT 4714 Remote Database Manager</title>
      <meta charset="utf-8" />
      <script>
      <!-- Replaces the div html with nothing -->
      	function clearTable(){
      		document.getElementById('results').innerHTML = "";
      	}
      </script>
      <link rel="stylesheet" href="style.css">
   </head>
   <body>
   
      <h1>Welcome to the Fall 2022 Project 4 Enterprise Database System</h1>
      <h2>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container</h2>
      <p>You are connected to the Project 4 Enterprise System database as a <span class="label">root-level</span> user.</p>
      <p>Please enter any valid SQL query or update command in the box below.</p>
      
      <div class = "line"> </div>
      
      <div class = "form">
	      <form action = "/Project4/rootSQLCommands" method = "post">
	         <textarea id="sqlStatement" name="sqlStatement" rows="10" cols="100"></textarea>
	         <div id="padding"></div>
	         <div id="buttons">
	         <button id="execute" type = "submit">Execute Command</button>
	         <button id="reset" type="reset">Reset Form</button>
	         <button onclick="clearTable()" id="clear" type="button">Clear Results</button>
	         </div>
	         
	      </form>
	  </div>
	      <p>All execution results will appear below this line.</p>
      <div class = "line" > </div>
      
      <h3>Database Results:</h3>
      
      <!-- Returns the string created in the servlet -->
      <div id = "results">
	  <%= session.getAttribute("tableHTML") %>
	  </div>
	  
   </body>
</html>
