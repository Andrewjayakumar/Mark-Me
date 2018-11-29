/**
 * CMPUT 301 Team 24
 *
 * This list fragment will display all of the possible records associated to a problem that the user
 * has selected.
 *
 * Version 0.1
 *
 * Date: 2018-11-12
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @editor Curtis Goud
 * @see com.cybersix.markme.model.RecordModel
 * @see com.cybersix.markme.actvity.RecordCreationActivity
 */
package com.cybersix.markme.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cybersix.markme.R;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.controller.ProblemController;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.controller.RecordController;

import java.util.ArrayList;

/**
 * Jose: I will have to set this up also to be able to get a list of records based off of a problem
 *      TODO: this may involve some elastic searching and queries that should be handled by the controller
 */
public class RecordListFragment extends ListFragment {
    public static final String EXTRA_RECORD_INDEX = "RecordIdx";
    private ArrayAdapter<RecordModel> recordListAdapter;
    private RecordController recordController = RecordController.getInstance();
    private ArrayList<RecordModel> recordsToDisplay = new ArrayList<>();
    private EBodyPart selectedPart;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        //Get selected part from intent
        selectedPart = (EBodyPart) args.getSerializable(BodyFragment.EXTRA_SELECTED_PART);
        ProblemModel problemModel = ProblemController.getInstance().getSelectedProblem();

        getTitle().setText(problemModel.getTitle()); // Title should be title of problem
        getDetails().setText(problemModel.getDescription()); // Detail should be problem description
        getReturnButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(ProblemListFragment.class);
            }
        });

        if(selectedPart == null){
            recordsToDisplay = recordController.getSelectedProblemRecords();
        } else {
            for(RecordModel r:recordController.getSelectedProblemRecords()){
                if(r.getBodyLocation().getBodyPart().equals(selectedPart)){
                    recordsToDisplay.add(r);
                }
            }
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putInt(RecordListFragment.EXTRA_RECORD_INDEX, position);
                NavigationController.getInstance().switchToFragment(RecordInfoFragment.class, b);
            }
        });

        getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: What if we want to add a record with using the body?

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        update();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        update();
//    }

    private void update() {
        // this function will update the records to display onto the list everytime the fragemnent
        // is called
        // set the adapter for the list activity
        recordsToDisplay = new ArrayList<RecordModel>();
        if(selectedPart == null){
            recordsToDisplay = recordController.getSelectedProblemRecords();
        } else {
            for(RecordModel r:recordController.getSelectedProblemRecords()){
                if(r.getBodyLocation().getBodyPart().equals(selectedPart)){
                    recordsToDisplay.add(r);
                }
            }
        }
        recordListAdapter = new ArrayAdapter<RecordModel>(getActivity(), R.layout.list_item, recordsToDisplay);
        getListView().setAdapter(recordListAdapter);
        recordListAdapter.notifyDataSetChanged();
    }
}