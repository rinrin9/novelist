package com.example.novelist.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "topic")
@Data
public class Topic extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "topic_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false, length = 25000)
    private String description;
    
    @Column(nullable = false, length = 50)
    private String title;
    
    private boolean keep = false;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "topicId", insertable = false, updatable = false)
    private List<Favorite> favorites;
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "topicId", insertable = false, updatable = false)
    private List<Comment> comments;
    
}