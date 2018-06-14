package com.example.swapnils.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.swapnils.padya.R;
import com.example.swapnils.pojo.SurveyDetailsDbPojo;
import com.google.gson.Gson;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import database.DataBaseHelper;

/**
 * Created by shrikantk on 26-02-2018.
 */

public class SurveyAdapter extends RecyclerView
        .Adapter<SurveyAdapter
        .MyViewHolder> {

    ArrayList<SurveyDetailsDbPojo> surveyValuesList;
    Context mContext;
   DataBaseHelper dataBaseHelper;

    public SurveyAdapter(Context context, ArrayList<SurveyDetailsDbPojo> surveyValuesList) {
        this.mContext = context;
        this.surveyValuesList = surveyValuesList;
        this.dataBaseHelper = new DataBaseHelper(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SurveyDetailsDbPojo pojo = surveyValuesList.get(position);
        int surveyno = pojo.getSurvey_no();
        String insurer_name=pojo.getInsurer_name();
        String insured_company=pojo.getInsured_company();
        String veichale_no=pojo.getVeichale_no();
        String type=pojo.getType();
        String survey_date=pojo.getSurvey_date();
        String place=pojo.getPlace();
        String estimeted_amount=pojo.getEstimeted_amount();
        String asserted_amount=pojo.getAsserted_amount();
        String actvity=pojo.getActvity();
        String DateTime=pojo.getDateTime();

        holder.sr_no_textview.setText(Integer.toString(surveyno));
        holder.insurer_name_textview.setText(insurer_name);
        holder.veichale_no_textview.setText(veichale_no);
        holder.date_textview.setText(survey_date);







    }



    @Override
    public int getItemCount() {
//        if (cartValuesList == null)
//            return 0;
        return surveyValuesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sr_no_textview, insurer_name_textview, veichale_no_textview,date_textview;


        public MyViewHolder(View view) {
            super(view);
            sr_no_textview = (TextView) itemView.findViewById(R.id.sr_no_textview);
            insurer_name_textview = (TextView) itemView.findViewById(R.id.insurer_name_textview);
            veichale_no_textview = (TextView) itemView.findViewById(R.id.veichale_no_textview);
            date_textview = (TextView) itemView.findViewById(R.id.date_textview);


        }
    }
}
