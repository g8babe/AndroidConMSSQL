package com.fju.mymssqlserverdatabaseconnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private static String ip = "172.20.10.2";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "Project";
    private static String username = "sa";
    private static String password = "0000";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database;

    private Connection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, password);
            textView.setText("SUCCESS");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textView.setText("ERROR");
        } catch (SQLException e) {
            e.printStackTrace();
            textView.setText("FAILURE");
        }

    }

    public void sqlButton(View view) {

        if (connection!=null) {

            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Project.dbo.T_USER_DATA;");
                while (resultSet.next()) {
                    textView.setText(resultSet.getString(2));
                }

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }
        else  {
            textView.setText("Connection is null");
        }

    }
}
