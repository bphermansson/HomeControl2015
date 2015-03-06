package nu.paheco.patrik.homecontrol2015;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.HapticFeedbackConstants;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    String baseUrL = "http://192.168.1.7/hc/sendCommand.php?port=serial/by-id/usb-Telldus_Homeautomation_USB-Dongle_TS000007-if00-port0&proto=NEXA&";

    /**
     * Identifier for the first fragment.
     */
    public static final int FRAGMENT_ONE = 0;

    /**
     * Identifier for the second fragment.
     */
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    /**
     * Number of total fragments.
     */
    public static final int FRAGMENTS = 3;

    /**
     * The adapter definition of the fragments.
     */
    private FragmentPagerAdapter _fragmentPagerAdapter;

    /**
     * The ViewPager that hosts the section contents.
     */
    private ViewPager _viewPager;

    /**
     * List of fragments.
     */
    private List<Fragment> _fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create fragments.
        _fragments.add(FRAGMENT_ONE, new FirstFragment());
        _fragments.add(FRAGMENT_TWO, new SecondFragment());
        _fragments.add(FRAGMENT_THREE, new ThirdFragment());


        // Setup the fragments, defining the number of fragments, the screens and titles.
        _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return FRAGMENTS;
            }

            @Override
            public Fragment getItem(final int position) {
                return _fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case FRAGMENT_ONE:
                        return "Ljusstyrning";
                    case FRAGMENT_TWO:
                        return "Title Two";
                    case FRAGMENT_THREE:
                        return "Title Three";
                    default:
                        return null;
                }
            }
        };
        _viewPager = (ViewPager) findViewById(R.id.pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
    }

    public void onNexaSendLroomon(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        String getUrl = baseUrL.concat("house=C&channel=3&onoff=on");
        new SendNexa().execute(getUrl);
    }
    public void onNexaSendLroomoff(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        String getUrl = baseUrL.concat("house=C&channel=3&onoff=off");
        new SendNexa().execute(getUrl);
    }
    public void onNexaSendOutdooron(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        String getUrl = baseUrL.concat("house=P&channel=2&onoff=on");
        new SendNexa().execute(getUrl);
    }
    public void onNexaSendOutdooroff(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        String getUrl = baseUrL.concat("house=P&channel=2&onoff=off");
        new SendNexa().execute(getUrl);
    }


}