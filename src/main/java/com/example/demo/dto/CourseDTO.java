package com.example.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseDTO {
	private int id;
	@NotBlank
	private String name;
	
	@JsonFormat(pattern = "dd/MM/yyyy" , timezone = "Asia/Ha_Noi")
	private Date createAt;
	
	@JsonFormat(pattern = "dd/MM/yyyy" , timezone = "Asia/Ha_Noi")
	private Date updateAt;
}
