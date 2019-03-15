package com.hfad.book;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hfad.imgur.R;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DisplayFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Book> results;
    private RecyclerView.LayoutManager layoutManager;
    private BookAdapter bookAdapter;
    private EditText editTextSearch;
    private Button buttonSearch;
    private String searchTerm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_display, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView_display);
        editTextSearch = rootView.findViewById(R.id.editText_display_search);
        buttonSearch = rootView.findViewById(R.id.button_display_search);

        //receives list of books object from MainActivity
        BookshelfActivity bookshelfActivity = (BookshelfActivity)getActivity();
        searchTerm = bookshelfActivity.getSearchTerm();
        results = bookshelfActivity.getResults();
        editTextSearch.setText(searchTerm);

        layoutManager = new LinearLayoutManager(getActivity());
        bookAdapter = new BookAdapter(results);

        //set layout manager before adapter
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);

        //user searches from DisplayActivity
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear list of books
                results.clear();
                searchBooks();
            }
        });
        return rootView;
    }


    private void searchBooks() {
        //XML converter to string
        TikXml tikXml = new TikXml.Builder()
                .exceptionOnUnreadXml(false) // set this to false if you don't want that an exception is thrown
                .build();

        //retrofit gets information from internet and returns XML
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://goodreads.com/")
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();

        //retrofit creates interface
        BookService bookService = retrofit.create(BookService.class);

        //call to return list of books
        Call<BookResponse> call = bookService.searchByKeyword(Credentials.API_KEY, editTextSearch.getText().toString());

        //execute call on background thread
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                //checks for error codes such as 404
                if (!response.isSuccessful()) {
                    Log.e("enqueue", "code: " + response.code());
                    return;
                }
                //get response abd put in BookResults
                List<Book> newResults = response.body().getSearch().getResults().getWorks();
                //if no books were found, invalid search
                if (newResults == null) {
                    Toast.makeText(getActivity(), "No books found. Please enter another search.", Toast.LENGTH_SHORT).show();
                } else {
                    //add new search results
                    results.addAll(newResults);
                    //need to call within the method because it is Async so if notifyDataSetChanged is called up above
                    //then nothing has changed yet
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.e("enqueue", "onFailure: " + t.getMessage());
            }
        });
    }
}
