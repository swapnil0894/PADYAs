package com.example.swapnils.padya;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import database.DataBaseHelper;

import static java.lang.String.valueOf;

public class AddSurvey extends Activity implements View.OnClickListener {

    EditText et_survey_no,et_customername,et_insured,et_veichaleno,et_type,et_dos,et_place,et_estimatedamount,et_assertedamount,et_actvity;
    Button btn_accept;
    DataBaseHelper dataBaseHelper;
    Context context=AddSurvey.this;
    int num=0,currentcount;
    private Calendar mcalendar;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);
        init();
        setDefaults();
        setEventHandlers();
    }

    private void init() {
        dataBaseHelper=new DataBaseHelper(context);
        et_survey_no=(EditText)findViewById(R.id.et_survey_no);
        et_customername=(EditText)findViewById(R.id.et_customername);
        et_insured=(EditText)findViewById(R.id.et_insured);
        et_veichaleno=(EditText)findViewById(R.id.et_veichaleno);
        et_type=(EditText)findViewById(R.id.et_type);
        et_dos=(EditText)findViewById(R.id.et_dos);
        et_place=(EditText)findViewById(R.id.et_place);
        et_estimatedamount=(EditText)findViewById(R.id.et_estimatedamount);
        et_assertedamount=(EditText)findViewById(R.id.et_assertedamount);
        et_actvity=(EditText)findViewById(R.id.et_actvity);

        btn_accept=(Button) findViewById(R.id.btn_accept);
    }

    private void setDefaults() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        et_dos.setText(formattedDate);

    }
    private void setEventHandlers() {
        btn_accept.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String  srv_no=et_survey_no.getText().toString();
            String    cust_name=et_customername.getText().toString();
            String    insur_name=et_insured.getText().toString();
            String    viech_no=et_veichaleno.getText().toString();
            String    sur_type=et_type.getText().toString();
            String    sur_date=et_dos.getText().toString();
            String    sur_place=et_place.getText().toString();
            String    est_amount=et_estimatedamount.getText().toString();
            String    assur_amount=et_assertedamount.getText().toString();
            String    sur_actvity=et_actvity.getText().toString();

              long result = dataBaseHelper.insertSurveyDetails(cust_name, insur_name, viech_no, sur_type, sur_date,sur_place,est_amount,assur_amount,sur_actvity);
                if (result == -1) {
                    Toast.makeText(context, "Failed to add patient. Try again later..", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(context, "Survey Added Successfully", Toast.LENGTH_SHORT).show();
                    et_survey_no.setText("");
                    et_customername.setText("");
                    et_insured.setText("");
                    et_veichaleno.setText("");
                    et_type.setText("");
                    et_dos.setText("");
                    et_place.setText("");
                    et_estimatedamount.setText("");
                    et_assertedamount.setText("");
                    et_actvity.setText("");

                    Intent i= new Intent(AddSurvey.this,MainActivity.class);
                    startActivity(i);

                   // patientCount++;

                }
            }
        });
    }
}
