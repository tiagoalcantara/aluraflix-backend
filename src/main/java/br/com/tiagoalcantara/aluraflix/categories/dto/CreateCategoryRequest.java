package br.com.tiagoalcantara.aluraflix.categories.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.shared.validators.annotations.HexColor;

import javax.validation.constraints.NotBlank;

public class CreateCategoryRequest {
    @NotBlank
    private final String title;
    @NotBlank @HexColor
    private final String color;

    public CreateCategoryRequest(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public Category toModel(){
        return new Category(this.title, this.color);
    }
}
