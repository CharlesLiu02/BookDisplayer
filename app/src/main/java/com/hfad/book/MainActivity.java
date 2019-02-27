package com.hfad.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hfad.imgur.R;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;


import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Serializable{
    private EditText editTextSearchTerms;
    private Button buttonSearch;
    public static final String TAG = "MAINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });
    }

    private void wireWidgets() {
        editTextSearchTerms = findViewById(R.id.editText_main_search);
        buttonSearch = findViewById(R.id.button_main_search);
    }

    private void searchBooks() {

        TikXml tikXml = new TikXml.Builder()
                .exceptionOnUnreadXml(false) // set this to false if you don't want that an exception is thrown
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://goodreads.com/")
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();

        //retrofit creates interface
        BookService bookService = retrofit.create(BookService.class);

        Call<BookResponse> call = bookService.searchByKeyword(Credentials.API_KEY, editTextSearchTerms.getText().toString());

        //execute call on background thread
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(!response.isSuccessful()){
                    Log.e("enqueue", "code: " + response.code());
                    return;
                }
                BookResults results = response.body().getSearch().getResults();
                if(results.getWorks() == null){
                    Toast.makeText(MainActivity.this, "No books found. Please enter another search.", Toast.LENGTH_SHORT).show();
                }else {
                    Gson gson = new Gson();
                    String json = gson.toJson(results);
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("searchResults", json);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("enqueue", "onFailure: " + t.getMessage());
            }
        });
    }
}
