package br.com.tiagoalcantara.aluraflix.categories.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryResponse {
    private final Long id;
    private final String title;
    private final String color;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.color = category.getColor();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public static List<CategoryResponse> convertCategoryListToCategoryResponseList(List<Category> categoryList){
        return categoryList.stream().map(CategoryResponse::new).collect(Collectors.toList());
    }
}
