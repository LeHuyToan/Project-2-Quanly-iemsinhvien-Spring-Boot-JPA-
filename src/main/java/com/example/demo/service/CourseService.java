package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.repository.CourseRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface CourseService {
	void create(CourseDTO courseDTO);
	
	void update(CourseDTO courseDTO);
	 
	void delete(int id);
	
	CourseDTO getById(int id);
	
	PageDTO<List<CourseDTO>> search(SearchDTO searchDTO);
}
@Service
class CourseImpl implements CourseService{
	@Autowired
	CourseRepo courseRepo;
	
	@Override
	@Transactional
	public void create(CourseDTO courseDTO) {
		Course course = new ModelMapper().map(courseDTO, Course.class);
		courseRepo.save(course);
	}

	@Override
	@Transactional
	public void update(CourseDTO courseDTO) {
		Course course = courseRepo.findById(courseDTO.getId()).orElse(null);
		if (course != null) {
			course.setName(courseDTO.getName());
			courseRepo.save(course);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		courseRepo.deleteById(id);
	}

	@Override
	public CourseDTO getById(int id) {
		Course course = courseRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(course);
	}

	private CourseDTO convert(Course course) {
		return new ModelMapper().map(course, CourseDTO.class);
	}
	
	@Override
	public PageDTO<List<CourseDTO>> search(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending();
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
		Page<Course> page = courseRepo.searchName("%" + searchDTO.getKeyword() + "%", pageRequest);

		PageDTO<List<CourseDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		// List<User> users = page.getContent();
		List<CourseDTO> courseDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T : List<UserDTO>
		pageDTO.setData(courseDTOs);

		return pageDTO;
	}
	
}