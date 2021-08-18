package br.com.tiagoalcantara.aluraflix.videos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "videos")
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false)
    private String title;
    @NotBlank @Column(nullable = false)
    private String description;
    @NotBlank @Column(nullable = false)
    private String url;

    @Deprecated
    public Video(){}

    public Video(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void updateFields(String title, String description, String url){
        if(title != null) this.title = title;
        if(description != null) this.description = description;
        if(url != null) this.url = url;
    }
}
