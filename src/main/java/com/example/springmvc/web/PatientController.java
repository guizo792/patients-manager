package com.example.springmvc.web;

import java.awt.print.Pageable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springmvc.dao.PatientRepository;
import com.example.springmvc.entities.Patient;

@Controller
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping(path = "/")
	public String index() {
		return "index";
	}

	@GetMapping(path = "/patients")
	public String listPatients(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
//		Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page, size));
		Page<Patient> pagePatients = patientRepository.findByNameContains(keyword, PageRequest.of(page, size));
		model.addAttribute("patients", pagePatients.getContent());
		model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("keyword", keyword);
		return "patients";
	}

	@GetMapping(path = "/deletePatient")
	public String delete(Long id, String keyword, int page, int size) {
		patientRepository.deleteById(id);
		// set the page to the previous one if there is no more element in the current
		if (patientRepository.findByNameContains(keyword, PageRequest.of(page, size)).getContent().size() == 0
				&& page > 0) {
			page--;
		}
		;
		return "redirect:/patients?page=" + page + "&size=" + size + "&keyword=" + keyword;
	}

	@GetMapping(path = "/formPatient")
	public String formPatient(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("mode", "new");
		return "formPatient";
	}

	@PostMapping("/savePatient")
	public String savePatient( Model model, @Valid Patient patient, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "formPatient";
		patientRepository.save(patient);
		model.addAttribute("patient", patient);
		return "confirmation";
	}
	
	@GetMapping(path = "/editPatient")
	public String editPatient(Model model, Long id) {
		Patient patient = patientRepository.findById(id).get();
		model.addAttribute("patient", patient);
		model.addAttribute("mode", "edit");
		return "formPatient";
	}
	
}
