package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.Okul;
import net.javaguides.springbootsecurity.repositories.OkulRepository;
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
public class OkulController
{
	@Autowired
	private OkulRepository okulRepository;
	
	@GetMapping("/okullar")
	public String okullar(Model model)
	{
		model.addAttribute("okullar", okulRepository.findAll());
		return "okullar";
	}

	@GetMapping("/okul")
	public String okul(Model model)	{
		Okul okul=new Okul();
		model.addAttribute("okul", okul);
		return "okul";
	}

	@GetMapping("/okul/{id}")
	public String okulById(Model model, @PathVariable Integer id)	{
		var okul = okulRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Okul Id:" + id));

		if(okul!=null)
		model.addAttribute("okul", okul);
		return "okul";
	}

	@GetMapping("/okul/delete/{id}")
	public String deleteOkul(@PathVariable("id") Integer id, Model model) {
		Okul okul = okulRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Okul Id:" + id));

		okulRepository.delete(okul);
		model.addAttribute("okullar", okulRepository.findAll());
		return "/okullar";
	}

	@PostMapping("/okul")
	public String saveOkul(Okul okul)
	{
		okulRepository.save(okul);
		return "redirect:/okullar";
	}
}
