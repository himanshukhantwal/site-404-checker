package com.rhythmone;

import com.rhythmone.crawler.SiteCrawlerController;
import com.rhythmone.mailer.SendSiteStatusMail;
import com.rhythmone.reporter.StatusReport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by himanshuk on 21/1/16.
 */
public class SiteStatusChecker {
    private static String allSiteList;
    private static SiteCrawlerController siteCrawlerController=null;
    private static String[] siteNames;

    public static void main(String[] arr) throws Exception {
        SiteStatusChecker siteStatusChecker=new SiteStatusChecker();
        List<String> urls = siteStatusChecker.getAllSiteList();

        StatusReport statusReport=StatusReport.get_instance();
        statusReport.init(siteNames,urls);

        siteCrawlerController=new SiteCrawlerController(urls.toArray(new String[urls.size()]));
        siteCrawlerController.startCrawl();

        SendSiteStatusMail sendSiteStatusMail=SendSiteStatusMail.getInstance();
        sendSiteStatusMail.send(statusReport.getHTMLReport());
        //System.out.print(statusReport.getHTMLReport());

    }


    public List<String> getAllSiteList() throws IOException {
        InputStream inputStream=null;
        inputStream=new FileInputStream("/etc/site-404-checker/site-list.properties");
        Properties siteListProperties=new Properties();
        siteListProperties.load(inputStream);

        List<String> urlList=new ArrayList<>();
        List<String> siteList=new ArrayList<>();
        for (Map.Entry<Object, Object> siteToUrl:siteListProperties.entrySet()){
            siteList.add((String)siteToUrl.getKey());
            for(String url:((String)siteToUrl.getValue()).split(",")){
                urlList.add(url.trim());
            }
        }
        siteNames=siteList.toArray(new String[siteList.size()]);
        return urlList;
    }
}
