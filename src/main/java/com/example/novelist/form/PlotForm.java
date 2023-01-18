package com.example.novelist.form;

import lombok.Data;

@Data
public class PlotForm {

	private Long id;
	
	private String title;

    private String first;

    private String second;

    private String third;

    private String fourth;
    
    private String firstshow;

    private String secondshow;

    private String thirdshow;

    private String fourthshow;

    private UserForm user;

}