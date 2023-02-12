


package com.example.novelist.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "chara")
@Data
public class Chara extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "chara_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long topicId;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String nickname;
    
    @Column(length = 50)
    private String role;
    
    @Column(length = 50)
    private String gendere;
    
    @Column(length = 50)
    private String age;
    
    @Column(length = 50)
    private String birthday;
    
    @Column(length = 50)
    private String height;
    
    @Column(length = 50)
    private String weight;
     
    @Column(length = 50)
    private String personality;
    
    @Column(length = 100)
    private String skill;
    
    @Column(length = 100)
    private String ability;
    
    @Column(length = 100)
    private String Appearance;
    
    @Column(length = 100)
    private String upbringing;
    
    @Column(length = 100)
    private String background;
    
    @Column(length = 200)
    private String others;
    
    @ManyToOne
    @JoinColumn(name = "topicId", insertable = false, updatable = false)
    private Topic topic;
    
    
}