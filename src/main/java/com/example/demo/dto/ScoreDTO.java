package com.example.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ScoreDTO{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double score;
	
	private CourseDTO course;
	private StudentDTO student;
	
	@JsonFormat(pattern = "dd/MM/yyyy" , timezone = "Asia/Ha_Noi")
	private Date createAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy" , timezone = "Asia/Ha_Noi")
	private Date updateAt;
}
