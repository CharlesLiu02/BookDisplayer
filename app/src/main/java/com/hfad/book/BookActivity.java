package com.hfad.book;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hfad.imgur.R;
import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

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
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        wireWidgets();

        //recieves book object from DisplayActivity from intent
        Gson gson = new Gson();
        book = gson.fromJson(getIntent().getStringExtra("book"), Book.class);
        //get summary of specific book using retrofit
        getSummary();
        //displays information on textviews
        setViews();
    }

    private void getSummary() {
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
                if(summary == null) {
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
        Glide.with(imageViewBookImage).load(book.getBookInformation().getImageUrl()).into(imageViewBookImage);
        ratingBar.setRating((float)book.getAverageRating());

    }

    private void wireWidgets() {
        textViewTitle = findViewById(R.id.textView_book_activity_title);
        textViewAuthor = findViewById(R.id.textView_book_activity_author);
        textViewRating = findViewById(R.id.textView_book_activity_rating);
        textViewSummaryTitle = findViewById(R.id.textView_book_activity_summary_title);
        textViewSummary = findViewById(R.id.textView_book_activity_summary);
        imageViewBookImage = findViewById(R.id.imageView_book_activity_book_image);
        ratingBar = findViewById(R.id.ratingBar_book_activity_rating);
    }
}
