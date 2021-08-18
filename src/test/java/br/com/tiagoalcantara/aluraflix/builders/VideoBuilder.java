package br.com.tiagoalcantara.aluraflix.builders;

import br.com.tiagoalcantara.aluraflix.videos.model.Video;

public class VideoBuilder {
    private String title;
    private String description;
    private String url;

    public VideoBuilder(){
        this.title = "Default title";
        this.description = "Default description";
        this.url = "http://default.url.com.br/";
    }

    public void title(String title) {
        this.title = title;
    }

    public void description(String description) {
        this.description = description;
    }

    public void url(String url) {
        this.url = url;
    }

    public Video build(){
        return new Video(this.title, this.description, this.url);
    }
}
