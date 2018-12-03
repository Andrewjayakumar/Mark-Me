/**
 * CMPUT 301 Team 24
 *
 * Patient assignment activity is to be a separate activity entirely under the settings activity.
 *
 * Version 0.1
 *
 * Date: 2018-11-18
 *
 * Copyright Notice
 * @author Jose Ramirez
 */
package com.cybersix.markme.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cybersix.markme.R;
import com.cybersix.markme.actvity.UserActivityAddPopUp;
import com.cybersix.markme.controller.NavigationController;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public class UserAssignmentFragment extends Fragment {
    // will need to set a list adapter to the view to be notified if any of the models have been
    // updated
    private ArrayAdapter<UserModel> userListAdapter;
    private ArrayList<UserModel> userList = new ArrayList<UserModel>();
    private ListView assignedUserListView;
    private int removePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_assignment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set up all of the buttons that are used within this activity
        Button addButton = (Button) getActivity().findViewById(R.id.fragment_user_assignment_addAssignUserButton);
        Button removeButton = (Button) getActivity().findViewById(R.id.fragment_user_assignment_removeUserButton);
        assignedUserListView = (ListView) getActivity().findViewById(R.id.fragment_user_assignment_listView);

        // TODO: Will be removed once server functionality is implemented
        for (int i = 0; i < 15; i++){
            String tempUsername = "UserPerson" + Integer.toString(i);
            String tempPassword = "1234";
            String tempID = "Fake ID " + Integer.toString(i);
            try {
                UserModel tempUser = new UserModel(tempUsername);
                tempUser.setUserId(tempID);
                userList.add(tempUser);
            }
            catch (Exception e) {
                // do nothing
            }
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will open the add user popup
                Intent addUserIntent = new Intent(getActivity(), UserActivityAddPopUp.class);
                startActivity(addUserIntent);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the user has selected a user from the list view and wants to remove the user
                // ask for a popup whether or not the user wishes to continue

                removeUser();
            }
        });

        TextView title = getActivity().findViewById(R.id.fragment_title_bar_fragmentTitle);
        View returnButton = getActivity().findViewById(R.id.fragment_title_bar_returnButton);

        title.setText("Assign Myself");
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.getInstance().switchToFragment(SettingsFragment.class);
            }
        });

        //TODO: still need to set the "selected" animation to be there
        assignedUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // this is when one of the selected items within the list view is selected
                view.setSelected(true);
                assignedUserListView.setSelected(true);
                removePosition = position;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // the list view will show all of the registered care providers from within the database
        // getCareProviders();
        // displayCareProvides();
        // TODO: Make sure to check the previous assignment for reference into how to use the list adapter
        // The list will have a "Select is on" mode where the user can select one of the patient
        // items

        // TODO: load the list of assigned users from the server. For now it is just fake data created
        // TODO: from onCreate
        // userList = IOUtilityController.getUsers();
        userListAdapter = new ArrayAdapter<UserModel>(getContext(), R.layout.list_item, userList);
        assignedUserListView.setAdapter(userListAdapter);

    }

    private void removeUser() {
        // TODO: add a prompt asking if the user is sure they want to remove the item
        if (assignedUserListView.isSelected()) {
            assignedUserListView.setSelected(false);
            // get and remove the selected item
            UserModel removeUser = userList.get(removePosition);
            // build the warning dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("Are you sure you want to remove this patient?\n" + removeUser.toString());
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        userList.remove(removePosition);
                        userListAdapter.notifyDataSetChanged();
                        //TODO: save the list into the server
                        Log.d("Remove Assign User", "The user assignment has been removed");
                    }
                    catch (Exception e) {
                        // display an error when removing the list item
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // return null to the activity
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void addUser() {

    }
}
