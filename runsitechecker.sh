#!/bin/bash
echo "starting site checker"
java -cp "target/site-404-checker-1.0-SNAPSHOT.jar:target/lib/*" com.rhythmone.SiteStatusChecker
echo "site checker successfully ended"
