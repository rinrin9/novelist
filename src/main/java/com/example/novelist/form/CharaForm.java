package com.example.novelist.form;

import javax.validation.constraints.Size;

import com.example.novelist.entity.Topic;
import lombok.Data;

@Data
public class CharaForm {
	
    private Long id;
    
    private Long topicId;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String nickname;
    
    @Size(max = 50)
    private String role;
    
    @Size(max = 50)
    private String gendere;
    
    @Size(max = 50)
    private String age;
    
    @Size(max = 50)
    private String birthday;
    
    @Size(max = 50)
    private String height;
    
    @Size(max = 50)
    private String weight;
     
    @Size(max = 50)
    private String personality;

    @Size(max = 100)
    private String skill;
    
    @Size(max = 100)
    private String ability;
    
    @Size(max = 100)
    private String Appearance;
    
    @Size(max = 100)
    private String upbringing;
    
    @Size(max = 100)
    private String background;
    
    @Size(max = 200)
    private String others;
    
    private Topic topic;
}
