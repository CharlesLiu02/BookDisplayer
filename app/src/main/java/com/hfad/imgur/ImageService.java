package com.hfad.imgur;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageService {
    @GET("/api/")
    Call<ImageResponse> searchByKeyword(@Query("image_type") String imageType,
                                        @Query("q") String searchTerm);
}
