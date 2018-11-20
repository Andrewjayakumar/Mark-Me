/**
 * CMPUT 301 Team 24
 *
 * To implement the full gallery and to grab all the information
 *
 * Version 1.0
 *
 * Date: 2018-11-20
 *
 * Copyright Notice
 * @author Vishal Patel
 */
package com.cybersix.markme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class fullGallery extends Fragment {

    public static final String PHOTO_CONTENT = "ca.cybersix.photo";
    private Bitmap bitmaps[];
    private int counter;

    public fullGallery() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_gallery, container, false);
//        Bitmap b0 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ic_gallery);
//        Bitmap b1 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ic_body);
        bitmaps = new Bitmap[2000];
//        bitmaps[0]= b0;
//        bitmaps[1]= b1;
//        bitmaps[2]= b0;
//        bitmaps[3]= b1;

        counter = 0;
        int size;
        if(RecordController.getInstance().selectedProblemRecords==null){
            size=0;
        }else{
            size  = RecordController.getInstance().selectedProblemRecords.size();
        }

        for (int i = 0; i < size; i++) {
            int recordSize  = RecordController.getInstance().selectedProblemRecords.get(i).getPhotos().size();
            for (int j = 0; j < recordSize; j++) {
                this.bitmaps[counter] = RecordController.getInstance().selectedProblemRecords.get(i).getPhotos().get(j);
                counter++;
            }
        }

        GridView gridView = view.findViewById(R.id.gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(getActivity(), this.bitmaps);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap bitmap = fullGallery.this.bitmaps[position];
                Intent intent = new Intent(getActivity().getBaseContext(),
                        deletePhoto.class);

                try {
                    //Write file
                    String filename = "bitmap.png";
                    FileOutputStream stream = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    //Cleanup
                    stream.close();
                    bitmap.recycle();

                    //Pop intent
                    intent.putExtra(PHOTO_CONTENT, filename);
                    getActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        return view;
    }
}
