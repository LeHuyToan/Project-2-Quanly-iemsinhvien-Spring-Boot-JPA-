package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Table(name ="user")//map to table SQL
@Entity	//
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
//	@OneToMany(mappedBy = "user")
//	private List<UserRole> roles;

	//Áp dụng với bảng chỉ 2 cột , 1 cột là FK
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name ="user_role",
		joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<String> roles;
	
	@ManyToOne
	private Department department;
	
	private int age;
	private String name;
	//lưu tên file path
	private String avatarUrl;
	@Column(unique = true)
	private String username;
	private String password;
	private String homeAddress;
	
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	private String email;
	
	

}
