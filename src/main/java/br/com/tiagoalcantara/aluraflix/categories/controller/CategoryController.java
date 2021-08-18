package br.com.tiagoalcantara.aluraflix.categories.controller;

import br.com.tiagoalcantara.aluraflix.categories.dto.CategoryResponse;
import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> response = CategoryResponse.convertCategoryListToCategoryResponseList(categoryList);

        return ResponseEntity.ok(response);
    }
}
