package com.hfad.book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    //telling retrofit to get data from the server
    //relative url that adds to base url
    @GET("/search.xml?")
    //returns call object
    //encapsulates single call and response
    //use Call to execute call get request
    Call<BookResponse> searchByKeyword(@Query("key") String key,
                                        @Query("q") String searchTerm);

}
