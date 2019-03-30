package com.hfad.book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hfad.imgur.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.BookViewHolder>{
    //list of favorite books
    //need to initialize to prevent null object reference
    private List<Book> bookItems = new ArrayList<>();
    private int position;

    //needed to implement context menu
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    //class of view holder that defines each view in the recycler view
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

            //create context menu called delete
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(Menu.NONE, R.id.delete, Menu.NONE, "Delete");
                }
            });

            //get position of book clicked in favorite books
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickListener.recyclerViewListClicked(v, getLayoutPosition());
                }
            });

            //send to book activity the book that the user clicked
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

    //constructor
    public FavoriteBookAdapter(List<Book> bookItems){
        this.bookItems = bookItems;
    }

    public FavoriteBookAdapter(Context context, RecyclerViewClickListener itemListener){
        this.context = context;
        this.recyclerViewClickListener = itemListener;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //inflate view holder
    @Override
    public FavoriteBookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_book, viewGroup, false);
        BookViewHolder bookViewHolder = new BookViewHolder(v);
        return bookViewHolder;
    }

    //bind view holder to adapter
    @Override
    public void onBindViewHolder(@NonNull final FavoriteBookAdapter.BookViewHolder bookViewHolder, int position) {
        Book currentBook = bookItems.get(position);
        BookInformation currentBookInformation = currentBook.getBookInformation();
        Glide.with(bookViewHolder.imageView).load(currentBookInformation.getImageUrl()).into(bookViewHolder.imageView);
        bookViewHolder.author.setText(currentBookInformation.getAuthor().getName());
        bookViewHolder.title.setText(currentBookInformation.getTitle());
        bookViewHolder.ratingBar.setRating((float)currentBook.getAverageRating());

        bookViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(bookViewHolder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }
}
