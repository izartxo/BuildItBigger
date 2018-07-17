package com.udacity.gradle.builditbigger.androidjavalibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AndroidMainActivityFragment extends Fragment {

    public AndroidMainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.joke_fragment, container, false);

        TextView tv = (TextView) root.findViewById(R.id.joke_text);
        Intent intent = getActivity().getIntent();
        tv.setText(intent.getStringExtra("data"));



        return root;
    }
}
