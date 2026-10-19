package com.example.english_vocab.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "words")

public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String enWord;
    private String jpMeaning;

    @Column(columnDefinition = "TEXT")
    private String exampleSentence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getJpMeaning() {
        return jpMeaning;
    }

    public void setJpMeaning(String jpMeaning) {
        this.jpMeaning = jpMeaning;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }
}