package com.example.demo.job;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedule {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmailService emailService;
	
//	@Scheduled(fixedDelay = 60000)
	public void hello() {
		log.info("Hello");
//		emailService.testEmail();
	}
	
	//GIAY _ PHUT _ GIO _ NGAY _ THANG _ THU
	@Scheduled(cron = "0 8 22 * * *")
	public void morning() {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		
		//Thang 1 bắt đầu từ 0
		int month = cal.get(Calendar.MONTH) + 1 ;
		
		List<User> users = userRepo.searchByBirthday(date , month);
		for(User u : users) {
			log.info("Happy birthday <3" + u.getName());
			emailService.sendBirthdayEmail(u.getEmail(), u.getName());
		}
		log.info("<3");
	}
}
