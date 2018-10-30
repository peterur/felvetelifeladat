package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "articles")
public class Article extends AuditModel {
    @NotNull
	@Id
    @GeneratedValue(generator = "article_generator")
    @SequenceGenerator(
            name = "article_generator",
            sequenceName = "article_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotNull
    @Size(min = 0, max = 255)
    private String title;
    
    
    @Column(columnDefinition = "text")
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
