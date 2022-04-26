package com.mire.nysjangtuh.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long num;
    private String title;
    private String content;
    private String area;
    private String pic1;
    private String pic2;
    private String pic3;
    private String pic4;
    private String pic5;
    private Date date;
    private int view;
    private String user_id;


}
