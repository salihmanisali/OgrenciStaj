package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.entities.BaseEntity;
import net.javaguides.springbootsecurity.entities.User;
import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageFileNotFoundException;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import net.javaguides.springbootsecurity.repositories.FirmaRepository;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import net.javaguides.springbootsecurity.repositories.OgretmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Controller
public class UploadController {

	private final StorageService storageService;

	@Autowired
	private OgretmenRepository ogretmenRepository;

	@Autowired
	private OgrenciRepository ogrenciRepository;

	@Autowired
	private FirmaRepository firmaRepository;


	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename,DosyaTuru.OGRETMEN);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}


	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

//
//	@RequestMapping(value = "upload/{id}")
//	@ResponseBody
//	public ResponseEntity<byte[]> getImage(@PathVariable(value = "id") Long id) throws IOException {
//
//		Path path=storageService.load(id.toString(),DosyaTuru.OGRETMEN);
//
//		File serverFile = new File(path.toString());
//
//		byte[] image =  Files.readAllBytes(serverFile.toPath());
//		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
//
//	}


	@RequestMapping(value = "/upload/{dosyaTuru}/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable(value = "id") Integer id, @PathVariable(value = "dosyaTuru") DosyaTuru dosyaTuru) throws IOException {
		User baseEntity;

		if(dosyaTuru==DosyaTuru.OGRETMEN)
			baseEntity = ogretmenRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Hatalı Id:" + id));
		else if(dosyaTuru==DosyaTuru.OGRENCI)
			baseEntity = ogrenciRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Hatalı Id:" + id));
		else if(dosyaTuru==DosyaTuru.FIRMA)
			baseEntity = firmaRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Hatalı Id:" + id));
		else return null;

		Path path=storageService.load(baseEntity.getResimUrl(),dosyaTuru);

		File serverFile = new File(path.toString());

		byte[] image =  Files.readAllBytes(serverFile.toPath());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

	}
}
