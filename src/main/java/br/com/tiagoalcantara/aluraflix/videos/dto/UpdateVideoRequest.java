package br.com.tiagoalcantara.aluraflix.videos.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import br.com.tiagoalcantara.aluraflix.shared.validators.annotations.ExistingId;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UpdateVideoRequest {
    @Size(min = 1)
    private final String title;
    @Size(min = 1)
    private final String description;
    @URL @Size(min = 1)
    private final String url;
    @Positive @ExistingId(domainClass = Category.class)
    private final Long idCategory;

    public UpdateVideoRequest(String title, String description, String url, Long idCategory) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.idCategory = idCategory;
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

    public void applyUpdate(Video video, CategoryRepository categoryRepository){
        if(this.title != null) video.setTitle(this.title);
        if(this.description != null) video.setDescription(this.description);
        if(this.url != null) video.setUrl(this.url);
        if(this.idCategory != null) {
            Category category = categoryRepository.getById(this.idCategory);
            video.setCategory(category);
        }
    }
}
