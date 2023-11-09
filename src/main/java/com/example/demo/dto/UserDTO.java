package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy" , timezone = "Asia/Ha_Noi")
	private Date birthdate;

	//many to one
	private DepartmentDTO department;
	private List<String> roles;
	
	private int id;
	@Min(value = 0 , message = "{min.msg}")
	private int age;
	@NotBlank(message = "{not.blank}")
	private String name;
	private String avatarUrl;
	private String username;
	private String password;
	private String homeAddress;
	
	@JsonIgnore
	private MultipartFile file;

}
