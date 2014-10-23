CHIP AND PIN REPORTING TOOL
Intention: 
Automate the chip and pin and monitor the payment done through kiosk. The input to the tool is basically from the webkiosk logs which further processed to view graphical output. This help in reducing the daily effort spend on preparing the report. This tool also provides customized reporting of payment status which is described in the below sections. This will also help real time monitoring of payment done through kiosks
Technology And Components Used:
•	Spring 3 MVC
•	Spring Batch
•	Bootstrap
•	Mongo DB
•	Tomcat server
Integrating Components 
•	Either Cassandra tool or Log downloading tool
Features
•	Over all status of payment from all the kiosk.
•	Over all status of payment from all NGK the kiosk.
•	Comparison of NGK kiosk with BLS kiosks.
•	Search with Kiosk Id which will provide the payment statistics of that particular kiosk.
Implementation of the Chip And Pin Reporting Tool
•	Input is the webkiosk application logs which are fetched either from Cassandra tool or from the Log downloading tool (which need to be decided).
•	Every day once the batch process will run automatically which will convert webkiosk logs to desired modal and will be pushed to Mongo db.
•	The web module of the tool get the input from the user and corresponding graphical response will be shown as output.
