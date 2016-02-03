# site-404-checker
This tool is a utility created in Java and is used to check if there are any pages on a given website having 404/Not-Found status. 
The tool reads a list of websites from one of its property file and then pings all the static URLs of the website to check the status of response returned by them. This check happens in recursion with a depth upto level 10 covering all descendant URLs.
During the check, all the URLs with response status different from 200 are recorded and a consolidated report is sent out as a mail to email addresses listed again in one other property file. The report sent in mail highlights URLs with red colors which have a 404 status.

Currently the tool is hosted at server - 10.180.4.25 and repository is present at - https://bitbucket.org/blinkxdev/site-404-checker

Following are details about the property files present in the tool:

    site-list.properties - This file is located in /etc/site-404-checker and contains the list of websites to be checked

    mail-config.properties - This file is located in /etc/site-404-checker and contains the list of email addresses to which report should be sent, along with details related to subject of the email sent and also the email addresses from which the report email will be sent.

    crawl-config.properties - This file is located in /etc/site-404-checker and contains the configuration values related to the tool, for example, the depth level till which the pages should be checked etc..

Adding any new website or mail address is as easy as updating the property files mentioned above. All the new values are picked up whenever the tool is fired again. Currently the tool is scheduled to run every 8 hours.


### System requirement:
1. Java installed(>jdk1.6)
2. SMTP server installed and configured (or use smtp.gmail.com for sending from gmail server directly)
3. Git command line utility installed
4. Maven build tool

### Build Tool:
1. Perform ```git clone at /opt/ directory```
2. go inside /opt/site-404-checker directory 
    and Run command mvn install

### Run Tool:-
1. Add property file from 
    
    ```/opt/site-404-checker/src/main/resources/*   to   /etc/site-404-checker directory.```

2. Add your site urls to the site-list.properties file:
    
    ```vi /etc/site-404-checker/site-list.properties```
    
3. Add your mail-id and smtp server details to mail-config.properties:
    
    ```vi /etc/site-404-checker/mail-config.properties```
    
4. Make changes to crawler configuration:
    
    ```vi /etc/site-404-checker/crawl-config.properties```
    
5. execute /opt/site-404-checker/runsitechecker.sh

    > you should recieve report mail after completion of above script

### Configuring tool to run at fixed interval:
1. add the runsitechecker.sh script in cron job:
    
    ```vi /etc/crontab```
    
    
    > add below line for running the script after every 8 hour:
    
    ```0 */8 * * * <user-name> /opt/site-404-checker/runsitechecker.sh```
    


