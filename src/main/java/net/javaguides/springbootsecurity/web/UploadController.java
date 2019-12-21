package net.javaguides.springbootsecurity.web;

import net.javaguides.springbootsecurity.enums.DosyaTuru;
import net.javaguides.springbootsecurity.helpers.storage.StorageFileNotFoundException;
import net.javaguides.springbootsecurity.helpers.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class UploadController {

	private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException {

//		model.addAttribute("files", storageService.loadAll().map(
//				path -> MvcUriComponentsBuilder.fromMethodName(UploadController.class,
//						"serveFile", path.getFileName().toString()).build().toString())
//				.collect(Collectors.toList()));

		return "upload";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename,DosyaTuru.OGRETMEN);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file, DosyaTuru.OGRETMEN);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/upload";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

//	@GetMapping("/uploadStatus")
//	public String uploadStatus() {

//		return "uploadStatus";
//	}
//	@RequestMapping(value = "uploads/{imageName}")
//	@ResponseBody
//	public ResponseEntity<byte[]> getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
//
//		File serverFile = new File(UPLOADED_FOLDER + imageName + ".jpg");
//
//		byte[] image =  Files.readAllBytes(serverFile.toPath());
//		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
//
//	}
}
