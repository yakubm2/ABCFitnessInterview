
There are three apis in here

For this i have used H2 embedded Db to store class info and bookinginfo
To access DB console
http://localhost:8080/h2-console

1)GET http://localhost:8080/api/bookings/search (For get booking Details)
2)POST  http://localhost:8080/api/bookings (For creating bookings)
3)POST  http://localhost:8080/api/classes (For creating classes)
Note.Plz refer funtional test cases and unit test cases for requestBody
Db tables
show columns from Booking;

FIELD  	            TYPE  	  	  KEY    
PARTICIPATION_DATE	DATE	  		  NULL
CLASS_ID	          BIGINT			  NULL
ID	                BIGINT		    PRI	  
MEMBER_NAME	        CHARACTER    	NO	


show columns from Club_Class;

FIELD  	    TYPE  	   KEY    
CAPACITY	  INTEGER		 NULL
DURATION	  INTEGER	 	 NULL
END_DATE	  DATE	  	 NULL
START_DATE	DATE	  	 NULL
START_TIME	TIME(6)		 NULL
ID	        BIGINT		 PRI
NAME	      CHARACTER  NO	
