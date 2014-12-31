package perf.http;

import com.yiguang.perf.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liyiguang on 12/31/14.
 */
public class SampleForJava extends Test{

    @Override
    public void beforeAll() {
        warmUp(1, 1);
    }

    @Override
    public void doTest() {
        HttpURLConnection connection = null;
        try {
            URL theUrl = new URL("http://localhost:8080/test.html");
            connection = (HttpURLConnection) theUrl.openConnection();
            connection.connect();

            int code = connection.getResponseCode();

            //System.out.println(code);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
    }
}
