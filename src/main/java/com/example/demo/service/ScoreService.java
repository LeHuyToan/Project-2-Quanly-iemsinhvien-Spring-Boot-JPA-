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

import com.example.demo.dto.AvgScoreByCourse;
import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.SearchScoreDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.entity.Score;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepo;
import com.example.demo.repository.ScoreRepo;
import com.example.demo.repository.StudentRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface ScoreService {
	void create(ScoreDTO scoreDTO);
	
	void update(ScoreDTO scoreDTO);
	 
	void delete(int id);
	
	ScoreDTO getById(int id);
	
	PageDTO<List<ScoreDTO>> search(SearchScoreDTO searchDTO);
	
	List<AvgScoreByCourse> avgScoreByCourses();
}
@Service
class ScoreImpl implements ScoreService{
	@Autowired
	ScoreRepo scoreRepo;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	CourseRepo courseRepo;
	
	@Override
	@Transactional
	public void create(ScoreDTO scoreDTO) {
		Score score = new ModelMapper().map(scoreDTO, Score.class);
		scoreRepo.save(score);
	}

	@Override
	@Transactional
	public void update(ScoreDTO scoreDTO) {
		Score score = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
		score.setScore(scoreDTO.getScore());
			
		Student student = studentRepo.findById(scoreDTO.getStudent().getUser().getId()).orElseThrow(NoResultException::new);
		score.setStudent(student);
		
		
		Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new);
		score.setCourse(course);
		scoreRepo.save(score);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScoreDTO getById(int id) {
		Score score = scoreRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(score);
	}
	
	private ScoreDTO convert(Score score) {
		return new ModelMapper().map(score, ScoreDTO.class);
	}

	@Override
	public PageDTO<List<ScoreDTO>> search(SearchScoreDTO searchDTO) {
		Sort sortBy = Sort.by("id").ascending();
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
		Page<Score> page = null;
		if(searchDTO.getCourseId()!= null) {
			page = scoreRepo.searchByCourse(searchDTO.getCourseId(), pageRequest);
		}else if (searchDTO.getStudentId() != null) {
			page = scoreRepo.searchByStudent(searchDTO.getStudentId(), pageRequest);
		}else {
			page = scoreRepo.findAll(pageRequest);
		}

		PageDTO<List<ScoreDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		// List<User> users = page.getContent();
		List<ScoreDTO> scoreDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		// T : List<UserDTO>
		pageDTO.setData(scoreDTOs);

		return pageDTO;
	}

	@Override
	public List<AvgScoreByCourse> avgScoreByCourses() {
		return scoreRepo.avgScoreByCourse();
	}

	

}