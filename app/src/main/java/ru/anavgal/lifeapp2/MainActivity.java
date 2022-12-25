package ru.anavgal.lifeapp2;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Database db = new Database();

        try {
            db.dbQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

