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
        }else {
        	Plot entity = new Plot();
        	entity.setTopicId(id);
        	entity.setSetting("『例』\n物語の主題の提示\n・兄が妹を助ける話\n\n世界観の設定\n・人食い鬼が存在する世界でその鬼を退治する存在もいる。\n・鬼は首を切るか日の光を浴びないと死なない\n・舞台は日本の大正時代\n\n主人公の設定\n・大家族の長男で父親代わりの存在で弟や妹の面倒をみる\n\n物語の目標を明示\n・主人公の家族が鬼に殺され、妹が鬼にされてしまい、妹を人間に戻すために鬼を退治する組織に入る。\n\n主人公と関係性が高くなる人物との出会い\n・個性的な同期との出会い、以後行動を共にする事が多くなる");
        	entity.setConfrontation("『例』\n様々な能力、人の力を使い目標に向かう\n・鬼の協力者を味方につけ、元凶の鬼の血が必要だと知る\n・その元凶の鬼が唯一人を鬼に変える力を持ち、首を切っても死なない事を知る\n\n目標に向かうが課題や困難が立ちはだかる\n・妹が鬼という事が組織に伝わり、組織の隊長役に妹が殺されそうになる\n・主人公は秘めた力を持っているがその力を引き出すのに手こずる\n・鬼と対峙するも仲間に多くの犠牲を出す\n\n最大の課題、難関に立ち向かう\n・元凶の鬼と対峙する");
        	entity.setResolution("『例』\n最後の課題\n・元凶の鬼と戦うもまともに戦っても勝てないので時間を稼ぎ、日の光を浴びせて殺すことを試みる\n・多くの仲間を犠牲にするが何とか足止めし日の光によって消滅に成功\n\nどんでん返し、超展開\n・妹を人間に戻す薬の効果が効き、人間に戻ったのち最終決戦に加わる\n・絶望の中、主人公の力が解放され味方全体にバフがかかる\n・ラスボスを消滅出来たと思ったが、死の間際に主人公の体内に入り暴走を起こす\n・しかし、主人公は体内でラスボスと立ち向かい本当の消滅に成功");
        	plotrepository.saveAndFlush(entity);
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
