package com.hfad.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.hfad.imgur.R;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;


import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
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
                Log.e("enqueue", "onResponse: " + results.toString());
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("enqueue", "onFailure: " + t.getMessage());
            }
        });
    }
}
