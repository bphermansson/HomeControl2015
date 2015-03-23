package nu.paheco.patrik.homecontrol2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThirdFragment extends ListFragment {
    private ListView listView1;
    private ArrayAdapter<String> listAdapter ;
    private static final String TAG = "Emonvalues";
    JSONArray contacts = null;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "...In data onResume ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "In onCreateView");
        View myInflatedView = inflater.inflate(R.layout.fragment_three, container,false);;

        // Grab data from sensors
        String result="";
        try {
            result = new getserverdata().execute("http://192.168.1.6/emoncms_new/feed/list.json?userid=2").get();
            //Log.i("EmonLog", "Result: "+result);
            Log.i("EmonLog", "Got data");
        } catch (Exception e) {
            Log.i("EmonLog", "Error: "+e);
        }

        String[] lines = new String[19];
        // Loop through array
        if (result != null) {
            try {
                JSONArray jArray = new JSONArray(result);
                Integer jlen = jArray.length();
                String sjlen = jlen.toString();
                Log.d(TAG,"sjlen: " + sjlen);

                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        String id = oneObject.getString("id");
                        String name = oneObject.getString("name");
                        String value = oneObject.getString("value");
                        String time = oneObject.getString("time");
                        // Convert timestamp
                        long itime = Integer.valueOf(time);
                        Date date = new Date(itime*1000);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd-hh:mm", Locale.ENGLISH);
                        String realdate = format.format(date);
                        //Log.d(TAG,realdate);
                        lines[i] = realdate + " : " + name + " : " + value;
                        Log.d(TAG,lines[i]);
                    } catch (JSONException e) {
                        // Oops
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }

        // Populate listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, lines);
        setListAdapter(adapter);
        return myInflatedView;
    }
}