package com.hfad.book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hfad.book.Book;
import com.hfad.imgur.R;

import org.w3c.dom.Text;

public class BookActivity extends AppCompatActivity {
    private Book book;
    private TextView textViewTitle;
    private TextView textViewAuthor;
    private TextView textViewRating;
    private TextView textViewReviewTitle;
    private TextView textViewReview;
    private ImageView imageViewBookImage;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        wireWidgets();
        Gson gson = new Gson();
        book = gson.fromJson(getIntent().getStringExtra("book"), Book.class);
        setViews();
    }

    private void setViews() {
        textViewTitle.setText(book.getBookInformation().getTitle());
        textViewAuthor.setText("By " + book.getBookInformation().getAuthor().getName());
        textViewRating.setText(book.getAverageRating() + "/5");
        textViewReviewTitle.setText("Top Review: ");
        //textViewReview.setText();
        Glide.with(imageViewBookImage).load(book.getBookInformation().getImageUrl()).into(imageViewBookImage);
        ratingBar.setRating((float)book.getAverageRating());

    }

    private void wireWidgets() {
        textViewTitle = findViewById(R.id.textView_book_activity_title);
        textViewAuthor = findViewById(R.id.textView_book_activity_author);
        textViewRating = findViewById(R.id.textView_book_activity_rating);
        textViewReviewTitle = findViewById(R.id.textView_book_activity_review_title);
        textViewReview = findViewById(R.id.textView_book_activity_review);
        imageViewBookImage = findViewById(R.id.imageView_book_activity_book_image);
        ratingBar = findViewById(R.id.ratingBar_book_activity_rating);
    }
}
