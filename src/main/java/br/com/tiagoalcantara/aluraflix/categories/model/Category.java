package br.com.tiagoalcantara.aluraflix.categories.model;

import br.com.tiagoalcantara.aluraflix.shared.validators.HexColor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank @HexColor
    @Column(nullable = false)
    private String color;

//    @OneToMany(mappedBy = "category")
//    List<Video> videos;

    @Deprecated
    public Category(){}

    public Category(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
