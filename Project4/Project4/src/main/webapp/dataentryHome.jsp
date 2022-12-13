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
      <h2>Data Entry Application</h2>
      <div class = "line" > </div>
      <p>You are connected to the Project 4 Enterprise System database as a <span class="label">data-entry-level</span> user.</p>
      <p>Enter the data values in a form below to add a new record to the corresponding database table.</p>
      <div class = "line" > </div>
      <h3>Suppliers Record Insert</h3>
      <form action = "/Project4/suppliersSQLCommands" method = "post">
         <table>
            <tr>
               <th>snum</th>
               <th>sname</th>
               <th>status</th>
               <th>city</th>
            </tr>
            <tr>
               <td><input type="text" name="snum" size="20"></td>
               <td><input type="text" name="sname" size="20"></td>
               <td><input type="text" name="status" size="20"></td>
               <td><input type="text" name="city" size="20"></td>
            </tr>
         </table>
         <button id="enterSupplier" type = "submit">Enter Supplier Record Into Database</button>
         <button id="clearSupplier" onclick="clearTable()" type = "reset">Clear Data and Results</button>
      </form>
      <h3>Parts Record Insert</h3>
      <form action = "/Project4/partsSQLCommands" method = "post">
         <table>
            <tr>
               <th>pnum</th>
               <th>pname</th>
               <th>color</th>
               <th>weight</th>
               <th>city</th>
            </tr>
            <tr>
               <td><input type="text" name="pnum" size="20"></td>
               <td><input type="text" name="pname" size="20"></td>
               <td><input type="text" name="color" size="20"></td>
               <td><input type="text" name="weight" size="20"></td>
               <td><input type="text" name="city" size="20"></td>
            </tr>
         </table>
         <button id="enterPart" type = "submit">Enter Part Record Into Database</button>
         <button id="clearPart"  onclick="clearTable()" type = "reset">Clear Data and Results</button>
      </form>
      <h3>Jobs Record Insert</h3>
      <form action = "/Project4/jobsSQLCommands" method = "post">
         <table>
            <tr>
               <th>jnum</th>
               <th>jname</th>
               <th>numworkers</th>
               <th>city</th>
            </tr>
            <tr>
               <td><input type="text" name="jnum" size="20"></td>
               <td><input type="text" name="jname" size="20"></td>
               <td><input type="text" name="numworkers" size="20"></td>
               <td><input type="text" name="city" size="20"></td>
            </tr>
         </table>
         <button id="enterJob" type = "submit">Enter Job Record Into Database</button>
         <button id="clearJob"  onclick="clearTable()" type = "reset">Clear Data and Results</button>
      </form>
      <h3>Shipments Record Insert</h3>
      <form action = "/Project4/shipmentsSQLCommands" method = "post">
         <table>
            <tr>
               <th>snum</th>
               <th>pnum</th>
               <th>jnum</th>
               <th>quantity</th>
            </tr>
            <tr>
               <td><input type="text" name="snum" size="20"></td>
               <td><input type="text" name="pnum" size="20"></td>
               <td><input type="text" name="jnum" size="20"></td>
               <td><input type="text" name="quantity" size="20"></td>
            </tr>
         </table>
         <button id="enterShipment" type = "submit">Enter Shipment Record Into Database</button>
         <button id="clearShipment" onclick="clearTable()" type = "reset">Clear Data and Results</button>
      </form>
      
      <h3>Database Results:</h3>
      
      <!-- Returns the string created in the servlet -->
      <div id = "results">
	  <%= session.getAttribute("tableHTML") %>
	  </div>
	  
   </body>
</html>
