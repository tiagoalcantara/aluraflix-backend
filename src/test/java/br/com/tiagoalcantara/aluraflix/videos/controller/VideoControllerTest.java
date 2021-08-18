package br.com.tiagoalcantara.aluraflix.videos.controller;

import br.com.tiagoalcantara.aluraflix.builders.VideoBuilder;
import br.com.tiagoalcantara.aluraflix.videos.dto.CreateVideoRequest;
import br.com.tiagoalcantara.aluraflix.videos.dto.UpdateVideoRequest;
import br.com.tiagoalcantara.aluraflix.videos.dto.VideoResponse;
import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import br.com.tiagoalcantara.aluraflix.videos.repository.VideoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VideoRepository videoRepository;

    @BeforeEach
    void setUp() {
        videoRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToListAllVideos() throws Exception {
        VideoBuilder videoBuilder = new VideoBuilder();
        List<Video> videos = List.of(
                videoBuilder.build(),
                videoBuilder.build(),
                videoBuilder.build()
        );

        videoRepository.saveAll(videos);

        MvcResult mvcResult = mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(VideoResponse.convertVideoListToVideoResponseList(videos));

        assertEquals(expected, response);
    }

    @Test
    void shouldBeAbleToFindVideoById() throws Exception {
        VideoBuilder videoBuilder = new VideoBuilder();
        Video video = videoBuilder.build();
        videoRepository.save(video);

        MvcResult mvcResult = mockMvc.perform(get("/videos/{id}", video.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(new VideoResponse(video));

        assertEquals(expected, response);
    }

    @Test
    void shouldReturnStatusNotFoundWhenUnableToFindVideoById() throws Exception {
        mockMvc.perform(get("/videos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAbleToCreateNewVideo() throws Exception {
        CreateVideoRequest createVideoRequest = new CreateVideoRequest("Video", "Description", "http://test.com", 1L);

        MvcResult mvcResult = mockMvc.perform(post("/videos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createVideoRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String responseAsJson = mvcResult.getResponse().getContentAsString();
        Video response = objectMapper.readValue(responseAsJson, Video.class);
        Video expected = videoRepository.findById(response.getId()).orElse(null);

        assertNotNull(expected);
    }

    @Test
    void shouldReturnBadRequestWhenCreateVideoRequestIsInvalid() throws Exception {
        CreateVideoRequest createVideoRequest = new CreateVideoRequest("", null, "aa", 1L);

        Integer expectedErrorQuantity = 3;

        mockMvc.perform(post("/videos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createVideoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.count", Matchers.equalTo(expectedErrorQuantity)));
    }

    @Test
    void shouldBeAbleToUpdateVideo() throws Exception{
        VideoBuilder videoBuilder = new VideoBuilder();
        Video video = videoBuilder.build();
        UpdateVideoRequest updateVideoRequest = new UpdateVideoRequest("New Title", "New Description", "http://newurl.com", 1L);
        videoRepository.save(video);

        MvcResult mvcResult = mockMvc.perform(put("/videos/{id}", video.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateVideoRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Video response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Video.class);

        assertAll(
                () -> assertEquals(updateVideoRequest.getTitle(), response.getTitle()),
                () -> assertEquals(updateVideoRequest.getDescription(), response.getDescription()),
                () -> assertEquals(updateVideoRequest.getUrl(), response.getUrl())
        );
    }

    @Test
    void shouldReturnNotFoundWhenUpdateHasNonexistentId() throws Exception {
        mockMvc.perform(put("/videos/1")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenUpdateVideoRequestIsInvalid() throws Exception{
        VideoBuilder videoBuilder = new VideoBuilder();
        Video video = videoBuilder.build();
        videoRepository.save(video);

        UpdateVideoRequest updateVideoRequest = new UpdateVideoRequest("", "", "aa", null);
        Integer expectedErrorQuantity = 3;

        mockMvc.perform(put("/videos/{id}", video.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateVideoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.count", Matchers.equalTo(expectedErrorQuantity)));
    }

    @Test
    void shouldBeAbleToDeleteVideo() throws Exception{
        VideoBuilder videoBuilder = new VideoBuilder();
        Video video = videoBuilder.build();
        videoRepository.save(video);

        mockMvc.perform(delete("/videos/{id}", video.getId()))
                .andExpect(status().isOk());

        Optional<Video> optionalVideo = videoRepository.findById(video.getId());
        assertTrue(optionalVideo.isEmpty());
    }

    @Test
    void shouldReturnNotFoundWhenDeleteWithNonexistentId() throws Exception {
        mockMvc.perform(delete("/videos/1"))
                .andExpect(status().isNotFound());
    }
}