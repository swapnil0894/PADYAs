package com.example.swapnils.padya;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.Suppress;
import android.view.View;

import com.example.swapnils.adapter.SurveyAdapter;
import com.example.swapnils.pojo.SurveyDetailsDbPojo;

import java.util.ArrayList;
import java.util.List;

import database.DataBaseHelper;

public class SurveyList extends Activity {

    RecyclerView display_report_recyclerview;
    ArrayList<SurveyDetailsDbPojo> surveyValuesList;
    Context context=SurveyList.this;
    DataBaseHelper dataBaseHelper;
    private RecyclerView mRecyclerView;
    static private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private DisplayReportAdapter mAdapter;
//    private List<DisplayReportList> reportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        init();
        getValuesFromDb();

    }

    private void init() {
        dataBaseHelper = new DataBaseHelper(context);
        mRecyclerView=(RecyclerView) findViewById(R.id.display_report_recyclerview);
    }

    private void getValuesFromDb() {
        surveyValuesList = dataBaseHelper.getSurveys();
        if (surveyValuesList == null || surveyValuesList.size() == 0) {
            Utilities.showAlertDialog(context, "Alert", "No records in database.", ApplicationConstants.ALERT);
            return;
        }
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SurveyAdapter(context, surveyValuesList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
