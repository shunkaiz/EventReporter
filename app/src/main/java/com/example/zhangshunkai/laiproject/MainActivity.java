package com.example.zhangshunkai.laiproject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements EventFragment.OnItemSelectListener {


    private EventFragment eventFragment;
    private CommentFragment commentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventFragment = new EventFragment();
        commentFragment = new CommentFragment();
//        ListView eventList = findViewById(R.id.event_list);
//        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.event_item, R.id.event_title, getEventNames());
//        EventAdapter adapter = new EventAdapter(this);
//        eventList.setAdapter(adapter);

        if(findViewById(R.id.event_container)!=null){
            getSupportFragmentManager().beginTransaction().add(R.id.event_container, eventFragment).commit();
        }

        if(findViewById(R.id.comment_container)!=null && isTablet()){
            getSupportFragmentManager().beginTransaction().add(R.id.comment_container, commentFragment).commit();
        }

    }


    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



    @Override
    public void onItemSelected(int position){
        commentFragment.onItemSelected(position);
    }



}
