package com.example.novelist.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.novelist.entity.Chara;
import com.example.novelist.entity.Topic;
import com.example.novelist.form.CharaForm;
import com.example.novelist.repository.CharaRepository;

@Controller
public class CharaController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CharaRepository chararepository;

    @Value("${image.local:false}")
    private String imageLocal;
    
    @GetMapping("/chara/{id}")
    public String search(Model model, @PathVariable("id") long id) throws IOException {
    	
    	Optional<Chara> chara = chararepository.findByTopicIdAndRole(id, "主人公");
    	CharaForm charaform = new CharaForm();
    	if (chara.isPresent()) {
    	    charaform = modelMapper.map(chara.get(), CharaForm.class);
    	}
		model.addAttribute("charaform", charaform);
		model.addAttribute("topicid", id);
    	
        return "charas/chara";
    }
 
    @GetMapping(value = "/characreated/{id}")
    public String characreated(Model model, @PathVariable("id") long id) throws IOException {
         
    	Optional<Chara> chara = chararepository.findByTopicIdAndRole(id, "ヒロイン");
    	CharaForm charaform = new CharaForm();
    	if (chara.isPresent()) {
    	    charaform = modelMapper.map(chara.get(), CharaForm.class);
    	}
		model.addAttribute("charaform", charaform);
		model.addAttribute("topicid", id);
    	
        return "charas/character";
    }

    
    @RequestMapping(value = "/chara-content/{id}")
    public String createdetails(@Validated @ModelAttribute("charaform") CharaForm charaform, BindingResult result, @PathVariable("id") Long id, 
    		@PathVariable("id") Topic topic, Model model, RedirectAttributes redirAttrs, Locale locale)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", messageSource.getMessage("topics.create.flash.3", new String[] {}, locale));
            return "topics/edit";
        }

        Chara entity = new Chara();
        if (charaform.getId() != null) {
        	entity =chararepository.findById(charaform.getId()).get();
        }
        
        entity.setTopicId(topic.getId());
        entity.setName(charaform.getName());
        entity.setNickname(charaform.getNickname());
        entity.setRole(charaform.getRole());
        entity.setGendere(charaform.getGendere());
        entity.setAge(charaform.getAge());
        entity.setBirthday(charaform.getBirthday());
        entity.setHeight(charaform.getHeight());
        entity.setWeight(charaform.getWeight());
        entity.setPersonality(charaform.getPersonality());
        entity.setSkill(charaform.getSkill());
        entity.setAbility(charaform.getAbility());
        entity.setAppearance(charaform.getAppearance());
        entity.setUpbringing(charaform.getUpbringing());
        entity.setBackground(charaform.getBackground());
        entity.setOthers(charaform.getOthers());
        
        chararepository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", messageSource.getMessage("topics.create.flash.4", new String[] {}, locale));

        return "redirect:/chara/{id}";
    }

}
