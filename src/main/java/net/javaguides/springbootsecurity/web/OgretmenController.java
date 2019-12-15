package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgretmenRepository;
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

		return "ogretmen";
	}

	@GetMapping("/ogretmen/{id}")
	public String ogretmenById(Model model, @PathVariable Integer id)	{
		var ogretmen = ogretmenRepository.findById(id);
		if(ogretmen.get()!=null)
			model.addAttribute("ogretmen", ogretmen.get());
		return "ogretmen";
	}

	@PostMapping("/ogretmen")
	public String saveFirma(Ogretmen ogretmen)
	{
		ogretmenRepository.save(ogretmen);
		return "redirect:/ogretmenler";
	}


}
