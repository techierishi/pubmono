package com.karmick.dragcontroller;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/9/2015.
 */
public class PhotoSortrFragment extends Fragment {

    View viewFragment;
    PhotoSortrView photoSorter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.photosorts_fragment, container, false);




        photoSorter=(PhotoSortrView)viewFragment.findViewById(R.id.drag_layer);

        ArrayList<Drawable> IMAGES = new ArrayList<>();
        IMAGES.add(getActivity().getResources().getDrawable(R.drawable.lake));
        IMAGES.add(getActivity().getResources().getDrawable(R.drawable.m74hubble));
        IMAGES.add(getActivity().getResources().getDrawable(R.drawable.sunset));
        IMAGES.add(getActivity().getResources().getDrawable(R.drawable.tahiti));

        photoSorter.setIMAGES(IMAGES);


        return viewFragment;

    }

    @Override
    public void onResume() {
        super.onResume();
        photoSorter.loadImages(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        photoSorter.unloadImages();
    }
}
