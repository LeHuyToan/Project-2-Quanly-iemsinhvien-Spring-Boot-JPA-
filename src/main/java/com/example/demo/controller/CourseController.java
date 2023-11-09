package com.example.demo.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.service.CourseService;
import com.example.demo.service.DepartmentService;

import jakarta.validation.Valid;

//ws: REST
@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	CourseService courseService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid CourseDTO courseDTO) {
		courseService.create(courseDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	//HTTP STATUS CODE:
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
		
	}

	@GetMapping("/")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDTO<CourseDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<CourseDTO>builder().status(200).data(courseService.getById(id)).build();
	}
	
	@PutMapping("/")
	public CourseDTO edit(@RequestBody @Valid CourseDTO courseDTO){
		courseService.update(courseDTO);
		return courseDTO;
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<CourseDTO>>> search(@RequestBody @Valid SearchDTO searchDTO) {
		PageDTO<List<CourseDTO>> pageDTO = courseService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<CourseDTO>>>builder().status(200).data(pageDTO).build();
	}
}
