package nu.paheco.patrik.homecontrol2015;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import android.widget.TextView;

/**
 * Created by user on 8/23/14.
 */
public class SendNexa extends AsyncTask<String, Void, String> {
    private static final String TAG = "Nexatest";
    String result = "fail";

    public String doInBackground(String... params) {
        //tasks
        Log.d(TAG, params[0]);
        String url = params[0];
        Log.d(TAG, "In doInBackground");
        return makeHttpRequest(url);
    }
    final String makeHttpRequest(String url)
    {
        Log.d(TAG, "Make request, url=: ");
        Log.d(TAG, url);

        BufferedReader inStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpRequest);
            inStream = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()));

            StringBuffer buffer = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = inStream.readLine()) != null) {
                buffer.append(line + NL);
            }
            inStream.close();

            result = buffer.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }



    public void onPostExecute(String result) {
        Log.d(TAG, "Result: ");
        Log.d(TAG, result);
    }

}
