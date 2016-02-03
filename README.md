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
