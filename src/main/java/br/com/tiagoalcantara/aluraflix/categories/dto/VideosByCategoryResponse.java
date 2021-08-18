package br.com.tiagoalcantara.aluraflix.categories.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.videos.dto.VideoResponse;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;

import java.util.List;

public class VideosByCategoryResponse {
    private final CategoryResponse category;
    private final List<VideoResponse> videos;

    public VideosByCategoryResponse(List<Video> videosEntityList, Category categoryEntity){
        this.category = new CategoryResponse(categoryEntity);
        this.videos = VideoResponse.convertVideoListToVideoResponseList(videosEntityList);
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public List<VideoResponse> getVideos() {
        return videos;
    }
}
