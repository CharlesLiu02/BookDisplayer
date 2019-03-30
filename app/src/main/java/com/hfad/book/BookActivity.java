package com.hfad.book;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hfad.imgur.R;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BookActivity extends AppCompatActivity{
    private Book book;
    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewRating;
    private TextView textViewSummaryTitle;
    private TextView textViewSummary;
    private ImageView imageViewBookImage;
    private RatingBar ratingBarRating;
    private Button buttonFavoriteBook;
    private List<Book> favoriteBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        wireWidgets();

        favoriteBooks = new ArrayList<>();

        //receives book object from DisplayActivity from intent
        Gson gson = new Gson();
        book = gson.fromJson(getIntent().getStringExtra("book"), Book.class);
        //get summary of specific book using retrofit
        getSummary();
        //displays information on textviews
        setViews();

        buttonFavoriteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookActivity.this, "Added Book to Favorites", Toast.LENGTH_SHORT).show();
                writeToFile(book);
            }
        });

    }

    public void writeToFile(Book book) {
        // check if the file already exists...if it doesn't:
        //then create a new file and write book object
        String fileName = "favoriteBooks.txt";
        File file = new File(getFilesDir(), fileName);
        if(file.exists()) {
            readFile();
            favoriteBooks.add(book);
            Gson gson = new Gson();
            String s = gson.toJson(favoriteBooks);

            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(s.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Gson gson = new Gson();
            List<Book> books = new ArrayList<>();
            books.add(book);
            String s = gson.toJson(books);

            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(s.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void readFile() {
        try {
            FileInputStream fis = getApplicationContext().openFileInput("favoriteBooks.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            String json = sb.toString();
            Gson gson = new Gson();
            favoriteBooks = gson.fromJson(json, new TypeToken<ArrayList<Book>>() {}.getType());
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void getSummary(){
        TikXml tikXml = new TikXml.Builder()
                .exceptionOnUnreadXml(false) // set this to false if you don't want that an exception is thrown
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://goodreads.com/")
                .addConverterFactory(TikXmlConverterFactory.create(tikXml))
                .build();

        //retrofit creates interface
        BookService bookService = retrofit.create(BookService.class);

        Call<DescriptionResponse> call = bookService.searchForSummary(Credentials.API_KEY, book.getBookInformation().getTitle(), book.getBookInformation().getAuthor().getName());

        //execute call on background thread
        call.enqueue(new Callback<DescriptionResponse>() {
            @Override
            public void onResponse(Call<DescriptionResponse> call, Response<DescriptionResponse> response) {
                if(!response.isSuccessful()){
                    Log.e("enqueue", "code: " + response.code());
                    return;
                }
                //gets summary
                String summary = response.body().getDescription().getSummary();
                if(summary.equals("")) {
                    textViewSummary.setText("No description available.");
                }else{
                    //gets HTML from string and interprets it
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //newer versions of android uses different method
                        textViewSummary.setText(Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        textViewSummary.setText(Html.fromHtml(summary));
                    }
                }
            }

            @Override
            public void onFailure(Call<DescriptionResponse> call, Throwable t) {
                Log.e("enqueue", "onFailure: " + t.getMessage());
            }
        });

    }

    private void setViews() {
        textViewTitle.setText(book.getBookInformation().getTitle());
        textViewAuthor.setText("By " + book.getBookInformation().getAuthor().getName());
        textViewRating.setText(book.getAverageRating() + "/5");
        textViewSummaryTitle.setText("Summary: ");
        //uses glide to display book cover from url
        Glide.with(imageViewBookImage).load(book.getBookInformation().getImageUrl()).into(imageViewBookImage);
        ratingBarRating.setRating((float)book.getAverageRating());

    }

    private void wireWidgets() {
        textViewTitle = findViewById(R.id.textView_book_activity_title);
        textViewAuthor = findViewById(R.id.textView_book_activity_author);
        textViewRating = findViewById(R.id.textView_book_activity_rating);
        textViewSummaryTitle = findViewById(R.id.textView_book_activity_summary_title);
        textViewSummary = findViewById(R.id.textView_book_activity_summary);
        imageViewBookImage = findViewById(R.id.imageView_book_activity_book_image);
        ratingBarRating = findViewById(R.id.ratingBar_book_activity_rating);
        buttonFavoriteBook = findViewById(R.id.button_book_favorite_book);
    }
}
