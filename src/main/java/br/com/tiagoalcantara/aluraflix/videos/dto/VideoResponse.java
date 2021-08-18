package br.com.tiagoalcantara.aluraflix.videos.dto;

import br.com.tiagoalcantara.aluraflix.videos.model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class VideoResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final String url;
    private final Long idCategory;

    public VideoResponse(Video video){
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.url = video.getUrl();
        this.idCategory = video.getCategory().getId();
    }

    public Long getId() {
        return id;
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

    public Long getIdCategory() {
        return idCategory;
    }

    public static List<VideoResponse> convertVideoListToVideoResponseList(List<Video> videoList){
        return videoList.stream().map(VideoResponse::new).collect(Collectors.toList());
    }
}
