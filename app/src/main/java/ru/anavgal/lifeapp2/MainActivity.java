package ru.anavgal.lifeapp2;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream fis;
        Properties property = new Properties();

        try {
            fis = this.getResources().openRawResource(R.raw.config); //new FileInputStream("./././././app/src/main/res/config.properties");//("src/main/resources/config.properties");
            property.load(fis);

            String host = property.getProperty("db.host");
            String login = property.getProperty("db.login");
            String password = property.getProperty("db.password");

            System.out.println("HOST: " + host
                    + ", LOGIN: " + login
                    + ", PASSWORD: " + password);

            Database db = new Database(host, login, password);

            // Database db = new Database("10.0.2.2", "postgres", "KapiTal14");

            try {
                db.dbQuery(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }





    }


}

