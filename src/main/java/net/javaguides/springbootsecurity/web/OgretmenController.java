package net.javaguides.springbootsecurity.web;

import lombok.var;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.entities.Ogretmen;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgretmenRepository;
import net.javaguides.springbootsecurity.repositories.OkulRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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


	private final StorageService storageService;

	@Autowired
	public OgretmenController(StorageService storageService) {
		this.storageService = storageService;
	}

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


	@PostMapping("/ogretmen")
	public String saveOgretmen(Ogretmen ogretmen)
	{
		var resim = ogretmen.getResim();
		ogretmen = ogretmenRepository.save(ogretmen);

		ogretmen.setResim(resim);

		if(ogretmen.getResim()!=null && !ogretmen.getResim().getOriginalFilename().isEmpty()) {
			String ext = getExtension(ogretmen.getResim().getOriginalFilename());
			Path path = storageService.store(ogretmen.getResim(), ogretmen.getId().toString() + "." + ext, DosyaTuru.OGRETMEN);
			ogretmen.setResimUrl(path.toString());
			ogretmenRepository.save(ogretmen);
		}
		return "redirect:/ogretmenler";
	}

	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}


	@RequestMapping(value = "/upload/ogretmen/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable(value = "id") Integer id) throws IOException {

		Ogretmen ogretmen = ogretmenRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Hatalı Öğretmen Id:" + id));

		Path path=storageService.load(ogretmen.getResimUrl(),DosyaTuru.OGRETMEN);

		File serverFile = new File(path.toString());

		byte[] image =  Files.readAllBytes(serverFile.toPath());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

	}
}
