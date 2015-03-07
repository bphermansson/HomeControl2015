package nu.paheco.patrik.homecontrol2015;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by user on 1/18/15.
 */
public class dataadapter extends ArrayAdapter<dataclass> {

    Context context;
    int layoutResourceId;
    dataclass data[] = null;

    public dataadapter(Context context, int layoutResourceId, dataclass[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            //holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtInfo = (TextView)row.findViewById(R.id.txtInfo);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        dataclass weather = data[position];
        holder.txtTitle.setText(weather.title);
        holder.txtInfo.setText(weather.info);
        //holder.imgIcon.setImageResource(weather.icon);
        //holder.

        return row;
    }

    static class WeatherHolder
    {
        //ImageView imgIcon;
        TextView txtTitle;
        TextView txtInfo;
    }
}
