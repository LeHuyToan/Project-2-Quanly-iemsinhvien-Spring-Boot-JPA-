package com.example.demo.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.DepartmentService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

//ws: REST
@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;

	@PostMapping("/")
	public ResponseDTO<Void> create(@ModelAttribute @Valid DepartmentDTO departmentDTO) {
		departmentService.create(departmentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@PostMapping("/json")
	public ResponseDTO<Void> createNewJOSN(@RequestBody @Valid DepartmentDTO departmentDTO) {
		departmentService.create(departmentDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	//HTTP STATUS CODE:
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
		
	}

	@GetMapping("/")
	@ResponseStatus(code = HttpStatus.OK)
//	@Secured({"ROLE_ADMIN","ROLE_SYSADMIN"})
//	@RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})
	@PreAuthorize("isAuthenticated()")
//	@PostAuthorize
	public ResponseDTO<DepartmentDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<DepartmentDTO>builder().status(200).data(departmentService.getById(id)).build();
	}
	
	@PutMapping("/")
	public DepartmentDTO edit(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO){
		departmentService.update(departmentDTO);
		return departmentDTO;
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<DepartmentDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {
		
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(searchDTO);
		return ResponseDTO.<PageDTO<List<DepartmentDTO>>>builder().status(200).data(pageDTO).build();
	}
}
