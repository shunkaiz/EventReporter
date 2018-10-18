package com.example.zhangshunkai.laiproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class CommentFragment extends Fragment {
    private GridView mGridView;
    OnItemSelectListener mCallback;

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        mGridView = view.findViewById(R.id.comment_grid);
        mGridView.setAdapter(new EventAdapter(getActivity()));
        return view;
    }

    // Change background color if the item is selected
    public void onListItemSelected(int position){
        for (int i = 0; i < mGridView.getChildCount(); i++){
            if (position == i) {
                mGridView.getChildAt(i).setBackgroundColor(Color.BLUE);
            } else {
                mGridView.getChildAt(i).setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
        }
    }

    // Container Activity must implement this interface
    public interface OnItemSelectListener {
        public void onCommentSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
            //do something
        }
    }



}
