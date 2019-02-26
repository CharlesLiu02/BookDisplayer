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
    private List<BookItem> bookItems;
    private RecyclerView.LayoutManager layoutManager;
    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        wireWidgets();

        Gson gson = new Gson();
        results = gson.fromJson(getIntent().getStringExtra("searchResults"), BookResults.class).getWorks();
        bookItems = new ArrayList<>();

        addBookItems(results);

        layoutManager = new LinearLayoutManager(this);
        bookAdapter = new BookAdapter(bookItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);
    }

    private void addBookItems(List<Book> results) {
        for(int i = 0; i < results.size(); i++){
            Book currentBook = results.get(i);
            BookInformation currentBookInformation = currentBook.getBookInformation();
            bookItems.add(new BookItem(currentBookInformation.getImageUrl(), currentBookInformation.getTitle(),
                            currentBookInformation.getAuthor().getName(), currentBook.getAverageRating()));
        }
    }

    private void wireWidgets() {
        recyclerView = findViewById(R.id.recyclerView_display);
    }
}
