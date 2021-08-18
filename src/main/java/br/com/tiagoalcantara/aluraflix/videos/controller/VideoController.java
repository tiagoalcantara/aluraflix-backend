package br.com.tiagoalcantara.aluraflix.videos.controller;

import br.com.tiagoalcantara.aluraflix.categories.repository.CategoryRepository;
import br.com.tiagoalcantara.aluraflix.videos.dto.CreateVideoRequest;
import br.com.tiagoalcantara.aluraflix.videos.dto.UpdateVideoRequest;
import br.com.tiagoalcantara.aluraflix.videos.dto.VideoResponse;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import br.com.tiagoalcantara.aluraflix.videos.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/videos")
public class VideoController {

    private final VideoRepository videoRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository, CategoryRepository categoryRepository){
        this.videoRepository = videoRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<VideoResponse>> list(@RequestParam(name = "search", defaultValue = "") String title){
        List<Video> videoList = videoRepository.findAllByTitleContaining(title);
        List<VideoResponse> response = VideoResponse.convertVideoListToVideoResponseList(videoList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> find(@PathVariable Long id){
        Video video = findByIdOrThrowResponseStatusException(id);
        VideoResponse response = new VideoResponse(video);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VideoResponse> create(@Valid @RequestBody CreateVideoRequest request, UriComponentsBuilder uriBuilder){
        Video video = request.toModel(categoryRepository);
        videoRepository.save(video);

        URI createdUri = uriBuilder.path("/videos/{id}").buildAndExpand(video.getId()).toUri();
        VideoResponse response = new VideoResponse(video);

        return ResponseEntity.created(createdUri).body(response);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<VideoResponse> update(@Valid @RequestBody UpdateVideoRequest request, @PathVariable Long id){
        Video video = findByIdOrThrowResponseStatusException(id);

        request.applyUpdate(video, categoryRepository);
        VideoResponse response = new VideoResponse(video);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Video video = findByIdOrThrowResponseStatusException(id);
        videoRepository.delete(video);

        return ResponseEntity.ok().build();
    }

    private Video findByIdOrThrowResponseStatusException(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vídeo não encontrado"));
    }
}
