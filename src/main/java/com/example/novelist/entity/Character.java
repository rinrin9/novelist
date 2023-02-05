


package com.example.novelist.entity;

import java.io.Serializable;

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
@Table(name = "character")
@Data
public class Character extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "character_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String nickname;
    
    @Column
    private String role;
    
    @Column
    private String gendere;
    
    @Column
    private String age;
    
    @Column
    private String birthday;
    
    @Column
    private String height;
    
    @Column
    private String weight;
     
    @Column
    private String personality;
    
    @Column
    private String skill;
    
    @Column
    private String ability;
    
    @Column
    private String Appearance;
    
    @Column
    private String upbringing;
    
    @Column
    private String background;
    
    @Column
    private String others;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "topicId", insertable = false, updatable = false)
    private Topic topic;
    
    
}