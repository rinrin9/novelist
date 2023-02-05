package com.example.novelist.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.novelist.validation.constraints.ImageByte;
import com.example.novelist.validation.constraints.ImageNotEmpty;

import lombok.Data;

@Data
public class TopicUpdateForm {

    private Long id;

    private Long userId;

    @ImageByte(max = 2000000)
    private MultipartFile image;

    private String imageData;

    private String path;
    
    @NotEmpty
    @Size(max = 50)
    private String title;

    @NotEmpty
    @Size(max = 25000)
    private String description;
    
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

    private UserForm user;
    
    private List<FavoriteForm> favorites;
    
    private FavoriteForm favorite;
    
    private List<CommentForm> comments;

}