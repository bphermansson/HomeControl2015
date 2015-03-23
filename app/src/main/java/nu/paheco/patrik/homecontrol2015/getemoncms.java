package nu.paheco.patrik.homecontrol2015;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/6/15.
 */

public class getemoncms extends AsyncTask<Object, Void, String> {
    private static final String TAG = "In getemoncms";
    String result = "fail";
    static String response = null;

    protected String doInBackground(Object... params) {
        String url = (String) params[0];
        return makeHttpRequest(url);
    }
    final String makeHttpRequest(String url)
    {
        Log.d(TAG, "Make request, url=: ");
        Log.d(TAG, url);
        // http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
        List<NameValuePair> params= new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userid", "2"));

        BufferedReader inStream = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            if (params != null) {
                String paramString = URLEncodedUtils
                        .format(params, "utf-8");
                url += "?" + paramString;
            }
            HttpGet httpGet = new HttpGet(url);

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

            Log.d(TAG, "Getemoncms, " + response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
