package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.config.WebSecurityConfig;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.entities.Role;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import net.javaguides.springbootsecurity.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Salih Efe
 *
 */
@Controller
public class FirmaController {
	@Autowired
	private FirmaRepository firmaRepository;

	@Autowired
	private StorageService storageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;


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

	@GetMapping("/firmahome")
	public String firmahome(Model model, Principal principal) {
		var firma = firmaRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + principal.getName()));

		if (firma != null)
			model.addAttribute("firma", firma);

		model.addAttribute("firmauser",true);
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

	@PostMapping("/firmahome")
	public String saveFirmaHome(Firma firma)
	{
		persistFirma(firma);
		return "redirect:/firmahome";
	}

	@PostMapping("/firma")
	public String saveFirma(Firma firma)
	{
		persistFirma(firma);
		return "redirect:/firmalar";
	}

	private void persistFirma(Firma firma) {
		var resim = firma.getResim();

		Firma firmaEski = null;

		if(firma.getId()!=null)
			firmaEski = firmaRepository.findById(firma.getId()).orElse(null);

		if(firmaEski!=null) {
			var password = firmaEski.getPassword();
			var roles = firmaEski.getRoles();

			firma.setPassword(password);
			firma.setRoles(roles);

		}else{
			firma.setPassword(passwordEncoder.encode(firma.getPassword()));

			if(firma.getRoles()==null) {
				firma.setRoles(new LinkedList<>(Arrays.asList(customUserDetailsService.getFirmaRole())));
			}
		}

		firma = firmaRepository.save(firma);
		firma.setResim(resim);

		if(firma.getResim()!=null && !firma.getResim().getOriginalFilename().isEmpty()) {
			String ext = getExtension(firma.getResim().getOriginalFilename());
			Path path = storageService.store(firma.getResim(), firma.getId().toString() + "." + ext, DosyaTuru.FIRMA);
			firma.setResimUrl(path.toString());
			firmaRepository.save(firma);
		}
	}

	private String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

}
