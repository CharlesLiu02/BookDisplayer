package com.hfad.book;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hfad.imgur.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookItems;
    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView author;
        private RatingBar ratingBar;

        public BookViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_book_image);
            title = itemView.findViewById(R.id.textView_book_title);
            author = itemView.findViewById(R.id.textView_book_author);
            ratingBar = itemView.findViewById(R.id.ratingBar_book_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    Book book = bookItems.get(pos);

                    Gson gson = new Gson();
                    String json = gson.toJson(book);

                    Intent intent = new Intent(v.getContext(), BookActivity.class);
                    intent.putExtra("book", json);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public BookAdapter(List<Book> bookItems){
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
        Book currentBook = bookItems.get(position);
        BookInformation currentBookInformation = currentBook.getBookInformation();
        Glide.with(bookViewHolder.imageView).load(currentBookInformation.getImageUrl()).into(bookViewHolder.imageView);
        bookViewHolder.author.setText(currentBookInformation.getAuthor().getName());
        bookViewHolder.title.setText(currentBookInformation.getTitle());
        bookViewHolder.ratingBar.setRating((float)currentBook.getAverageRating());
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }
}
