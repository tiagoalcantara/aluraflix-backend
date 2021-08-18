package br.com.tiagoalcantara.aluraflix.categories.controller;

import br.com.tiagoalcantara.aluraflix.categories.dto.CategoryResponse;
import br.com.tiagoalcantara.aluraflix.categories.dto.CreateCategoryRequest;
import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> find(@PathVariable Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria não encontrada"));

        CategoryResponse response = new CategoryResponse(category);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request, UriComponentsBuilder uriBuilder){
        Category category = request.toModel();
        categoryRepository.save(category);

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(category.getId()).toUri();
        CategoryResponse response = new CategoryResponse(category);

        return ResponseEntity.ok(response);
    }
}
