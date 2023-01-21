package ru.anavgal.lifeapp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private Connection connection;
    private final String database = "LifeApp2";
    private final int port = 5432;
   private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;


    public Database(String host, String login, String password)
    {

        this.url = String.format(this.url, host, this.port, this.database);
        connect(login, password);
        System.out.println("connection status:" + status);

        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect(String login, String password)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, login, password);
                    status = true;
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.status = false;
        }
        return connection;
    }

    public void dbQuery(String login, String password) throws SQLException{

        PreparedStatement prepStatement = null;
        String selectTableSQL = "select * from public.notes_list";

        try {

            prepStatement = connect(login, password).prepareStatement(selectTableSQL);
            // выполнить SQL запрос
            prepStatement.executeQuery();
            System.out.println("Select is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (prepStatement != null) {
                prepStatement.close();
            }

        }
    }
}