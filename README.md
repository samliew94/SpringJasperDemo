![alt text](screenshot.png)

# SpringJasperDemo
A web app that demonstrates how to use Spring Boot in conjunction with Jasper Reports for printing

# Context
  User wants to print order details stored in a database.<br/>
  So I setup an api endpoint at server:port/print that fetches all the records from table "Orders".<br/>
  After fetch, all objects are then stored in a List<Map> then converted to a Json file.<br/>
  This Json file is then sent to the .jasper file to display records ready for printing.<br/>

# Setup
  Your machine must be running Java 11 (may add Docker support)<br/>
  The app runs on localhost:8080<br/>
  In command prompt, run the following:<br/>  
  `java -jar SpringJasperDemo-1.0.0.jar`<br/>
  Navigate to any of the end points below to test it out.<br/>

# End Points
  `/order` -> fetches all orders from db<br/>
  `/order/delete` -> deletes all data from table Order<br/>
  `/order/populate` -> performs /order/delete, inserts 50 rows of of random data and finally performs /order<br/>
  `/print` -> all data from table Order is processed and prepped by JasperLibrary for printing.<br/>

# DB Structure:
  DB Type -> MySQL<br/>
  Tables -> Orders and Products<br/>
  Orders -> id, ordered_date, product (fkey to Products), quantity<br/>
  Products -> id, product_code, product_name<br/>
  
  
