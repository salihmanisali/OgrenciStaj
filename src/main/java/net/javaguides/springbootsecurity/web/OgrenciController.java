package net.javaguides.springbootsecurity.web;

import lombok.extern.log4j.Log4j2;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.entities.Okul;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
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
import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Salih Efe
 *
 */
@Controller
@Log4j2
public class OgrenciController
{
	@Autowired
	private OgrenciRepository ogrenciRepository;

	@Autowired
	OgretmenRepository ogretmenRepository;

	@Autowired
	private FirmaRepository firmaRepository;

	@Autowired
	private OkulRepository okulRepository;

	@Autowired
	private StorageService storageService;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;


	@GetMapping("/ogrenciler")
	public String ogrenciler(Model model)
	{
		model.addAttribute("ogrenciler", ogrenciRepository.findAll());
		return "ogrenciler";
	}

	@GetMapping("/ogrenciByFirma")
	public String ogrenciByFirma(Model model,Principal principal)
	{

		Firma firma = firmaRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma"));

		model.addAttribute("ogrenciler", firma.getOgrenciList());
		return "ogrencilist";
	}

	@GetMapping("/ogrencilerByOkul")
	public String ogrencilerByOkul(Model model,Principal principal)
	{

		Ogretmen ogretmen = ogretmenRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Firma"));

		Okul okul=ogretmen.getOkul();
		model.addAttribute("Okul",okul);
		model.addAttribute("ogrenciler", ogrenciRepository.findAllByOkul(okul));
		return "ogrencilist";
	}

	@GetMapping("/ogrencilist")
	public String ogrencilist(Model model)
	{
		model.addAttribute("ogrenciler", ogrenciRepository.findAll());
		return "ogrencilist";
	}


	@GetMapping("/ogrenci")
	public String ogrenci(Model model)	{

		Ogrenci ogrenci=new Ogrenci();
		model.addAttribute("ogrenci", ogrenci);
		model.addAttribute("okullar", okulRepository.findAll());
		return "ogrenci";
	}

	@GetMapping("/ogrenci/{id}")
	public String ogrenciById(Model model, @PathVariable Integer id)	{

		Ogrenci ogrenci = ogrenciRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğrenci Id:" + id));

		model.addAttribute("okullar", okulRepository.findAll());
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

	@GetMapping("/ogrencihome")
	public String ogrencihome(Model model, Principal principal) {
		var ogrenci = ogrenciRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğrenci Id:" + principal.getName()));

		if (ogrenci != null)
			model.addAttribute("ogrenci", ogrenci);

		model.addAttribute("ogrenciuser",true);
		return "ogrenci";
	}


	@PostMapping("/ogrencihome")
	public String saveOgrenciHome(Ogrenci ogrenci){
		persistOgrenci(ogrenci);
		return "redirect:/ogrencihome";
	}

	@PostMapping("/ogrenci")
	public String saveOgrenci(Ogrenci ogrenci){
		persistOgrenci(ogrenci);
		return "redirect:/ogrenciler";
	}


	private void persistOgrenci(Ogrenci ogrenci) {
		var resim = ogrenci.getResim();
		var cv = ogrenci.getCv();

		Ogrenci ogrenciEski = null;

		if(ogrenci.getId()!=null)
			ogrenciEski = ogrenciRepository.findById(ogrenci.getId()).orElse(null);

		if(ogrenciEski!=null) {

			ogrenci.setPassword(ogrenciEski.getPassword());
			ogrenci.setRoles(ogrenciEski.getRoles());
			ogrenci.setCvUrl(ogrenciEski.getCvUrl());
			ogrenci.setResimUrl(ogrenciEski.getResimUrl());

		}else{
			ogrenci.setPassword(passwordEncoder.encode(ogrenci.getPassword()));

			if(ogrenci.getRoles()==null) {
				ogrenci.setRoles(new LinkedList<>(Arrays.asList(customUserDetailsService.getOgrenciRole())));
			}
		}

		ogrenci = ogrenciRepository.save(ogrenci);
		ogrenci.setResim(resim);

		if(ogrenci.getResim()!=null && !ogrenci.getResim().getOriginalFilename().isEmpty()) {
			String ext = getExtension(ogrenci.getResim().getOriginalFilename());
			Path path = storageService.store(ogrenci.getResim(), ogrenci.getId().toString() + "." + ext, DosyaTuru.OGRENCI);
			ogrenci.setResimUrl(path.toString());
			ogrenciRepository.save(ogrenci);
		}

		ogrenci.setCv(cv);
		if(ogrenci.getCv()!=null && !ogrenci.getCv().getOriginalFilename().isEmpty()) {
			String ext = getExtension(ogrenci.getCv().getOriginalFilename());
			Path path = storageService.store(ogrenci.getCv(), ogrenci.getId().toString() + "." + ext, DosyaTuru.CV);
			ogrenci.setCvUrl(path.toString());
			ogrenciRepository.save(ogrenci);
		}
	}

	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	//TODO: Okul seçimi admin - ogretmen - ogrenci için ayrı ayrı
}
