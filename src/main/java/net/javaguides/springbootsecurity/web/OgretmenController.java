package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.OgretmenRepository;
import net.javaguides.springbootsecurity.repositories.OkulRepository;
import net.javaguides.springbootsecurity.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;

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

	@Autowired
	private StorageService storageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;


	@GetMapping("/ogretmenler")
	public String ogretmenler(Model model)
	{
		model.addAttribute("ogretmenler", ogretmenRepository.findAll());
		return "ogretmenler";
	}

	@GetMapping("/ogretmen")
	public String ogretmen(Model model)	{
		Ogretmen ogretmen = new Ogretmen();
//		ogretmen.setAdi("Salih");
//		ogretmen.setEmail("ff");
//		ogretmen = ogretmenRepository.save(ogretmen);

		model.addAttribute("ogretmen",ogretmen);
		model.addAttribute("okullar", okulRepository.findAll());
		return "ogretmen";
	}

	@GetMapping("/ogretmen/{id}")
	public String ogretmenById(Model model, @PathVariable Integer id)	{
		Ogretmen ogretmen = ogretmenRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğretmen Id:" + id));

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

	@PostMapping("/ogretmenhome")
	public String saveOgretmenHome(Ogretmen ogretmen){
		persistOgretmen(ogretmen);
		return "redirect:/ogretmenhome";
	}

	@PostMapping("/ogretmen")
	public String saveOgretmen(Ogretmen ogretmen){
		persistOgretmen(ogretmen);
		return "redirect:/ogretmenler";
	}

	private void persistOgretmen(Ogretmen ogretmen) {
		var resim = ogretmen.getResim();

		Ogretmen ogretmenEski = null;

		if(ogretmen.getId()!=null)
			ogretmenEski = ogretmenRepository.findById(ogretmen.getId()).orElse(null);

		if(ogretmenEski!=null) {
			var password = ogretmenEski.getPassword();
			var roles = ogretmenEski.getRoles();

			ogretmen.setPassword(password);
			ogretmen.setRoles(roles);

		}else{
			ogretmen.setPassword(passwordEncoder.encode(ogretmen.getPassword()));

			if(ogretmen.getRoles()==null) {
				ogretmen.setRoles(new LinkedList<>(Arrays.asList(customUserDetailsService.getOgretmenRole())));
			}
		}

		ogretmen = ogretmenRepository.save(ogretmen);
		ogretmen.setResim(resim);

		if(ogretmen.getResim()!=null && !ogretmen.getResim().getOriginalFilename().isEmpty()) {
			String ext = getExtension(ogretmen.getResim().getOriginalFilename());
			Path path = storageService.store(ogretmen.getResim(), ogretmen.getId().toString() + "." + ext, DosyaTuru.OGRETMEN);
			ogretmen.setResimUrl(path.toString());
			ogretmenRepository.save(ogretmen);
		}
	}


	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}
}
