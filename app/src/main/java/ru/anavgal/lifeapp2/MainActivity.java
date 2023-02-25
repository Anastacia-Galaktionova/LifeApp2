package ru.anavgal.lifeapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream fis;
        Properties property = new Properties();

        try {
            fis = this.getResources().openRawResource(R.raw.config);
            property.load(fis);

            String host = property.getProperty("db.host");
            String login = property.getProperty("db.login");
            String password = property.getProperty("db.password");

            System.out.println("HOST: " + host
                    + ", LOGIN: " + login
                    + ", PASSWORD: " + password);

            Database db = new Database(host, login, password);

            /*
            try {
                ResultSet rs = db.dbQueryAllNotes(login, password);

                while (rs.next()){
                    for (int count=2; count<=3;count++)
                    {
                        System.out.println(" id: " + rs.getString(1)
                                + " title: " + rs.getString(2)
                                + " text: " + rs.getString(3));
                        TextView text = findViewById(R.id.row1text);
                        text.setTextColor(Color.BLACK);
                        text.setText(rs.getString(count));
                        TextView text2 = findViewById(R.id.row2text);
                        text2.setTextColor(Color.BLACK);
                        text2.setText(rs.getString(count));
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

             */
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        //TableLayout table = findViewById(R.id.noteListTable);
        //TableRow row = findViewById(R.id.noteListTableRow1);
       // TextView text = findViewById(R.id.row1text);
        //text.setText("ejrhjbafvhjffhvnj kjshfusjhbfsf");


        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);

        database = RoomDB.getInstance(this);
       // notes = database.mainDAO().getAll();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent,101);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
               // database.mainDAO().insert(new_notes);
                notes.clear();
               // notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }

        }



    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }
    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };

}

