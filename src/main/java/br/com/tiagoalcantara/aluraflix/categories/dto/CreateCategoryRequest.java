package br.com.tiagoalcantara.aluraflix.categories.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateCategoryRequest {
    @NotBlank
    private final String title;
    @NotBlank
    @Pattern(regexp = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$")
    private final String color;

    public CreateCategoryRequest(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public Category toModel(){
        return new Category(this.title, this.color);
    }
}
