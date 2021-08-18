package br.com.tiagoalcantara.aluraflix.videos.dto;

import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class CreateVideoRequest {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    @NotBlank @URL
    private final String url;

    public CreateVideoRequest(String title, String description, String url) {
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

    public Video toModel() {
        return new Video(this.title, this.description, this.url);
    }
}
