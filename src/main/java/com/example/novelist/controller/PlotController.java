package com.example.novelist.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.example.novelist.entity.Plot;
import com.example.novelist.entity.Topic;
import com.example.novelist.form.PlotForm;
import com.example.novelist.repository.PlotRepository;

@Controller
public class PlotController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PlotRepository plotrepository;

    @GetMapping("/plot/{id}")
    public String search(Model model, @PathVariable("id") long id) throws IOException {
    	
    	PlotForm plotform = new PlotForm();
    	Optional<Plot> plot = plotrepository.findByTopicId(id);
    	if (!plot.isEmpty()) {
    		plotform = modelMapper.map(plot.get(), PlotForm.class);
        }
        model.addAttribute("plotform", plotform);
        model.addAttribute("topicid", id);
    	
        return "plots/index";
    }
    
    @GetMapping("/plotpick/setting/{id}")
    public String setting(Model model, @PathVariable("id") long id) throws IOException {
    	
    	PlotForm plotform = new PlotForm();
    	Optional<Plot> plot = plotrepository.findByTopicId(id);
    	if (!plot.isEmpty()) {
    		plotform = modelMapper.map(plot.get(), PlotForm.class);
        }
        model.addAttribute("plotform", plotform);
        model.addAttribute("setting", plotform.getSetting());
        model.addAttribute("topicid", id);
    	
        return "plots/setting";
    }
    
    @GetMapping("/plotpick/confrontation/{id}")
    public String confrontation(Model model, @PathVariable("id") long id) throws IOException {
    	
    	PlotForm plotform = new PlotForm();
    	Optional<Plot> plot = plotrepository.findByTopicId(id);
    	if (!plot.isEmpty()) {
    		plotform = modelMapper.map(plot.get(), PlotForm.class);
        }
        model.addAttribute("plotform", plotform);
        model.addAttribute("setting", plotform.getConfrontation());
        model.addAttribute("topicid", id);
    	
        return "plots/confrontation";
    }
    
    @GetMapping("/plotpick/Resolution/{id}")
    public String Resolution(Model model, @PathVariable("id") long id) throws IOException {
    	
    	PlotForm plotform = new PlotForm();
    	Optional<Plot> plot = plotrepository.findByTopicId(id);
    	if (!plot.isEmpty()) {
    		plotform = modelMapper.map(plot.get(), PlotForm.class);
        }
        model.addAttribute("plotform", plotform);
        model.addAttribute("setting", plotform.getResolution());
        model.addAttribute("topicid", id);
    	
        return "plots/resolution";
    }
    @GetMapping("/plotpick/memo/{id}")
    public String memo(Model model, @PathVariable("id") long id) throws IOException {
    	
    	PlotForm plotform = new PlotForm();
    	Optional<Plot> plot = plotrepository.findByTopicId(id);
    	if (!plot.isEmpty()) {
    		plotform = modelMapper.map(plot.get(), PlotForm.class);
        }
        model.addAttribute("plotform", plotform);
        model.addAttribute("setting", plotform.getMemo());
        model.addAttribute("topicid", id);
    	
        return "plots/memo";
    }
    
    @GetMapping(value = "/plotpick/{plotid}")
    public String plotpick(Model model, @PathVariable("plotid") long plotid) throws IOException {
         
    	Optional<Plot> plot = plotrepository.findById(plotid);
    	PlotForm plotform = modelMapper.map(plot.get(), PlotForm.class);
    	
		model.addAttribute("plotform", plotform);
		model.addAttribute("topicid", plotform.getTopic().getId());
		
        return "plots/index";
    }
    
    @RequestMapping(value = "/plot-content/{id}")
    public String createdetails(@Validated @ModelAttribute("plotform") PlotForm plotform, BindingResult result, @PathVariable("id") Long id, 
    		@PathVariable("id") Topic topic, Model model, RedirectAttributes redirAttrs, Locale locale, UriComponentsBuilder builder)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", messageSource.getMessage("topics.create.flash.3", new String[] {}, locale));
            return "topics/edit";
        }

        Plot entity = new Plot();
        if (plotform.getId() != null) {
        	entity =plotrepository.findById(plotform.getId()).get();
        }

        entity.setTopicId(topic.getId());
        if (plotform.getSetting() != null) {
        	entity.setSetting(plotform.getSetting());
        }
        if (plotform.getConfrontation() != null) {
        	entity.setConfrontation(plotform.getConfrontation());
        }
        if (plotform.getResolution() != null) {
        	entity.setResolution(plotform.getResolution());
        }
        if (plotform.getMemo() != null) {
        	entity.setMemo(plotform.getMemo());
        }
        
        plotrepository.saveAndFlush(entity);
        
        URI location = builder.path("/plotpick/" + entity.getId()).build().toUri();

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", messageSource.getMessage("topics.create.flash.4", new String[] {}, locale));

        return "redirect:" + location.toString();
    }
}
