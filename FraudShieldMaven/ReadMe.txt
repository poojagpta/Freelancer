ReadMe.txt
===========

Project build steps using maven.
===============================

1. Checokut the code
2. Goto MerchantViewMaven project directory and build using 'mvn install' command.
3. Goto fraudshield-web project directory and build using 'mvn install' command. (fraudshield-web is dependent on some MerchantViewMaven classes)
4. Goto MainWebModule project directory and build using 'mvn install' command 
	or 'mvn gae:run' to deploy on local machine.

