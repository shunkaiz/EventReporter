package com.example.zhangshunkai.laiproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity implements EventFragment.OnItemSelectListener,
        CommentFragment.OnItemSelectListener {


    private EventFragment mEventFragment;
    private CommentFragment mCommentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEventFragment = new EventFragment();
        mCommentFragment = new CommentFragment();
//        ListView eventList = findViewById(R.id.event_list);
//        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.event_item, R.id.event_title, getEventNames());
//        EventAdapter adapter = new EventAdapter(this);
//        eventList.setAdapter(adapter);

        if(findViewById(R.id.event_container)!=null){
            findViewById(R.id.event_container).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            getSupportFragmentManager().beginTransaction().add(R.id.event_container, mEventFragment).commit();
        }

        if(findViewById(R.id.comment_container)!=null && isTablet()){
            getSupportFragmentManager().beginTransaction().add(R.id.comment_container, mCommentFragment).commit();
        }

    }


    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



    @Override
    public void onItemSelected(int position){
        if(!isTablet()){
            Intent intent = new Intent(this, EventGridActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
        else {
            mCommentFragment.onListItemSelected(position);
        }
    }


    @Override
    public void onCommentSelected(int position) {
        mEventFragment.onItemSelected(position);
    }



}
