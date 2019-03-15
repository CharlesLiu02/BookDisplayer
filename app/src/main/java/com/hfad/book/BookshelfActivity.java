package com.hfad.book;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hfad.imgur.R;

import java.util.List;

public class BookshelfActivity extends AppCompatActivity {
    private List<Book> results;
    private String searchTerm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    fm.beginTransaction().replace(R.id.container_bookshelf_activity, new DisplayFragment()).commit();
                    return true;
                case R.id.navigation_favorite_books:
                    fm.beginTransaction().replace(R.id.container_bookshelf_activity, new FavoriteBooksFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        Gson gson = new Gson();
        results = gson.fromJson(getIntent().getStringExtra("searchResults"), BookResults.class).getWorks();
        searchTerm = getIntent().getStringExtra("searchTerm");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container_bookshelf_activity, new DisplayFragment()).commit();
    }

    public String getSearchTerm(){
        return searchTerm;
    }

    public List<Book> getResults() {
        return results;
    }
}
