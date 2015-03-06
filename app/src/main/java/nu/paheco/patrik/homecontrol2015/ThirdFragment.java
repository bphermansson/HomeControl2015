package nu.paheco.patrik.homecontrol2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;

public class ThirdFragment extends Fragment {
    private ListView listView1;
    private ArrayAdapter<String> listAdapter ;
    private static final String TAG = "ThirdFragment";


    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...In data onResume ");
        //Url for all feeds & values
        String getUrl = "http://192.168.1.6/emoncms/feed/list.json?userid=2";

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
        Log.d(TAG, "Returned result: " + emonresult);

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

        }
        Log.d("TAG", "Emonlines: " + e.toString());
        System.out.println(nooflines);

        Log.d(TAG, "Names: " + names[2] + "-" + values[2]);
        Log.d(TAG, "Row2: " + emonrow[2]);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_two, null);

        // Create UI components here.


        View myInflatedView = inflater.inflate(R.layout.fragment_two, container,false);;
        return myInflatedView;
    }

}