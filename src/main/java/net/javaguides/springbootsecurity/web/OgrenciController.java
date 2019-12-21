package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
		Ogrenci ogrenci=new Ogrenci();
		model.addAttribute("ogrenci", ogrenci);
		return "ogrenci";
	}

	@GetMapping("/ogrenci/{id}")
	public String ogrenciById(Model model, @PathVariable Integer id)	{
		var ogrenci = ogrenciRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğrenci Id:" + id));

		if(ogrenci!=null)
		model.addAttribute("ogrenci", ogrenci);
		return "ogrenci";
	}

	@GetMapping("/ogrenci/delete/{id}")
	public String deleteOgrenci(@PathVariable("id") Integer id, Model model) {
		Ogrenci ogrenci = ogrenciRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğrenci Id:" + id));

		ogrenciRepository.delete(ogrenci);
		model.addAttribute("ogrenciler", ogrenciRepository.findAll());
		return "/ogrenciler";
	}

	@PostMapping("/ogrenci")
	public String saveOgrenci(Ogrenci ogrenci)
	{
		ogrenciRepository.save(ogrenci);
		return "redirect:/ogrenciler";
	}
}
