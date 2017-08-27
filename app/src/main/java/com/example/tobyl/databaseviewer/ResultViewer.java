package com.example.tobyl.databaseviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.panatrip.datagridview.DataGridView;

public class ResultViewer extends AppCompatActivity {
    public static final String QUERY_NO = "query_no";
    public static final String ACCESS_LEVEL = "access_level";
    private DataGridView dataGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_viewer);
        Intent intent = getIntent();
        String REMOTE_ADDR = (String)intent.getExtras().get("REMOTE_ADDR");
        String USER = (String) intent.getExtras().get("USER");
        String PASSWORD = (String)intent.getExtras().get("PASSWORD");

        //init UI components
        dataGridView = (DataGridView)findViewById(R.id.datagridview);

        //get Query
        int query_no = (int)getIntent().getExtras().get(QUERY_NO);
        int access_level =(int)getIntent().getExtras().get(ACCESS_LEVEL);
       // Query query = Query.queries[query_no];
        Query query = Query.getQueries(access_level)[query_no];
        //extract sql from query
        String sql = query.getSql();
        System.out.println(sql);
        //test account******
        REMOTE_ADDR = "192.168.0.101:1433";
        USER = "sa";
        PASSWORD = "zblogin";

        //connect to server and get results
        /*DatabaseConnection databaseConnection = new DatabaseConnection(dataGridView,REMOTE_ADDR,USER,PASSWORD);
        databaseConnection.execute(sql);*/
    }
}
