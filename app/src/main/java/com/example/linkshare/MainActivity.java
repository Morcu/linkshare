package com.example.linkshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editCrear;
    private Button btnCrear;
    private DatabaseReference tDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCrear = findViewById(R.id.editCrear);
        btnCrear = findViewById(R.id.btnCrear);

        tDatabase = FirebaseDatabase.getInstance().getReference();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editCrear.getText().toString();
                Map<String, Object> linkMap = new HashMap<>();
                linkMap.put("titulo", text);
                tDatabase.child("texto").push().setValue(linkMap);
            }
        });

    }
}