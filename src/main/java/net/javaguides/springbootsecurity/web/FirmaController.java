package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		var firma = firmaRepository.findById(id);
		if (firma.get() != null)
			model.addAttribute("firma", firma.get());
		return "firma";
	}

	@PostMapping("/firma")
	public String saveFirma(Firma firma) {
		firmaRepository.save(firma);
		return "redirect:/firmalar";
	}
}
