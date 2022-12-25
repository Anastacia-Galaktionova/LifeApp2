package ru.anavgal.lifeapp2;

import java.sql.*;

public class Database {

    private Connection connection;
    private final String host = "10.0.2.2";
    private final String database = "LifeApp2";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "KapiTal14";
    private String url = "jdbc:postgresql://%s:%d/%s";
    private boolean status;

    public Database()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        System.out.println("connection status:" + status);
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
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

    public void dbQuery() throws SQLException{
        Connection dbConnection;
        PreparedStatement prepStatement = null;
        String selectTableSQL = "select * from public.notes_list";

        try {

            prepStatement = connect().prepareStatement(selectTableSQL);
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