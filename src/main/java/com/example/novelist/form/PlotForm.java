package com.example.novelist.form;

import javax.validation.constraints.Size;

import com.example.novelist.entity.Topic;

import lombok.Data;

@Data
public class PlotForm {

	private Long id;
	
    private Long topicId;

	@Size(max = 300)
    private String setting;

	@Size(max = 300)
    private String confrontation;

	@Size(max = 300)
    private String Resolution;

	@Size(max = 300)
    private String memo;
    
    private Topic topic;

}