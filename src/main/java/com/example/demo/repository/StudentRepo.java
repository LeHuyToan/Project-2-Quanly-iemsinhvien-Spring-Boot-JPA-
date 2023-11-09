package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer > {
	@Query("SELECT d FROM Student d WHERE d.studentCode LIKE :x")
	Page<Student> searchName(@Param("x")String studentCode ,Pageable pageable);
}
