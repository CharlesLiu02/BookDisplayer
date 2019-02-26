package com.hfad.book;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hfad.imgur.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<BookItem> bookItems;
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView author;
        private RatingBar ratingBar;

        public BookViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book_image);
            title = itemView.findViewById(R.id.textView_book_title);
            author = itemView.findViewById(R.id.textView_book_author);
            ratingBar = itemView.findViewById(R.id.ratingBar_book_rating);
        }
    }

    public BookAdapter(List<BookItem> bookItems){
        this.bookItems = bookItems;
    }

    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_book, viewGroup, false);
        BookViewHolder bookViewHolder = new BookViewHolder(v);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder bookViewHolder, int position) {
        BookItem currentBookItem = bookItems.get(position);
        Glide.with(bookViewHolder.imageView).load(currentBookItem.getUrl()).into(bookViewHolder.imageView);
        //bookViewHolder.imageView.setImageDrawable(LoadImageFromWebOperations(currentBookItem.getUrl()));
        bookViewHolder.author.setText(currentBookItem.getAuthor());
        bookViewHolder.title.setText(currentBookItem.getTitle());
        bookViewHolder.ratingBar.setRating((float)currentBookItem.getRating());
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            Log.e("drawable", d.toString());
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
