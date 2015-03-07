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

import java.util.concurrent.ExecutionException;

public class ThirdFragment extends ListFragment {
    private ListView listView1;
    private ArrayAdapter<String> listAdapter ;
    private static final String TAG = "ThirdFragment";

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "...In data onResume ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "In onCreateView");

        String[][] data;

        // Grab data from sensors
        data = getdata();
        Log.d(TAG,"Data: " + data[0][1]);

        // Populate listview
        View myInflatedView = inflater.inflate(R.layout.fragment_three, container,false);;

        String[] values = new String[] { "Message1", "Message2", "Message3" };
        String[] emoncms = data[0];
        Integer lenemon = emoncms.length;
        Log.d(TAG,"lenemon:"+lenemon.toString());

        String[] ardweather = data[1];
        Log.d(TAG,"Emoncms: " + emoncms[1]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, emoncms);
        setListAdapter(adapter);
        return myInflatedView;
    }

    private String[][] getdata() {
        //Url for all feeds & values
        String getUrl = "http://192.168.1.6/emoncms/feed/list.json?userid=2";
        String[][] datavalues= new String[2][100];

        // Get data from Emoncms-server
        String emonresult = "";
        getemoncms getemoncms= new getemoncms();
        getemoncms.execute(getUrl);

        try {
            emonresult = getemoncms.get().toString();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Log.d(TAG, "Returned result: " + emonresult);

        //Clean emonresults
        String delims = "[}]";
        String delimsline = "[,]";
        String delimsitem = "[:]";
        String [] name;
        String [] value;
        String[] names = new String[10];
        String [] values = new String[10];
        String[] emonrow = new String[10];
        String[] emontitle = new String[10];

        // Split page into lines
        String[] lines = emonresult.split(delims);
        int nooflines = lines.length-1;
        Integer e=0;

        for(e=0; e<nooflines; e++) {
            // Remove first char if its a comma
            String firstLetter = lines[e].substring(0, 1);
            //Clean commas
            if (firstLetter.equals(",")) {
                lines[e] = lines[e].substring(1);
            }
            /* Line 1 = temp
            2=NA
            3=Humidity outdoor
            4=Air pressure
            5=energy
            6=NA
            7=Light level outdoor
            8=Indoor temperature

            */
            String[] items = lines[e].split(delimsline);
            // Name
            name = items[2].split(delimsitem);
            name[1] = name[1].replaceAll("[^A-Za-z0-9 ().\\[\\]]", "");
            // Value
            value = items[9].split(delimsitem);
            value[1] = value[1].replaceAll("[^-A-Za-z0-9().\\[\\]]", "");
            //Log.d(TAG, "value: ");
            //Log.d(TAG, value[1]);

            names[e]=name[1];
            values[e]=value[1];
            emonrow[e]=value[1];
            emontitle[e] = name[1];
            if (e.equals(0)) {
                emonrow[e]=emonrow[e]+"C";
            }
            else if (e.equals(1)) {
                emonrow[e]=emonrow[e]+"%";
            }
            else if (e.equals(2)) {
                emonrow[e]=emonrow[e]+"hPa";
            }
            else if (e.equals(4)) {
                int valuelight = Integer.valueOf(emonrow[e]);
                if (valuelight>150) {
                    emonrow[e] = emonrow[e] + ", daytime";
                }
                else {
                    emonrow[e] = emonrow[e] + ", night";
                }
            }
            datavalues[0][e]=emonrow[e];

        }
        //Log.d("TAG", "Emonlines: " + e.toString());
        //System.out.println(nooflines);

        Log.d(TAG, "Names: " + names[2] + "-" + values[2]);
        Log.d(TAG, "Row2: " + emonrow[2]);

        // Get data from Ardweather
        getUrl = "http://192.168.1.6/ardweather/summary.php";
        getArdweather getardweather= new getArdweather();
        getardweather.execute(getUrl);

        String result[][] = new String[10][10];

        try {
            result = getardweather.get();
        } catch (InterruptedException err) {
            // TODO Auto-generated catch block
            err.printStackTrace();
        }catch (ExecutionException err) {
            // TODO Auto-generated catch block
            err.printStackTrace();
        }

        //Create lines from values
        int i=0;
        for (i=1; i<5; i++ ) {
            datavalues[1][i] = "Temp: " + result[i][2] +"C, Humidity: " + result[i][3] +"% - " + result[i][4];
            //Log.d(TAG,ardvalues[i]);
        }


        dataclass weather_data[] = new dataclass[e+5];
        {
            //new dataclass("1", "Cloudy");
            //new Weather(R.drawable.weather_showers, "Showers"),
            //new Weather(R.drawable.weather_snow, "Snow"),
            //new Weather(R.drawable.weather_storm, "Storm"),
            //new Weather(R.drawable.weather_sunny, "Sunny")
        }

        //weather_data[0]=new dataclass(1,"test");

        Integer c=0;
        // Add Emoncms values
        for (i=0; i<e; i++ ) {
            //listAdapter.add(Html.fromHtml(emonrow[i]).toString());
            weather_data[c]=new dataclass(emontitle[i], emonrow[i]);

            c++;
        }
        String b = String.valueOf(c);
        //Log.d("TAG", "c=" + b);

        // Adjust row count
        c--;

        // Add Ardweather values
        String si = "";
        si = String.valueOf(i);
        //Log.d("Show Ardweather, items=", si);
        String title = null;

        for (i=0; i<6; i++ ) {
            title = result[i][0] + " " + result[i][1];
            //Log.d(TAG,result[i][0]);
            //weather_data[c]=new dataclass(title, ardvalues[i]);
            c++;
        }
        Log.d(TAG,"Return:" + datavalues[0][1]);
        return datavalues;
    }

}