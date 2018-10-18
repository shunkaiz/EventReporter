package com.example.zhangshunkai.laiproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventGridActivity extends AppCompatActivity {

    int pos = 0;
    CommentFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_grid);

        pos = getIntent().getIntExtra("position", 0);
        fragment = new CommentFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.event_container, fragment).commit();



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fragment.onListItemSelected(pos);
    }

}
