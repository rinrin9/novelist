package com.example.novelist.form;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.novelist.validation.constraints.ImageByte;
import com.example.novelist.validation.constraints.ImageNotEmpty;

import lombok.Data;

@Data
public class TopicForm {

    private Long id;

    private Long userId;

    @ImageNotEmpty
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
    
    private boolean keep = false;

    private UserForm user;
    
    private List<FavoriteForm> favorites;
    
    private FavoriteForm favorite;
    
    private List<CommentForm> comments;
    
    private Date updatedAt;

}