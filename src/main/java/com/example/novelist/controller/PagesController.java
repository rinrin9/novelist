package com.example.novelist.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.novelist.entity.Comment;
import com.example.novelist.entity.Favorite;
import com.example.novelist.entity.Topic;
import com.example.novelist.entity.UserInf;
import com.example.novelist.form.CommentForm;
import com.example.novelist.form.FavoriteForm;
import com.example.novelist.form.TopicForm;
import com.example.novelist.form.UserForm;
import com.example.novelist.repository.TopicRepository;

@Controller
public class PagesController {
	
	@Autowired
    private TopicRepository repository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Value("${image.local:false}")
    private String imageLocal;

    @RequestMapping("/")
    public String index(Model model) throws IOException {

        Iterable<Topic> topics = repository.findTop3ByOrderByUpdatedAtDesc();
        List<TopicForm> list = new ArrayList<>();
        for (Topic entity : topics) {
            TopicForm form = getTopic(entity);
            list.add(form);
        }
        model.addAttribute("list", list);
    	
        return "pages/index";
    }
    
    public TopicForm getTopic(Topic entity) throws FileNotFoundException, IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Topic.class, TopicForm.class).addMappings(mapper -> mapper.skip(TopicForm::setUser));
        
        boolean isImageLocal = false;
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }
        TopicForm form = modelMapper.map(entity, TopicForm.class);

        if (isImageLocal) {
            try (InputStream is = new FileInputStream(new File(entity.getPath()));
                    ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                byte[] indata = new byte[10240 * 16];
                int size;
                while ((size = is.read(indata, 0, indata.length)) > 0) {
                    os.write(indata, 0, size);
                }
                StringBuilder data = new StringBuilder();
                data.append("data:");
                data.append(getMimeType(entity.getPath()));
                data.append(";base64,");

                data.append(new String(Base64Utils.encode(os.toByteArray()), "ASCII"));
                form.setImageData(data.toString());
            }
        }

        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
        form.setUser(userForm);
        
        return form;
    }
    
    private String getMimeType(String path) {
        String extension = FilenameUtils.getExtension(path);
        String mimeType = "image/";
        switch (extension) {
        case "jpg":
        case "jpeg":
            mimeType += "jpeg";
            break;
        case "png":
            mimeType += "png";
            break;
        case "gif":
            mimeType += "gif";
            break;
        }
        return mimeType;
    }
}