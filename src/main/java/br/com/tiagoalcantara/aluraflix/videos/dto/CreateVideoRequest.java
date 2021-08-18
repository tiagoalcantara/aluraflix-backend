package br.com.tiagoalcantara.aluraflix.videos.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import br.com.tiagoalcantara.aluraflix.shared.validators.annotations.ExistingId;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateVideoRequest {
    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    @NotBlank @URL
    private final String url;
    @NotNull @Positive @ExistingId(domainClass = Category.class)
    private final Long idCategory;

    public CreateVideoRequest(String title, String description, String url, Long idCategory) {
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

    public Video toModel(CategoryRepository categoryRepository) {
        Category category = categoryRepository.getById(this.idCategory);
        return new Video(this.title, this.description, this.url, category);
    }
}
