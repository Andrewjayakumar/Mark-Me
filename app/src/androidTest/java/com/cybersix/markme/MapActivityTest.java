package com.cybersix.markme;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.EspressoKey;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.cybersix.markme.BodyFragment;
import com.cybersix.markme.MainActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

public class MapActivityTest {

    NavigationController nav;

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup(){
        nav = NavigationController.getInstance(mainActivityTestRule.getActivity());
        ProblemController.getInstance().setSelectedProblem(0);
        mainActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout,new MapFragment())
                .commitAllowingStateLoss();
    }


    /*
        Use Case: 19
     */
    @Test
    public void testMapAllRecords(){

        //Init data
        RecordController.getInstance();
        ProblemController.getInstance();


        //Confirm displayed
        onView(withId(R.id.g_map)).check(matches(isDisplayed()));

    }

}
