package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.Message;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.MessageRepository;
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
public class OgrenciController
{
	@Autowired
	private OgrenciRepository ogrenciRepository;
	
	@GetMapping("/ogrenciler")
	public String ogrenciler(Model model)
	{
		model.addAttribute("ogrenciler", ogrenciRepository.findAll());
		return "ogrenciler";
	}

	@GetMapping("/ogrenci")
	public String ogrenci(Model model)	{
		return "ogrenci";
	}

	@PostMapping("/ogrenci")
	public String saveOgrenci(Ogrenci ogrenci)
	{
		ogrenciRepository.save(ogrenci);
		return "redirect:/ogrenciler";
	}
}
