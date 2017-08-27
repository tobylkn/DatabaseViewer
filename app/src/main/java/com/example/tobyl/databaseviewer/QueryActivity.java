package com.example.tobyl.databaseviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class QueryActivity extends AppCompatActivity {
    public final static String CONNECTION = "connectionObject";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        ListView listView = (ListView)findViewById(R.id.listView);
        /*
        Test access level
         */
        int access_level = 1;

        ArrayAdapter<Query> listAdapter = new ArrayAdapter<Query>(this,android.R.layout.simple_list_item_1,Query.getQueries(access_level));
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),position,Toast.LENGTH_LONG).show();
                System.out.println(position+","+id);
                Intent intent = new Intent(QueryActivity.this,ResultViewer.class);
                intent.putExtra(ResultViewer.QUERY_NO,position);
                intent.putExtra(ResultViewer.ACCESS_LEVEL,access_level);
                startActivity(intent);
            }
        });
    }


}
