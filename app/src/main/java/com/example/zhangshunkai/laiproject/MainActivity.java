package com.example.zhangshunkai.laiproject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ListView eventList = findViewById(R.id.event_list);
//        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.event_item, R.id.event_title, getEventNames());
//        EventAdapter adapter = new EventAdapter(this);
//        eventList.setAdapter(adapter);

        if(findViewById(R.id.fragment_container)!=null){
            Fragment fragment = isTablet()?new CommentFragment():new EventFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }


    private String[] getEventNames() {
        String[] names = {
                "Event1", "Event2", "Event3",
                "Event4", "Event5", "Event6",
                "Event7", "Event8", "Event9",
                "Event10", "Event11", "Event12"};
        return names;
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
