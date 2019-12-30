package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

/**
 * @author Salih Efe
 *
 */
@Controller
public class OgrenciController
{
	@Autowired
	private OgrenciRepository ogrenciRepository;

	@Autowired
	private StorageService storageService;
	
	@GetMapping("/ogrenciler")
	public String ogrenciler(Model model)
	{
		model.addAttribute("ogrenciler", ogrenciRepository.findAll());
		return "ogrenciler";
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

	@GetMapping("/ogrencihome")
	public String ogrencihome(Model model)	{
		Integer id = 1;

		return ogrenciById(model,id);
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
		var resim = ogrenci.getResim();
		ogrenci = ogrenciRepository.save(ogrenci);

		ogrenci.setResim(resim);

		if(ogrenci.getResim()!=null && !ogrenci.getResim().getOriginalFilename().isEmpty()) {
			String ext = getExtension(ogrenci.getResim().getOriginalFilename());
			Path path = storageService.store(ogrenci.getResim(), ogrenci.getId().toString() + "." + ext, DosyaTuru.OGRENCI);
			ogrenci.setResimUrl(path.toString());
			ogrenciRepository.save(ogrenci);
		}
		return "redirect:/ogrenciler";
	}

	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

}
