package com.example.linkshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewLinkActivity extends AppCompatActivity {


    private Button btnNewLink;
    private TextInputEditText txtLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_link_layout);

        btnNewLink = findViewById(R.id.btnNewEnlace);
        txtLink = findViewById(R.id.txtNewUrl);


        btnNewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(NewLinkActivity.this, ReceiveActivity.class);
                Log.i("TXT", txtLink.getText().toString());

                myIntent.putExtra("text",txtLink.getText().toString());
                myIntent.putExtra("Uniqid","From_Activity_A");

                startActivity(myIntent);
            }
        });


    }
}