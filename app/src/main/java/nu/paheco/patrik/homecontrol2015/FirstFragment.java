package nu.paheco.patrik.homecontrol2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment_one, null);

        // Create UI components here.
        View myInflatedView = inflater.inflate(R.layout.fragment_one, container,false);
        TextView t = (TextView) myInflatedView.findViewById(R.id.textView3);
        t.setText("FirstFragment");
        return myInflatedView;
    }

}