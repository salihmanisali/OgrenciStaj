package net.javaguides.springbootsecurity.web;


import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import net.javaguides.springbootsecurity.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
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
	private OgrenciRepository ogrenciRepository;

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
		Firma firma = firmaRepository.findById(id)
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

	@GetMapping("/firmahome")
	public String firmahome(Model model, Principal principal) {
		Firma firma = firmaRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + principal.getName()));

		if (firma != null)
			model.addAttribute("firma", firma);

		model.addAttribute("firmauser",true);
		return "firma";
	}



	@PostMapping("/firmahome")
	public String saveFirmaHome(Firma firma){
		persistFirma(firma);
		return "redirect:/firmahome";
	}

	@PostMapping("/firma")
	public String saveFirma(Firma firma){
		persistFirma(firma);
		return "redirect:/firmalar";
	}

	private void persistFirma(Firma firma) {
		MultipartFile resim = firma.getResim();

		Firma firmaEski = null;

		if(firma.getId()!=null)
			firmaEski = firmaRepository.findById(firma.getId()).orElse(null);

		if(firmaEski!=null) {
			firma.setPassword(firmaEski.getPassword());
			firma.setRoles(firmaEski.getRoles());
			firma.setResimUrl(firmaEski.getResimUrl());

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

	@PostMapping("/favori/{id}")
	public ResponseEntity addtofavoriById(Model model, @PathVariable Integer id, Principal principal) {
		Firma firma = firmaRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma Id:" + principal.getName()));

		Ogrenci ogrenci = ogrenciRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğrenci Id:" + id));

		if(ogrenci.getFirmaList()==null){
			ArrayList<Firma> list = new ArrayList<>();
			list.add(firma);
			ogrenci.setFirmaList(list);
		}else if(!ogrenci.getFirmaList().contains(firma))
		{
			ogrenci.getFirmaList().add(firma);
		}

		ogrenciRepository.save(ogrenci);

		return ResponseEntity.ok().body(ogrenci.getId().toString());
	}


}
