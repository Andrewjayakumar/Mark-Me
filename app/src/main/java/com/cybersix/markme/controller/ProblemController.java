/**
 * CMPUT 301 Team 24
 *
 * This controller will be in charge of the handling of the problem information between multiple
 * views
 *
 * Version 0.1
 *
 * Date: 2018-11-14
 *
 * Copyright Notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.model.ProblemModel
 * @see com.cybersix.markme.fragment.ProblemListFragment
 */
package com.cybersix.markme.controller;

import android.graphics.Bitmap;
import android.util.Log;

import com.cybersix.markme.ElasticSearchIOController;
import com.cybersix.markme.model.DataModel;
import com.cybersix.markme.model.RecordModel.PhotoTooLargeException;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.model.RecordModel.TooManyPhotosException;
import com.cybersix.markme.model.ProblemModel;

import java.util.ArrayList;

public class ProblemController {
    // set up the controller instance with lazy construction
    private static ProblemController instance = null;
    private UserProfileController userInstance;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected ProblemController() {
        DataModel.getInstance();
    }

    // Lazy construction of instance.
    public static synchronized ProblemController getInstance() {
        if (instance == null) {
            instance = new ProblemController();
            instance.loadProblemData();
        }
        return instance;
    }

    // TODO: THIS IS THE FAKE DATA UPDLOAD FOR THE LIST OF PROBLEMS
    private static void uploadFakeData() {
        // This function will just load fake problems to the data
        for (int i = 0; i < 10; i++) {
            String title = "Fake Title " + Integer.toString(i);
            String Description = "This is a fake description for getTitle " + Integer.toString(i);
            instance.createNewProblem(title, Description);
        }
    }

    // set up all of the necessary public functions that this controller will go through

    /**
     * This function will create a new problem for the user that called it and will add it
     * to the problem list.
     *
     * @param title the getTitle of the problem
     * @param description the description of the problem
     * @author Jose Ramirez
     */
    public void createNewProblem(String title, String description) {
        DataModel.getInstance().createNewProblem(title,description);
    }

    /**
     * This function will edit one of the selected problems
     * @param index the problem index to edit
     * @param newTitle the new getTitle for the edit
     * @param newDescription the new description for the edit
     */
    public void editProblem(int index, String newTitle, String newDescription) {
        DataModel.getInstance().editProblem(index,newTitle,newDescription);
    }

    /**
     * This function will load the problems based on the user that called this. The user information
     * is stored on the user profile controller
     * @return will return a list of problems that the user has
     */
    public void loadProblemData() {
        DataModel.getInstance().loadProblemData();
    }

    /*
     * This function will save all of the problems that the user has changed. This will be uploaded
     * from the cloud using elastic search
     * @param problems the list of problems to save

    /*
    public void saveProblemData(ArrayList<ProblemModel> problems) {
        /*
        TODO: will need to add elastic search functionality. For now it will just update the current
        list of problems. THIS IS SPECIFICALLY FOR EDITS

        instance.problems = problems;
    }
    */

    public void setSelectedProblem(int index){
       DataModel.getInstance().setSelectedProblem(index);
    }

    public ProblemModel getSelectedProblem(){
        return DataModel.getInstance().getSelectedProblem();
    }

    public ArrayList<ProblemModel> getProblems(){
        return DataModel.getInstance().getProblems();
    }

}