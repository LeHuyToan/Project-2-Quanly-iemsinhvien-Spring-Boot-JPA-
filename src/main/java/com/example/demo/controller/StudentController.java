package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;
	
	//Gia su khong upload file
	@PostMapping("/")
	public ResponseDTO<Void> newStudent(@RequestBody @Valid StudentDTO studentDTO){
		studentService.create(studentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/")
	public ResponseDTO<StudentDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<StudentDTO>builder().status(200).data(studentService.getById(id)).build();
	}
	
	@PutMapping("/")
	public StudentDTO edit(@RequestBody @Valid StudentDTO studentDTO){
		studentService.update(studentDTO);
		return studentDTO;
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		studentService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
		
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<StudentDTO>>> search(@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<StudentDTO>> pageDTO = studentService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<StudentDTO>>>builder().status(200).data(pageDTO).build();
	}

}
