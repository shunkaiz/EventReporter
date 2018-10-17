package com.example.zhangshunkai.laiproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class CommentFragment extends Fragment {

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        GridView gridView = view.findViewById(R.id.comment_grid);
        gridView.setAdapter(new EventAdapter(getActivity()));
        return view;
    }


}
