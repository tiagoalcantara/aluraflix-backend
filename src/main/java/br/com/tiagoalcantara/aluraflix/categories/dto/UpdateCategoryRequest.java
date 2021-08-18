package br.com.tiagoalcantara.aluraflix.categories.dto;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.shared.validators.HexColor;

import javax.validation.constraints.Size;

public class UpdateCategoryRequest {
    @Size(min = 1)
    private final String title;
    @Size(min = 1) @HexColor
    private final String color;

    public UpdateCategoryRequest(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public void applyUpdate(Category category){
        if(this.title != null) category.setTitle(this.title);
        if(this.color != null) category.setColor(this.color);
    }
}
