package com.hfad.book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hfad.imgur.R;

import org.json.XML;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editTextSearchTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        searchImages();
    }

    private void wireWidgets() {

    }

    private void searchImages() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://goodreads.com")
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                .build();

        //retrofit creates interface
        BookService bookService = retrofit.create(BookService.class);

        //Call<BookResponse> call = bookService.searchByKeyword(Credentials.API_KEY, editTextSearchTerms.getText().toString());
        Call<BookResponse> call = bookService.searchByKeyword(Credentials.API_KEY, "");

        //execute call on background thread
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if(!response.isSuccessful()){
                    Log.e("enqueue", "code: " + response.code());
                    return;
                }
                List<Book> books = response.body().getSearch().getResults();
                Log.e("enqueue", "onResponse: " + books.toString());
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("enqueue", "onFailure: " + t.getMessage());
            }
        });
    }
}
