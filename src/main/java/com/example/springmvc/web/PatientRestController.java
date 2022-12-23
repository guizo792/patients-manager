package com.example.springmvc.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springmvc.dao.PatientRepository;
import com.example.springmvc.entities.Patient;

@RestController
@RequestMapping("/api/v1")
public class PatientRestController {

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping(path = "/patients")
	public ResponseEntity<List<Patient>> getPatients() {
		List<Patient> patients = patientRepository.findAll();
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	@GetMapping(path = "/patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		try {
			Patient patient = patientRepository.findById(id).get();
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity("There is no patient with this id", HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/patients")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
		try {
			return new ResponseEntity<Patient>(patientRepository.save(patient), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("There was a problem adding this user", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/patients/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable Long id) {
		try {
			patientRepository.deleteById(id);
			return new ResponseEntity<String>("Patient deleted successfully", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("There was a problem deleting the user", HttpStatus.BAD_GATEWAY);
		}
	}

	@PutMapping(path = "/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient p) {
		try {
		Patient patient = patientRepository.findById(id).get();
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity("There is no user with this id :"+id, HttpStatus.BAD_GATEWAY);

		}
		
		try {
			
			return new ResponseEntity<Patient>(patientRepository.save(p), HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("There was a problem editing the user", HttpStatus.BAD_GATEWAY);
		}

	}
}
