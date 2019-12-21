package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgretmenRepository;
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
public class OgretmenController
{
	@Autowired
	private OgretmenRepository ogretmenRepository;

	@Autowired
	private OkulRepository okulRepository;

	@GetMapping("/ogretmenler")
	public String ogretmenler(Model model)
	{
		model.addAttribute("ogretmenler", ogretmenRepository.findAll());
		return "ogretmenler";
	}

	@GetMapping("/ogretmen")
	public String ogretmen(Model model)	{
		Ogretmen ogretmen = new Ogretmen();
		model.addAttribute("ogretmen",ogretmen);
		model.addAttribute("okullar", okulRepository.findAll());
		return "ogretmen";
	}

	@GetMapping("/ogretmen/{id}")
	public String ogretmenById(Model model, @PathVariable Integer id)	{
		var ogretmen = ogretmenRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + id));

		if(ogretmen!=null)
			model.addAttribute("ogretmen", ogretmen);
		model.addAttribute("okullar", okulRepository.findAll());
		return "ogretmen";
	}

	@GetMapping("/ogretmen/delete/{id}")
	public String deleteOgretmen(@PathVariable("id") Integer id, Model model) {
		Ogretmen ogretmen = ogretmenRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğretmen Id:" + id));

		ogretmenRepository.delete(ogretmen);
		model.addAttribute("ogretmenler", ogretmenRepository.findAll());
		return "/ogretmenler";
	}


	@PostMapping("/ogretmen")
	public String saveFirma(Ogretmen ogretmen)
	{
		ogretmenRepository.save(ogretmen);
		return "redirect:/ogretmenler";
	}


}
