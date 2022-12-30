package com.example.springmvc;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springmvc.dao.PatientRepository;
import com.example.springmvc.entities.Patient;

@SpringBootApplication
public class SpringmvcApplication implements CommandLineRunner {
	
	@Autowired
	private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringmvcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        patientRepository.save(new Patient(null, " Imaizumi", new Date(), false, 4));
//        patientRepository.save(new Patient(null, " Manami", new Date(), false, 5));
//        patientRepository.save(new Patient(null, " Naruko", new Date(), false, 4));
//        
//        patientRepository.findAll().forEach(p -> {
//        	System.out.println(p.getName());
//        });
    }

}
