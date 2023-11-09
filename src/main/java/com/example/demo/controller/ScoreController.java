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

import com.example.demo.dto.AvgScoreByCourse;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.SearchScoreDTO;
import com.example.demo.service.CourseService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ScoreService;

import jakarta.validation.Valid;

//ws: REST
@RestController
@RequestMapping("/score")
public class ScoreController {
	@Autowired
	ScoreService scoreService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid ScoreDTO scoreDTO) {
		scoreService.create(scoreDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	//HTTP STATUS CODE:
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		scoreService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
		
	}

	@GetMapping("/")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseDTO<ScoreDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<ScoreDTO>builder().status(200).data(scoreService.getById(id)).build();
	}
	
	@PutMapping("/")
	public ScoreDTO edit(@RequestBody @Valid ScoreDTO scoreDTO){
		scoreService.update(scoreDTO);
		return scoreDTO;
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<ScoreDTO>>> search(@RequestBody @Valid SearchScoreDTO searchDTO) {
		PageDTO<List<ScoreDTO>> pageDTO = scoreService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<ScoreDTO>>>builder().status(200).data(pageDTO).build();
	}
	
	@GetMapping("/avg-score-by-course")
	public ResponseDTO<List<AvgScoreByCourse>> avgScoreByCourse(){
		return ResponseDTO.<List<AvgScoreByCourse>>builder().status(200).data(scoreService.avgScoreByCourses()).build();
	}
}
