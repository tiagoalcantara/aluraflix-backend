package br.com.tiagoalcantara.aluraflix.videos.repository;

import br.com.tiagoalcantara.aluraflix.videos.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByCategoryId(Long categoryId);
    List<Video> findAllByTitleContaining(String title);
}
