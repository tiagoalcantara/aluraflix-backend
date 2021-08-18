package br.com.tiagoalcantara.aluraflix.videos.model;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @Deprecated
    public Video(){}

    public Video(String title, String description, String url, Category category) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
