package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Salih Efe
 *
 */
@Controller
public class FirmaController {
	@Autowired
	private FirmaRepository firmaRepository;

	@GetMapping("/firmalar")
	public String firmalar(Model model) {
		model.addAttribute("firmalar", firmaRepository.findAll());
		return "firmalar";
	}

	@GetMapping("/firma")
	public String firma(Model model) {
		Firma firma = new Firma();
		model.addAttribute("firma", firma);
		return "firma";
	}


	@GetMapping("/firma/{id}")
	public String firmaById(Model model, @PathVariable Integer id) {
		var firma = firmaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + id));

		if (firma != null)
			model.addAttribute("firma", firma);
		return "firma";
	}

	@GetMapping("/firma/delete/{id}")
	public String deleteFirma(@PathVariable("id") Integer id, Model model) {
		Firma firma = firmaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + id));

		firmaRepository.delete(firma);
		model.addAttribute("firmalar", firmaRepository.findAll());
		return "/firmalar";
	}


	@PostMapping("/firma")
	public String saveFirma(Firma firma) {
		firmaRepository.save(firma);
		return "redirect:/firmalar";
	}


}
