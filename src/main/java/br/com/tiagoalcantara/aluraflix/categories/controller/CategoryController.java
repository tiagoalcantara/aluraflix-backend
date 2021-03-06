package br.com.tiagoalcantara.aluraflix.categories.controller;

import br.com.tiagoalcantara.aluraflix.categories.dto.CategoryResponse;
import br.com.tiagoalcantara.aluraflix.categories.dto.CreateCategoryRequest;
import br.com.tiagoalcantara.aluraflix.categories.dto.UpdateCategoryRequest;
import br.com.tiagoalcantara.aluraflix.categories.dto.VideosByCategoryResponse;
import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import br.com.tiagoalcantara.aluraflix.videos.repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    private final VideoRepository videoRepository;
    private final CategoryRepository categoryRepository;

    public CategoryController(VideoRepository videoRepository, CategoryRepository categoryRepository) {
        this.videoRepository = videoRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> response = CategoryResponse.convertCategoryListToCategoryResponseList(categoryList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> find(@PathVariable @Positive Long id){
        Category category = findByIdOrThrowResponseStatusException(id);

        CategoryResponse response = new CategoryResponse(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<VideosByCategoryResponse> listVideos(@PathVariable @Positive Long id){
        Category category = findByIdOrThrowResponseStatusException(id);
        List<Video> videos = videoRepository.findAllByCategoryId(category.getId());

        VideosByCategoryResponse response = new VideosByCategoryResponse(videos, category);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request, UriComponentsBuilder uriBuilder){
        Category category = request.toModel();
        categoryRepository.save(category);

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(category.getId()).toUri();
        CategoryResponse response = new CategoryResponse(category);

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CategoryResponse> update(@Valid @RequestBody UpdateCategoryRequest request, @PathVariable @Positive Long id){
        Category category = findByIdOrThrowResponseStatusException(id);

        request.applyUpdate(category);
        CategoryResponse response = new CategoryResponse(category);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete (@PathVariable @Positive Long id) {
        Category category = findByIdOrThrowResponseStatusException(id);
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }

    private Category findByIdOrThrowResponseStatusException(Long id){
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoria n??o encontrada"));
    }
}
