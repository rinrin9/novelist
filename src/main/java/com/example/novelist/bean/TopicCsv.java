package com.example.novelist.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonPropertyOrder({ "ID", "ユーザーID", "タイトル", "本文"})
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TopicCsv {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("ユーザーID")
    private Long userId;

    @JsonProperty("タイトル")
    private String title;

    @JsonProperty("本文")
    private String description;

}