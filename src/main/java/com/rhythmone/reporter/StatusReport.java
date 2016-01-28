package com.rhythmone.reporter;

import com.rhythmone.model.UrlStatus;

import java.util.*;

/**
 * Created by himanshuk on 19/1/16.
 */
public class StatusReport {
    private static StatusReport _instance=null;
    private Map<String,List<UrlStatus>> siteNameToUrlStatusMap=new HashMap<>();
    private Map<String,int[]> siteToReachabilityCount=new HashMap<>();
    private int[] reachabilityCount=new int[3]; //0 index for success , 1 for redirectional, 2 for errors

    public static StatusReport get_instance(){
        if(_instance ==null) {
            _instance = new StatusReport();
        }
        return _instance;
    }
    public void init(String[] siteNames)  {
        for(String siteName:siteNames){
            siteNameToUrlStatusMap.put(siteName,new ArrayList<UrlStatus>());
            siteToReachabilityCount.put(siteName,new int[3]);
        }
    }

    public synchronized void write(String webURL,int statusCode,String statusDescription){

        for(Map.Entry<String, List<UrlStatus>> siteToUrl:siteNameToUrlStatusMap.entrySet()){
            if(webURL.contains(siteToUrl.getKey())){
                siteToUrl.getValue().add(new UrlStatus(webURL,statusDescription,statusCode));
                if(statusCode>=400){
                    reachabilityCount[2]++;
                    (siteToReachabilityCount.get(siteToUrl.getKey()))[2]++;
                }else if(statusCode>=300){
                    reachabilityCount[1]++;
                    (siteToReachabilityCount.get(siteToUrl.getKey()))[1]++;
                }else {
                    reachabilityCount[0]++;
                    (siteToReachabilityCount.get(siteToUrl.getKey()))[0]++;
                }
            }
        }
    }

    public String getHTMLReport(){
        StringBuffer htmlString=new StringBuffer("<!DOCTYPE html>" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>Site Report Sheet:</h2>\n" +
                "<h4>Reachable Urls:"+reachabilityCount[0]+"</h4>\n" +
                "<h4>Un-Reachable Urls:"+reachabilityCount[2]+"</h4>\n" +
                "<h4>Re-directional Urls:"+reachabilityCount[1]+"</h4>\n" +
                "<h5># Site Summary and Un-Reachable Urls are listed below #</h5>\n" +
                "<table style=\"width:100%; border:1px solid black;\">\n" +
                "  <tr style=\"text-align: left; background-color:#e5e5ff;\">\n" +
                "    <th style=\"padding: 5px;border:1px solid black;\">URL</th>\n" +
                "    <th style=\"padding: 5px;border:1px solid black;\">Time Stamp</th>\n" +
                "    <th style=\"padding: 5px;border:1px solid black;\">Reachable</th>\n" +
                "    <th style=\"padding: 5px;border:1px solid black;\" colspan=\"2\">Status</th>\n" +
                "  </tr>");

        for(Map.Entry<String,List<UrlStatus>> siteUrls:siteNameToUrlStatusMap.entrySet()){
                htmlString.append("<tr style=\"font-size: 17px;text-align: left;border:1px solid black;\"><td style=\"padding-left: 10px;padding-top: 2px\">").append(siteUrls.getKey()).append(" Web-Site Links</td></tr>");
                htmlString.append("<tr style=\"font-size: 12px;text-align: left;border:1px solid black;\"><td style=\"padding-left: 14px;padding-top: 2px\">").append("Reachable Urls: "+siteToReachabilityCount.get(siteUrls.getKey())[0]+"</td></tr>");
                htmlString.append("<tr style=\"font-size: 12px;text-align: left;border:1px solid black;\"><td style=\"padding-left: 14px;padding-top: 0px\">").append("Un-Reachable Urls: "+siteToReachabilityCount.get(siteUrls.getKey())[2]+"</td></tr>");
                htmlString.append("<tr style=\"font-size: 12px;text-align: left;border:1px solid black;\"><td style=\"padding-left: 14px;padding-top: 0px\">").append("Re-directional Urls: "+siteToReachabilityCount.get(siteUrls.getKey())[1]+"</td></tr>");
            for(UrlStatus urlStatus:siteUrls.getValue()) {
                  htmlString.append("<tr style=\"font-size: 10px;text-align: left;\" ");
                if(urlStatus.getStatusCode()>=400){
                    htmlString.append(" bgcolor=\"#FF0000\">")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getUrl()).append("</td>")
                             .append("<td  style=\"padding: 5px;border:1px solid black;\">").append(System.currentTimeMillis()).append("</td>")
                                .append("<td style=\"padding: 5px;border:1px solid black;\">").append("NO").append("</td>")
                                .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusCode()).append("</td>")
                                .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusDesc()).append("</td>")
                                .append("</tr>");
                }else if(urlStatus.getStatusCode()>=300){
                    htmlString.append(" bgcolor=\"#f7f7b3\">")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getUrl()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(System.currentTimeMillis()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append("YES REDIRECTING").append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusCode()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusDesc()).append("</td>").append("</tr>");
                }/*else{
                    htmlString.append(" bgcolor=\"#7fbf7f\">")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getUrl()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(System.currentTimeMillis()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append("YES").append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusCode()).append("</td>")
                            .append("<td style=\"padding: 5px;border:1px solid black;\">").append(urlStatus.getStatusDesc()).append("</td>").append("</tr>");
                }*/
              }
        }

        htmlString.append("</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>");

        return htmlString.toString();
    }
}
