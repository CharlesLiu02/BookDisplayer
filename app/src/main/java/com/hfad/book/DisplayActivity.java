package com.hfad.book;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.hfad.imgur.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Book> results;
    private RecyclerView.LayoutManager layoutManager;
    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        wireWidgets();

        //receives list of books object from MainActivity
        Gson gson = new Gson();
        results = gson.fromJson(getIntent().getStringExtra("searchResults"), BookResults.class).getWorks();

        layoutManager = new LinearLayoutManager(this);
        bookAdapter = new BookAdapter(results);

        //set layout manager before adapter
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);
    }


    private void wireWidgets() {
        recyclerView = findViewById(R.id.recyclerView_display);
    }
}
