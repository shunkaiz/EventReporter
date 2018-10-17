package com.example.zhangshunkai.laiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends BaseAdapter{

    Context context;
    List<Event> eventData;

    public EventAdapter(Context context) {
        this.context = context;
        eventData = DataService.getEventData();
    }


    @Override
    public int getCount() {
        return eventData.size();
    }

    @Override
    public Object getItem(int position) {
        return eventData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_item,
                    parent, false);
        }

        TextView eventTitle = convertView.findViewById(R.id.event_title);
        TextView eventAddress = (TextView) convertView.findViewById(R.id.event_address);
        TextView eventDescription = (TextView) convertView.findViewById(R.id.event_description);

        Event e = eventData.get(position);
        eventTitle.setText(e.getTitle());
        eventAddress.setText(e.getAddress());
        eventDescription.setText(e.getDescription());
        return convertView;
    }
}
