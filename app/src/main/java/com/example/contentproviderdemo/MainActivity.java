package com.example.contentproviderdemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EmployeeData> list = new ArrayList<>();
        adapter = new EmployeeAdapter(list, this);
        recyclerView.setAdapter(adapter);

        Button submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert data into the database using content provider
                // ...

                // After inserting, update the RecyclerView
                updateRecyclerView();
            }
        });
    }

    @SuppressLint("Range")
    private void updateRecyclerView() {
        EditText nameText = findViewById(R.id.textName);
        EditText idText = findViewById(R.id.textId);
        EditText designationText = findViewById(R.id.textDesignation);
        EditText salaryText = findViewById(R.id.textSalary);

        // class to add values in the database
        ContentValues values = new ContentValues();

        // fetching text from user
        values.put(MyContentProvider.name, nameText.getText().toString());
        values.put(MyContentProvider.id, Integer.parseInt(idText.getText().toString()));
        values.put(MyContentProvider.salary, Integer.parseInt(salaryText.getText().toString()));
        values.put(MyContentProvider.designation, designationText.getText().toString());

        // inserting into the database through content URI
        getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

        // displaying a toast message
        Toast.makeText(MainActivity.this, "New Record Inserted", Toast.LENGTH_LONG).show();

        nameText.setText("");
        idText.setText("");
        designationText.setText("");
        salaryText.setText("");
        // Query data from the content provider and update the RecyclerView
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            try {
                List<EmployeeData> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(MyContentProvider.name));
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyContentProvider.id)));
                    String designation = cursor.getString(cursor.getColumnIndex(MyContentProvider.designation));
                    int salary = Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyContentProvider.salary)));
                    list.add(new EmployeeData(name, id, designation, salary));
                }

                adapter.setData(list);
                adapter.notifyItemInserted(list.size()-1);
            } finally {
                cursor.close();
            }
        }
    }
}
