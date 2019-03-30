package com.hfad.book;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hfad.imgur.R;

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

public class FavoriteBooksFragment extends Fragment implements RecyclerViewClickListener {
    private List<Book> favoriteBooks;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoriteBookAdapter bookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_books, container, false);

        //initialize favoriteBooks
        readFile();

        recyclerView = rootView.findViewById(R.id.recycler_view_favorite_books);

        layoutManager = new LinearLayoutManager(getActivity());
        bookAdapter = new FavoriteBookAdapter(favoriteBooks);

        //set layout manager before adapter
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bookAdapter);

        registerForContextMenu(recyclerView);

        return rootView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                deleteBook(bookAdapter.getPosition());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteBook(int position) {
        //remove book from list
        //then write list to file
        favoriteBooks.remove(position);
        writeToFile(favoriteBooks);
        bookAdapter.notifyDataSetChanged();
    }

    private void readFile() {
        try {
            FileInputStream fis = getContext().openFileInput("favoriteBooks.txt");
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
            favoriteBooks = gson.fromJson(json, new TypeToken<ArrayList<Book>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(List<Book> books) {
        // check if the file already exists...if it doesn't:
        String fileName = "favoriteBooks.txt";
        File file = new File(getActivity().getFilesDir(), fileName);
        Gson gson = new Gson();

        String s = gson.toJson(books);

        FileOutputStream outputStream;

        try {
            outputStream = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        bookAdapter = new FavoriteBookAdapter(getContext(), this);
    }

}
