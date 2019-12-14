package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Salih Efe
 *
 */
@Controller
public class FirmaController
{
	@Autowired
	private FirmaRepository firmaRepository;
	
	@GetMapping("/firmalar")
	public String firmalar(Model model)
	{
		model.addAttribute("firmalar", firmaRepository.findAll());
		return "firmalar";
	}

	@GetMapping("/firma")
	public String firma(Model model)	{
		return "firma";
	}

	@PostMapping("/firma")
	public String saveFirma(Firma firma)
	{
		firmaRepository.save(firma);
		return "redirect:/firmalar";
	}
}
