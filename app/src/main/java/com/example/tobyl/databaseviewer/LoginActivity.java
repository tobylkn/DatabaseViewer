package com.example.tobyl.databaseviewer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import net.panatrip.datagridview.DataGridView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

   private DataGridView dataGridView;
    private String REMOTE_ADDR;
    private String USER;
    private String PASSWORD;
    private EditText remoteAddr;
    private EditText userName;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init UI components
        remoteAddr = (EditText)findViewById(R.id.remoteAddr);
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        dataGridView = (DataGridView)findViewById(R.id.datagridview);

        //localhost = 10.0.2.2
        //Set default values
        remoteAddr.setText("192.168.0.101:1433");
        userName.setText("sa");
        password.setText("zblogin");

    }

    public void onConnect(View view)
    {
        //init connection
        REMOTE_ADDR = remoteAddr.getEditableText().toString();
        USER = userName.getEditableText().toString();
        PASSWORD = password.getEditableText().toString();

        //pass results to reesultviewer
        /*Intent intent = new Intent(LoginActivity.this,ResultViewer.class);
        intent.putExtra("REMOTE_ADDR",REMOTE_ADDR);
        intent.putExtra("USER",USER);
        intent.putExtra("PASSWORD",PASSWORD);
        startActivity(intent);*/

        /*
        TODO Verify user name and password
         */
        ConnectionAsyncTask conn_async_task = new ConnectionAsyncTask();
        conn_async_task.execute();
    }

    public class ConnectionAsyncTask extends AsyncTask<String,Integer,Connection>{

        protected Connection doInBackground(String ...params){
            Connection conn = openConnection(REMOTE_ADDR,USER,PASSWORD);
            return conn;
        }
        protected void onPostExecute(Connection conn){
            if(conn!=null){
                //Setup DatabaseConnection
                //DatabaseConnection connection = new DatabaseConnection()
                //To QueryActivity
                System.out.println("Connected");
                Intent intent = new Intent(LoginActivity.this,QueryActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        }

        public Connection openConnection(String REMOTE_ADDR, String user, String password)
        {
            Connection conn;
            try{
                final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                //final String DRIVER_NAME = "net.sourceforge.jtds.jdbc.Driver";
                //final String DRIVER_NAME = "com.mysql.jdbc.Driver";
                Class.forName(DRIVER_NAME);
                String url = "jdbc:sqlserver://"+REMOTE_ADDR+";DatabaseName=kcerp";
                //String url = "jdbc:jtds:sqlserver://"+REMOTE_ADDR+";DatabaseName=kcerp";
                //String url = "jdbc:mysql://"+REMOTE_ADDR+"/";
                System.out.println("Connecting...");
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
    }
}
