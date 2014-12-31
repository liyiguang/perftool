package com.yiguang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;


public class SimpleHttpClient {

    private static final Logger log = LoggerFactory.getLogger(SimpleHttpClient.class);

    public static String CONTENT_TYPE_JSON = "application/json";
    public static String CONTENT_TYPE_XML = "application/xml";
    public static String CONTENT_TYPE_HTML = "text/html";
    public static String CONTENT_TYPE_TEXT = "text/plain";

    private int connectTimeOut = 5 * 1000;
    private int readTimeTimeout = 5 * 1000;
    private String contentType = CONTENT_TYPE_HTML;
    private String encode = "UTF-8";
    private String contentKey = "accept";

    private boolean baseAuth = false;
    private String userName = null;
    private String password = null;

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void setReadTimeTimeout(int readTimeTimeout) {
        this.readTimeTimeout = readTimeTimeout;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setBaseAuth(boolean baseAuth) {
        this.baseAuth = baseAuth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }

    public String get(String url) {

        HttpURLConnection connection = null;
        InputStream in = null;
        try {

            URL theUrl = new URL(url);
            connection = (HttpURLConnection) theUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(contentKey, contentType);
            if (baseAuth) {
                log.info("HTTP Basic Authentication : " + userName + "/" + password);
                BASE64Encoder enc = new BASE64Encoder();
                String userPassword = userName + ":" + password;
                String encodedAuthorization = enc.encode(userPassword.getBytes());
                connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(connectTimeOut);
            connection.setReadTimeout(readTimeTimeout);
            connection.connect();
            in = connection.getInputStream();

            int httpCode = connection.getResponseCode();

            if (httpCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("SimpleHttpClient get failed,url=" + url + "httpCode=" + httpCode);
            }

            String response = readStream(in);
            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ingore) {

                }
            }
        }

    }

    public String post(String url, Map<String, String> postData) {
        HttpURLConnection connection = null;
        InputStream in = null;
        try {
            URL theUrl = new URL(url);
            connection = (HttpURLConnection) theUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty(contentKey, contentType);
            if (baseAuth) {
                BASE64Encoder enc = new BASE64Encoder();
                String userPassword = userName + ":" + password;
                String encodedAuthorization = enc.encode(userPassword.getBytes());
                connection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);
            }
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(connectTimeOut);
            connection.setReadTimeout(readTimeTimeout);
            OutputStream out = connection.getOutputStream();
            out.write(buildParameter(postData).getBytes(encode));

            connection.connect();
            in = connection.getInputStream();

            int httpCode = connection.getResponseCode();

            if (httpCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("SimpleHttpClient get failed,url=" + url + "httpCode=" + httpCode);
            }

            return readStream(in);

        } catch (Exception e) {
            throw new RuntimeException("SimpleHttpClient get failed,url=" + url, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ingore) {

                }
            }
        }
    }

    private String readStream(InputStream in) throws IOException {
        StringWriter writer = new StringWriter();
        InputStreamReader reader = new InputStreamReader(in, encode);
        copy(reader, writer);
        return writer.toString();
    }

    private void copy(Reader in, Writer out) throws IOException {
        char[] buffer = new char[1024];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }
    }

    private String buildParameter(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            if (value != null) {
                sb.append(URLEncoder.encode(key, encode));
                sb.append("&");
                sb.append(URLEncoder.encode(value, encode));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        SimpleHttpClient client = new SimpleHttpClient();
        String ret = client.get("http://localhost:8080/test.html");

        System.out.println(ret);
    }

}
