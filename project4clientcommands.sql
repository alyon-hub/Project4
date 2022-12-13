# Client level user command script
# CNT 4714 - Fall 2022 - Project 4
# This script contains the commands that a client-level user will issue against the
# Project4 database (supplier/parts/jobs/shipments.  Due to the restrictions on the client-level user, the
# business logic cannot be triggered by any input from this category of user.  
# 
###########################################################
# Command 1: Query: list the supplier number and supplier name for those suppliers who ship every green part.
# This should return supplier S6 (Nicola Giannberti).

select snum, sname
from suppliers
where not exists
	( select *
  	  from parts
	  where parts.color = "green" 
	  and not exists
		( select *
		  from shipments
		  where shipments.snum = suppliers.snum
				and shipments.pnum = parts.pnum
		)
	);

############################################################
# Command 2: This command will not execute for the client-level user.
# Output should show the message indicating that the command was denied to the client-level user.

update jobs
set city = "Madrid" 
where city = "Paris";	

 #############################################################
 # Command 3: This is a basic select command using an aggregation operator
 # Business logic not triggered and no table is updated.
 
 select count(*) as total_shipments_with_quantity_over_100
 from shipments
 where quantity > 100;

 #############################################################
 # Command 4: This command will not execute for the client-level user.
 # Output should show the message indicating that the command was denied to the client-level user.
 
 replace into suppliers values ("S1", "Michael Schumacher",4,"Frankfurt");
