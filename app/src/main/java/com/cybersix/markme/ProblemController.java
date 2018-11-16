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
 * @see com.cybersix.markme.ProblemModel
 */
package com.cybersix.markme;

import java.util.ArrayList;
import java.util.Date;

public class ProblemController {
    // set up the controller instance with lazy construction
    private static ProblemController instance = null;
    public ArrayList<ProblemModel> problems;

    /**
     * This contructor will set up the controller variable "problems"
     */
    protected ProblemController() {
        problems = new ArrayList<ProblemModel>();
    }

    // Lazy construction of instance.
    public static ProblemController getInstance() {
        if (instance == null) {
            instance = new ProblemController();
            uploadFakeData();
        }
        return instance;
    }

    // TODO: THIS IS THE FAKE DATA UPDLOAD FOR THE LIST OF PROBLEMS
    private static void uploadFakeData() {
        // This function will just load fake problems to the data
        for (int i = 0; i < 10; i++) {
            String title = "Fake Title " + Integer.toString(i);
            String Description = "This is a fake description for title " + Integer.toString(i);
            instance.createNewProblem(title, Description);
        }
    }

    // set up all of the necessary public functions that this controller will go through

    /**
     * This function will create a new problem for the user that called it and will add it
     * to the problem list.
     *
     * @param title the title of the problem
     * @param description the description of the problem
     * @author Jose Ramirez
     */
    public void createNewProblem(String title, String description) {
        try {
            ProblemModel newProblem = new ProblemModel(title, description);

            // add the problem to the list of problems
            instance.problems.add(newProblem);
        }
        catch (Exception e) {
            // display an error that the problem has too long of a title
            String message = e.getMessage();

        }

    }

    /**
     * This function will edit one of the selected problems
     * @param index the problem index to edit
     * @param newTitle the new title for the edit
     * @param newDescription the new description for the edit
     */
    public void editProblem(int index, String newTitle, String newDescription) {
        // To find the problem, we compare the date as the date should be unique enough.
        ProblemModel problem = instance.problems.get(index);
        // set the new title and description
        try {
            // does references work here? Testing will check
            problem.setTitle(newTitle);
            problem.setDescription(newDescription);
            // then we are done. Display a successful edit

        }
        catch (Exception e) {
            // diaplay and error message that the problem could not be edited
            String message = e.getMessage();
            // TODO: talk to the group about how to display the error message
        }
    }

    /**
     * This function will load the problems based on the user that called this. The user information
     * is stored on the user profile controller
     * @return will return a list of problems that the user has
     */
    public ArrayList<ProblemModel> loadProblemData() {
        // TODO: will need to add elastic search functionality. For now it will always return a
        // TODO: set amount of data that will be uploaded first by the save

        return instance.problems;
    }

    /**
     * This function will save all of the problems that the user has changed. This will be uploaded
     * from the cloud using elastic search
     * @param problems the list of problems to save
     */
    public void saveProblemData(ArrayList<ProblemModel> problems) {
        /*
        TODO: will need to add elastic search functionality. For now it will just update the current
        list of problems
         */
        instance.problems = problems;
    }

}