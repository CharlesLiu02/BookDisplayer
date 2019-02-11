package com.hfad.imgur;

import java.util.List;

public class ImageResponse {
    private int totalHits;
    private List<Image> images;

    public ImageResponse() {
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
