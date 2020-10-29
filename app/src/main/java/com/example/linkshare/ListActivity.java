package com.example.linkshare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linkshare.Models.Enlaces;
import com.example.linkshare.adapters.EnlaceAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity  extends AppCompatActivity {

    private EnlaceAdapter eAdapter;
    private RecyclerView eRecycleview;
    private ArrayList<Enlaces> enlacesList = new ArrayList<>();
    private DatabaseReference tDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        tDatabase = FirebaseDatabase.getInstance().getReference();

        eRecycleview = findViewById(R.id.myReciclerView);
        eRecycleview.setLayoutManager(new LinearLayoutManager(this));

        getEnlacesFromFirebase();
    }

    private void getEnlacesFromFirebase(){
        tDatabase.child("texto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    enlacesList.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String texto = ds.child("titulo").getValue().toString();
                        enlacesList.add(new Enlaces(texto));
                    }
                    eAdapter = new EnlaceAdapter(enlacesList, R.layout.list_view);
                    eRecycleview.setAdapter(eAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
