package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.UserRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface StudentService {
	void create(StudentDTO studentDTO);
	
	void update(StudentDTO studentDTO);
	 
	void delete(int id);
	
	StudentDTO getById(int id);
	
	PageDTO<List<StudentDTO>> search(SearchDTO searchDTO);
}

@Service
class StudentImpl implements StudentService{

	@Autowired
	StudentRepo studentRepo;
	
	@Override
	@Transactional
	public void create(StudentDTO studentDTO) {
//		User user = new ModelMapper().map(studentDTO.getUser(), User.class);
		
		//dung casecade
		Student student = new ModelMapper().map(studentDTO, Student.class);
//		student.setUser(user);
//		student.setStudentCode(studentDTO.getStudentCode());
		studentRepo.save(student);
	}

	@Override
	@Transactional
	public void update(StudentDTO studentDTO) {
//		Student student = studentRepo.findById(studentDTO.getUser()).orElse(null);
//		if (student != null) {
//			student.setStudentCode(studentDTO.getStudentCode());
//			studentRepo.save(student);
//		}	
	}

	@Override
	public void delete(int id) {
		studentRepo.deleteById(id);
	}

	@Override
	public StudentDTO getById(int id) {
		Student student = studentRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(student);
	}

	private StudentDTO convert(Student student) {
		ModelMapper modelMapper =  new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return modelMapper.map(student, StudentDTO.class);
	}

	@Override
	public PageDTO<List<StudentDTO>> search(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("studentCode").ascending();
		if (StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}

		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(5);
		}
		if(searchDTO.getKeyword() == null)
			searchDTO.setKeyword("");
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<Student> page = studentRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<StudentDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		// List<User> users = page.getContent();
		List<StudentDTO> studentDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T : List<UserDTO>
		pageDTO.setData(studentDTOs);

		return pageDTO;
	}
	
}