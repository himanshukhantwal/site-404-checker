package com.rhythmone.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SiteCrawlerController {
    private String[] sitesToBeCrawled;
    private CrawlConfig config=new CrawlConfig();
    private int numberOfCrawlers;
    public SiteCrawlerController(String[] sitesToBeCrawled) throws FileNotFoundException {
        this.sitesToBeCrawled=sitesToBeCrawled;
        init();
    }

    private void init() throws FileNotFoundException {
        InputStream inputStream=new FileInputStream("~/site-404-checker/src/main/resources/crawl-config.properties");
        Properties crawlProperties=new Properties();
        try {
            crawlProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.setCrawlStorageFolder(crawlProperties.getProperty("crawlStorageFolder"));
        config.setMaxDepthOfCrawling(Integer.parseInt(crawlProperties.getProperty("maxDepthOfCrawling")));
        config.setMaxPagesToFetch(Integer.parseInt(crawlProperties.getProperty("maxPagesToFetch")));
        config.setPolitenessDelay(Integer.parseInt(crawlProperties.getProperty("politenessDelay")));
        config.setUserAgentString(crawlProperties.getProperty("userAgentString"));
        if(crawlProperties.getProperty("proxyHost")!=null && !crawlProperties.getProperty("proxyHost").isEmpty()){
            config.setProxyHost(crawlProperties.getProperty("proxyHost"));
            config.setProxyPort(Integer.parseInt(crawlProperties.getProperty("proxyPort")));
            if(crawlProperties.getProperty("proxyUser")!=null && !crawlProperties.getProperty("proxyUser").isEmpty()){
                config.setProxyUsername(crawlProperties.getProperty("proxyUser"));
            }
            if(crawlProperties.getProperty("proxyPassword")!=null && !crawlProperties.getProperty("proxyPassword").isEmpty()){
                config.setProxyUsername(crawlProperties.getProperty("proxyPassword"));
            }
        }
        this.numberOfCrawlers= Integer.parseInt(crawlProperties.getProperty("numberOfCrawlers"));
    }

    public void startCrawl() throws Exception {
        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        for(String site:sitesToBeCrawled) {
            controller.addSeed(site);
        }

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(SiteCrawler.class, numberOfCrawlers);
    }
}