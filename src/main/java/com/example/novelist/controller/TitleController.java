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
import java.util.Locale;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ResponseBody;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import com.example.novelist.entity.Topic;
import com.example.novelist.entity.UserInf;
import com.example.novelist.form.TopicForm;
import com.example.novelist.form.UserForm;
import com.example.novelist.repository.TopicRepository;
import com.example.novelist.entity.Favorite;
import com.example.novelist.form.FavoriteForm;
import com.example.novelist.entity.Comment;
import com.example.novelist.form.CommentForm;
import com.example.novelist.bean.TopicCsv;

@Controller
public class TitleController {
	
	@Autowired
	private MessageSource messageSource;

    protected static Logger log = LoggerFactory.getLogger(TopicsController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TopicRepository repository;

    @Autowired
    private HttpServletRequest request;

    @Value("${image.local:false}")
    private String imageLocal;

    @GetMapping(path = "/title")
    public String index(Principal principal, Model model) throws IOException {
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();

        Iterable<Topic> topics = repository.findAllByOrderByUpdatedAtDesc();
        List<TopicForm> list = new ArrayList<>();
        for (Topic entity : topics) {
            TopicForm form = getTopic(user, entity);
            list.add(form);
        }
        model.addAttribute("list", list);

        return "topics/titleindex";
    }

    public TopicForm getTopic(UserInf user, Topic entity) throws FileNotFoundException, IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Topic.class, TopicForm.class).addMappings(mapper -> mapper.skip(TopicForm::setUser));
        modelMapper.typeMap(Topic.class, TopicForm.class).addMappings(mapper -> mapper.skip(TopicForm::setFavorites));
        modelMapper.typeMap(Topic.class, TopicForm.class).addMappings(mapper -> mapper.skip(TopicForm::setComments));
        modelMapper.typeMap(Favorite.class, FavoriteForm.class).addMappings(mapper -> mapper.skip(FavoriteForm::setTopic));
        
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
        
        List<FavoriteForm> favorites = new ArrayList<FavoriteForm>();
            for (Favorite favoriteEntity : entity.getFavorites()) {
                FavoriteForm favorite = modelMapper.map(favoriteEntity, FavoriteForm.class);
                favorites.add(favorite);
                if (user.getUserId().equals(favoriteEntity.getUserId())) {
                    form.setFavorite(favorite);
                }
            }
            form.setFavorites(favorites);
            
            List<CommentForm> comments = new ArrayList<CommentForm>();
            
                for (Comment commentEntity : entity.getComments()) {
                    CommentForm comment = modelMapper.map(commentEntity, CommentForm.class);
                    comments.add(comment);
                }
                form.setComments(comments);

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