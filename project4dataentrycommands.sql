# Data entry level user command script
# CNT 4714 - Fall 2022 - Project 4
# This script contains the commands that a cdata-entyr-level user will issue against the
# Project 4 database (supplier/parts/jobs/shipments).  Due to the restrictions on the data entry level user 
# only inserts are allowed.  Project 4 incoporates server-side 
# business logic that manipulates the status of a supplier when certain conditions involving shipment quantity are triggered.
# This business logic can be impacted by this user.
#
###########################################################
# Command 1: Insert new shipment record for supplier S5.
# This will work ok. Business logic not triggered
# insert into shipments values ("S39", "P6", "J4", 33);

Enter these values into the shipments form:  snum = "S39", pnum = "P6", jnum = "J4", quantity = 33


############################################################
# Command 2: This command will not execute due to a referential integrity violation.
# insert into shipments values ("S39", "P55", "J4", 150);

Enter these values into the shipments form:  snum = "S39", pnum = "P55", jnum = "J4", quantity = 150


#############################################################
 # Command 3: This command will work.
 # insert into suppliers values("S90","Anna-Frieda",34,"Stockholm");

Enter these values into the suppliers form:  snum = "S90", sname = "Anna-Frieda", status = 34, city = "Stockholm"


 #############################################################
 # Command 4: This command will work. 
 # insert into jobs values ("J90","Top Secret Job",3,"Stockholm");

 Enter these values into the jobs form: jnum = "J90", jname = "Top Secret", numworkers = 3, city = "Stockholm"


 #############################################################
 # Command 5: This command will work and trigger the business logic.
 # insert into shipments values("S39","P6","J6",500);

 Enter these values into the shipments form:  snum = "S39", pnum = "P6", jnum = "J6", quantity = 500


#############################################################
 # Command 6: This command will work and trigger the business logic.
 # insert into parts values("P90","U-plate","black",50,"Cardiff");

 Enter these values into the parts form: pnum = "P90", pname = "U-plate", color = "black", weight = 50, city = "Cardiff"


#############################################################
 # Command 7: This command will not work.
 # insert into shipments values("S39","P6","J6",35);

 Enter these values into the shipments form:  snum = "S39", pnum = "P6", jnum = "J6", quantity = 35


#############################################################
 # Command 8: This command will work and trigger the business logic.
 # insert into shipments values("S90","P90","J90",500);

Enter these values into the shipments form:  snum = "S90", pnum = "P90", jnum = "J90", quantity = 500