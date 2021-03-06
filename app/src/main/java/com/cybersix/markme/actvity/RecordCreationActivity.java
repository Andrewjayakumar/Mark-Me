/**
 * CMPUT 301 Team 24
 *
 * This is the record creation activity that the user is required to fill in before the record
 * is saved onto the cloud
 *
 * Version 0.1
 *
 * Date: 2018-11-11
 *
 * Copyright Notice
 * @author Curtis Goud
 * @see com.cybersix.markme.model.RecordModel
 * @see com.cybersix.markme.fragment.RecordListFragment
 */
package com.cybersix.markme.actvity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersix.markme.model.BodyLocation;
import com.cybersix.markme.model.EBodyPart;
import com.cybersix.markme.R;
import com.cybersix.markme.fragment.RecordListFragment;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.controller.RecordController;

public class RecordCreationActivity extends AppCompatActivity {
    EBodyPart selectedPart = null;
    private EditText recordAddTitleText;
    private EditText recordAddDescText;
    private TextView recordBodyLocation;
    private Button buttonCancelRecord;
    private Button buttonAddRecord;
    private RecordController recordController = RecordController.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_creation);
        Intent prvIntent = getIntent();
        selectedPart = (EBodyPart) prvIntent.getSerializableExtra("BodyPart");
        recordBodyLocation = (TextView) findViewById(R.id.recordBodyLocation);
        recordAddTitleText = (EditText) findViewById(R.id.recordAddTitleText);
        recordAddDescText = (EditText) findViewById(R.id.recordAddDescText);

        buttonAddRecord = (Button) findViewById(R.id.buttonAddRecord);
        buttonCancelRecord = (Button) findViewById(R.id.buttonCancelRecord);

        buttonAddRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title = recordAddTitleText.getText().toString();
                String desc = recordAddDescText.getText().toString();
                BodyLocation bl = new BodyLocation(selectedPart);
                RecordModel record = recordController.createNewRecord(title,desc,null,null,bl);

                if(title.length() > 0){
                    newRecordAlert();
                } else {
                    Toast.makeText(RecordCreationActivity.this, "Title is too short", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancelRecord.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        recordBodyLocation.setText("Body Location: " + selectedPart.toString());
    }

    private void newRecordAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Record Added!");
        builder.setMessage("Would you like to add a Photo or Location to the Record?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Send to edit records instead of prev screen
                Intent data = new Intent();
                data.putExtra(RecordListFragment.EXTRA_RECORD_INDEX, recordController.getSelectedProblemRecords().size()-1);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }

}
