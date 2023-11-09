package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/")
	public ResponseDTO<Void> newUser(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException {
		if (!userDTO.getFile().isEmpty()) {
			// tên file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// lưu lại file vào ổ cứng máy chủ
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// Lấy tên file lưu xuống Database
			userDTO.setAvatarUrl(filename);
		}
		userService.create(userDTO);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}
	
	@GetMapping("/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws IOException {
		File file = new File("D:/" + filename);
		Files.copy(file.toPath(), resp.getOutputStream());
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		userService.delete(id);
		return ResponseDTO.<Void>builder().status(200).msg("ok").build();
	}

	@GetMapping("/list")
	public ResponseDTO<List<UserDTO>> list() {
		List<UserDTO> userDTOs = userService.getAll();
		return ResponseDTO.<List<UserDTO>>builder().status(200).data(userDTOs).build();
	}

	@PostMapping("/search")
	public ResponseDTO<PageDTO<List<UserDTO>>> search(@ModelAttribute @Valid SearchDTO searchDTO) {
		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);
		return ResponseDTO.<PageDTO<List<UserDTO>>>builder().status(200).data(pageUser).build();
	}

	@PutMapping("/")
	public ResponseDTO<UserDTO> edit(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException  {
		if (!userDTO.getFile().isEmpty()) {
			// tên file upload
			String filename = userDTO.getFile().getOriginalFilename();
			// lưu lại file vào ổ cứng máy chủ
			File saveFile = new File("D:/" + filename);
			userDTO.getFile().transferTo(saveFile);
			// Lấy tên file lưu xuống Database
			userDTO.setAvatarUrl(filename);
		}
		userService.update(userDTO);

		return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();

	}
}
