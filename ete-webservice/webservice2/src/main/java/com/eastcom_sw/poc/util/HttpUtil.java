package com.eastcom_sw.poc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2017/2/22.
 */
public class HttpUtil {
    protected static final Log logger = LogFactory.getLog(HttpUtil.class);

    /**
     * Send Http post request
     * @param url
     * @param send_data
     * @return
     * @throws java.io.IOException
     */
    public static String httpPost(String url, String send_data) throws IOException {
        URL u = null;
        HttpURLConnection con = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        StringBuilder buffer = new StringBuilder();
        // System.out.println("send_url:" + url);
        // System.out.println("send_data:" + send_data);
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            osw = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            osw.write(send_data);
            osw.flush();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(),
                    "utf-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }

        } catch (IOException e) {
            throw e;
        }finally {
            if (con != null) {
                con.disconnect();
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}
