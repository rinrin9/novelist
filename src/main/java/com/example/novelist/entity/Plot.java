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
@Table(name = "plot")
@Data
public class Plot extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "plot_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String title;

    @Column
    private String first;

    @Column
    private String second;
    
    @Column
    private String third;
    
    @Column
    private String fourth;
    
    @Column
    private String firstshow;

    @Column
    private String secondshow;
    
    @Column
    private String thirdshow;
    
    @Column
    private String fourthshow;
    
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
}
