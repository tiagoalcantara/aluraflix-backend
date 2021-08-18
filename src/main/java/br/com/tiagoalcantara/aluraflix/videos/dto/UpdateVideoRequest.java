package br.com.tiagoalcantara.aluraflix.videos.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Size;

public class UpdateVideoRequest {
    @Size(min = 1)
    private final String title;
    @Size(min = 1)
    private final String description;
    @URL @Size(min = 1)
    private final String url;

    public UpdateVideoRequest(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
