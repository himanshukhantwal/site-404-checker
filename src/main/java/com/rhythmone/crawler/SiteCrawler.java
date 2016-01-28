package com.rhythmone.crawler;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import com.rhythmone.reporter.StatusReport;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.HttpStatus;

public class SiteCrawler extends WebCrawler {
    private StatusReport statusReport=StatusReport.get_instance();

    private Pattern EXT_FILTERS;
    private Pattern URL_FILTERS;

    public SiteCrawler() throws IOException {
        init();
    }

    private void init() throws IOException {
        InputStream inputStream=new FileInputStream("/etc/site-404-checker/crawl-config.properties");
        Properties excludeProperties=new Properties();
        excludeProperties.load(inputStream);

        String extensionFilterString=(excludeProperties.getProperty("excludeExtension")).replace(",","|");
        EXT_FILTERS= Pattern.compile(".*(\\.("+extensionFilterString+"))$");

        String urlFilterString=excludeProperties.getProperty("excludeURL");
        if(urlFilterString!=null && !urlFilterString.isEmpty()) {
            urlFilterString=urlFilterString.replace(",","|");
            URL_FILTERS = Pattern.compile(".*(\\.(" + urlFilterString + "))$");
        }
    }

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         boolean shouldVisitThisUrl= !EXT_FILTERS.matcher(href).matches()
                && href.startsWith(url.getParentUrl());
         if(URL_FILTERS !=null){
             shouldVisitThisUrl=shouldVisitThisUrl && !URL_FILTERS.matcher(href).matches();
         }
         return shouldVisitThisUrl;
     }

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        System.out.println(webUrl + " is giving " + statusCode);
        statusReport.write(webUrl.getURL(), statusCode, statusDescription);
    }
}