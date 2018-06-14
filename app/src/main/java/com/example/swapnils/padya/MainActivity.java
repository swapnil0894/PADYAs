package com.example.swapnils.padya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import database.DataBaseHelper;

public class MainActivity extends Activity implements View.OnClickListener {


    Button btn_addsurvey,btn_surveydetails,btn_getbackup,btn_showsurvey_details;
    DataBaseHelper dataBaseHelper;
    Context context=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        eventhandler();
    }

    private void eventhandler() {
        btn_addsurvey.setOnClickListener(this);
        btn_surveydetails.setOnClickListener(this);
        btn_getbackup.setOnClickListener(this);
        btn_showsurvey_details.setOnClickListener(this);
    }

    private void init() {
        dataBaseHelper=new DataBaseHelper(context);
        dataBaseHelper.UpdateDB();
        btn_addsurvey=(Button)findViewById(R.id.btn_addsurvey);
        btn_surveydetails=(Button)findViewById(R.id.btn_surveydetails);
        btn_getbackup=(Button)findViewById(R.id.btn_getbackup);
        btn_showsurvey_details=(Button)findViewById(R.id.btn_showsurvey_details);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addsurvey:

                Utilities.showAlertDialog(MainActivity.this,
                        "Alert", "Add survey Clicked.", ApplicationConstants.ALERT);
                Intent i =new Intent(MainActivity.this, AddSurvey.class);
                startActivity(i);
                break;

            case R.id.btn_surveydetails:
                Utilities.showAlertDialog(MainActivity.this,
                        "Alert", "Survey Details Clicked.", ApplicationConstants.ALERT);
                break;

            case  R.id.btn_showsurvey_details:
//                try {
//                    if (dataBaseHelper.copyChecklistDbToFolder())
//                        Utilities.showMessageString("Backup saved sucessfully to folder.", MainActivity.this);
//                    else
//                        Utilities.showMessageString("Error accored during backup.", MainActivity.this);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                Intent in =new Intent(MainActivity.this, SurveyList.class);
                startActivity(in);
                break;


            case  R.id.btn_getbackup:
                try {
                    if (dataBaseHelper.copyChecklistDbToFolder())
                        Utilities.showMessageString("Backup saved sucessfully to folder.", MainActivity.this);
                    else
                        Utilities.showMessageString("Error accored during backup.", MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
