package com.example.tobyl.databaseviewer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TableRow;
import android.widget.TextView;

import net.panatrip.datagridview.DataGridView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobyl on 8/1/2017.
 */

public class DatabaseConnection extends AsyncTask<String, Integer, ResultSet>
{
    private Connection conn =null;
    private String REMOTE_ADDR;
    private String user;
    private String password;
    private DataGridView dataGridView;

    public DatabaseConnection(DataGridView dataGridView, String REMOTE_ADDR, String user, String password)
    {
        this.REMOTE_ADDR = REMOTE_ADDR;
        this.user = user;
        this.password = password;
        this.dataGridView = dataGridView;
    }

    @Override
    protected  ResultSet doInBackground(String...params)
    {
        conn = openConnection(REMOTE_ADDR,user,password);
        /*if(conn != null)
        {
           // tv_result.setText("Connected to database");
            System.out.println("Connected to database");

        }else{
            //tv_result.setText("Cannot Connect to database");
            System.out.print("Cannot Connect to database");
        }*/
        ResultSet resultSet = query(params[0]);

        return resultSet;
    }

    @Override
    protected  void onPostExecute(ResultSet resultSet)
    {
        if(resultSet != null)
        {
            loadData(resultSet);
            System.out.print("Query success");


        }else{
            //tv_result.setText("Query failed");
            System.out.print("Query failed");
        }

    }

    public Connection openConnection(String REMOTE_ADDR, String user, String password)
    {

        try{
            final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            //final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            String url = "jdbc:sqlserver://"+REMOTE_ADDR+";DatabaseName=kcerp";
            //String url = "jdbc:mysql://"+REMOTE_ADDR+"/";
            conn = DriverManager.getConnection(url,user,password);

        }catch (ClassNotFoundException e){
            conn=null;
            e.printStackTrace();
        } catch (SQLException e) {
            conn=null;
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet query(String sql)
    {
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            //If there is any results from query
            ResultSetMetaData rsmd = resultSet.getMetaData();
           int columnsNumber = rsmd.getColumnCount();
            System.out.println("Query success: Columns - "+columnsNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /*try {
                if (resultSet!=null){
                    System.out.println("ResultSet is closed");
                    resultSet.close();
                }
                if (statement!=null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }
        if (resultSet==null){
            System.out.println("ResultSet is NULL");
        }
        return resultSet;
    }

    public boolean execSQL(String sql)
    {
        boolean execResult = false;
        Statement statement = null;
        try{
            statement = conn.createStatement();
            if(statement!=null){
                execResult=statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
            e.printStackTrace();
        }
        return execResult;
    }

    private void print(String x){
        System.out.println(x);
    }

    /*
   Convert received table results into lists
    */
    private List<List<String>> getTableRows(ResultSet resultSet){
        List<List<String>> rows = new ArrayList<>();
        List<String> header = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            for (int col = 0; col < rsmd.getColumnCount(); col++) {
               /* String columnName= rsmd.getColumnName(col+1);
                print(columnName);*/
                header.add(rsmd.getColumnName(col+1));
            }

            rows.add(header);

            resultSet.last();
            int rowCount=resultSet.getRow();
            resultSet.first();

            if (rowCount == 0) {
                return rows;
            }

            do {
                List<String> row = new ArrayList<>();
                for (int col = 0; col < rsmd.getColumnCount(); col++) {
                    String strData = resultSet.getString(col+1);
                    row.add(strData);
                }
                rows.add(row);
            } while (resultSet.next());


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    /*
    Load results into dataGridView
     */
    private void loadData(ResultSet resultSet){
        List<List<String>> result = null;
        try {
            if( resultSet != null && resultSet.next()){
                result = getTableRows(resultSet);
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataGridView.setData(result, true);
    }


}
