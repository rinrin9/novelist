package com.example.novelist.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.example.novelist.form.TopicUpdateForm;
import com.example.novelist.form.UserForm;
import com.example.novelist.repository.TopicRepository;
import com.example.novelist.entity.Favorite;
import com.example.novelist.form.FavoriteForm;
import com.example.novelist.entity.Comment;
import com.example.novelist.form.CommentForm;
import com.example.novelist.bean.TopicCsv;

@Controller
public class TopicsController {
	
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

    @GetMapping(path = "/topics")
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

        return "topics/index";
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

    @RequestMapping(value = "/topics/{id}")
    public String show(@PathVariable("id") long id, Principal principal, Model model) throws IOException {
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();

        Optional<Topic> topic = repository.findById(id);
        TopicForm form = getTopic(user, topic.get());
        model.addAttribute("topic", form);

        return "topics/show";
    }
    
    @GetMapping(path = "/topics/top")
    public String topTopic(Principal principal, Model model) throws IOException {
    	Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        
        List<Topic> topics = repository.findByUserId(user.getUserId());
        List<TopicForm> list = new ArrayList<>();
        for (Topic entity : topics) {
            TopicForm form = getTopic(user, entity);
            list.add(form);
        }
        model.addAttribute("list", list);

        
        return "topics/home";
    }


    
    @GetMapping(path = "/topics/new")
    public String newTopic(Model model) {
        model.addAttribute("form", new TopicForm());
        
        return "topics/new";
    }

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public String create(Principal principal, @Validated @ModelAttribute("form") TopicForm form, BindingResult result,
    		Model model, @RequestParam MultipartFile image, RedirectAttributes redirAttrs, Locale locale)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", messageSource.getMessage("topics.create.flash.1", new String[] {}, locale));
            return "topics/new";
        }

        boolean isImageLocal = false;
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }

        Topic entity = new Topic();
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        entity.setUserId(user.getUserId());
        File destFile = null;
        if (isImageLocal) {
            destFile = saveImageLocal(image, entity);
            entity.setPath(destFile.getAbsolutePath());
        } else {
            entity.setPath("");
        }
        entity.setDescription(form.getDescription());
        entity.setTitle(form.getTitle());
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", messageSource.getMessage("topics.create.flash.2", new String[] {}, locale));

        return "redirect:/topics";
    }

    private File saveImageLocal(MultipartFile image, Topic entity) throws IOException {
        File uploadDir = new File("/uploads");
        uploadDir.mkdir();

        String uploadsDir = "/uploads/";
        String realPathToUploads = request.getServletContext().getRealPath(uploadsDir);
        if (!new File(realPathToUploads).exists()) {
            new File(realPathToUploads).mkdir();
        }
        String fileName = image.getOriginalFilename();
        File destFile = new File(realPathToUploads, fileName);
        image.transferTo(destFile);

        return destFile;
    }
    
    @RequestMapping(value = "/topics/edit/{id}")
    public String edit(@PathVariable("id") long id, Principal principal, Model model) throws IOException {
    	
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();

        Optional<Topic> topic = repository.findById(id);
        TopicForm form = getTopic(user, topic.get());
        model.addAttribute("form", form);


        return "topics/edit";
    }
    
    @RequestMapping(value = "/topics/edit", method = RequestMethod.POST)
    public String update(Principal principal, @Validated @ModelAttribute("form") TopicUpdateForm form, BindingResult result,
    		Model model, @RequestParam(name="image", required = false) MultipartFile image, RedirectAttributes redirAttrs, Locale locale)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", messageSource.getMessage("topics.create.flash.1", new String[] {}, locale));
            return "topics/edit";
        }

        boolean isImageLocal = false;
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }

        Topic entity =repository.findById(form.getId()).get();
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        entity.setUserId(user.getUserId());
        
        if (!image.isEmpty()) {
        	File destFile = null;
        	if (isImageLocal) {
        		destFile = saveImageLocal(image, entity);
        		entity.setPath(destFile.getAbsolutePath());
        	} else {
        		entity.setPath("");
        	}
        }
        entity.setDescription(form.getDescription());
        entity.setTitle(form.getTitle());
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", messageSource.getMessage("topics.create.flash.2", new String[] {}, locale));

        return "redirect:/topics/top";
    }

    @RequestMapping(value = "/topics/delete/{id}")
    public String delete(@PathVariable("id") long id, Principal principal, Model model, RedirectAttributes redirAttrs) throws IOException {

        repository.deleteById(id);
        //お気に入り削除
        //コメント削除
        
        return "redirect:/topics/top";
    }
    
    @RequestMapping(value = "/topics/topic.csv", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
            + "; charset=UTF-8; Content-Disposition: attachment")
    @ResponseBody
    public Object downloadCsv(@PathVariable("id") long id) throws IOException {
        Iterable<Topic> topics = repository.findAll();
        //Optional<Topic> topics = repository.findById(id);
        Type listType = new TypeToken<List<TopicCsv>>() {
        }.getType();
        List<TopicCsv> csv = modelMapper.map(topics, listType);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(TopicCsv.class).withHeader();

        return mapper.writer(schema).writeValueAsString(csv);
    }
}