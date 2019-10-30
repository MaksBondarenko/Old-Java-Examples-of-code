To use this program you need to start local server in MySQL or change configuration in Main.java, username and password set as "root", you can change it in Main.java

If you see this error in console:

Error 1: java.sql.SQLException: The server timezone value...

Then try to execute this in MySQL:

SET GLOBAL time_zone = '+3:00';