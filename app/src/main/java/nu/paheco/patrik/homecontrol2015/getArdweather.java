package nu.paheco.patrik.homecontrol2015;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by user on 12/20/14.
 */
public class getArdweather extends AsyncTask<Object, Void, String[][]> {

    private static final String TAG = "In getArdweather";
    protected String[][] doInBackground(Object... params) {
        String url = (String) params[0];
        // Get Xml-data from url
        String xml=getXmlFromUrl(url);
        Log.d(TAG,"xml: "+xml);
        String c[][] = new String[10][10];
        // Parse Xml
        try {
            c  = parse(xml);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d(TAG,url);
        //String test="";
        return c;
    }
    protected void onPostExecute(String[][] result) {
        //Log.d(TAG,"In onPostExecute");
        //Log.d(TAG,"Result: "+result[1][1]);
    }

    public static String[][] parse (String xml)
            throws XmlPullParserException, IOException
            {
                Log.d(TAG, "Lets parse");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader("<foo>Hello World!</foo>"));
                xpp.setInput(new StringReader(xml));

                int c=0;
                String[][] values= new String[6][20];

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        //System.out.println("Start document");
                    } else if (eventType == XmlPullParser.END_DOCUMENT) {
                        //System.out.println("End document");
                    } else if (eventType == XmlPullParser.START_TAG) {
                        //System.out.println("Start tag " + xpp.getName());
                        if (xpp.getName().equals("item")) {
                            c++;
                        }
                        if (xpp.getName().equals("name")) {
                            xpp.next();
                            values[c][0]=xpp.getText();
                            //Log.d(TAG,xpp.getText());
                        }
                        else if (xpp.getName().equals("timestamp")) {
                            xpp.next();
                            values[c][1]=xpp.getText();
                            //Log.d(TAG,xpp.getText());
                        }
                        else if (xpp.getName().equals("t")) {
                            xpp.next();
                            values[c][2]=xpp.getText();
                            //Log.d(TAG,xpp.getText());
                        }
                        else if (xpp.getName().equals("h")) {
                            xpp.next();
                            values[c][3]=xpp.getText();
                            //Log.d(TAG,xpp.getText());
                        }
                        else if (xpp.getName().equals("b")) {
                            xpp.next();
                            values[c][4]=xpp.getText();
                            //Log.d(TAG,xpp.getText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        //System.out.println("End tag " + xpp.getName());
                    } else if (eventType == XmlPullParser.TEXT) {
                        //System.out.println("Text " + xpp.getText());
                    }

                    eventType = xpp.next();
                }
                Log.d(TAG, c + " items found");
                //Log.d(TAG, values[2][0]);
                /*
                for (int i=1; i<(values.length-1); i++ ) {
                        System.out.println(values[i][0]);
                }
                */
                return values;
            }

    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }
    }

