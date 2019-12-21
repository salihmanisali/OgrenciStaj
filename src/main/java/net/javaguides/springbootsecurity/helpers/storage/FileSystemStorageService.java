package net.javaguides.springbootsecurity.helpers.storage;

import net.javaguides.springbootsecurity.enums.DosyaTuru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	private Map<DosyaTuru,Path> locations;


	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		locations=new HashMap<>();

		Arrays.asList(DosyaTuru.values()).forEach(dosyaTuru ->
				locations.put(dosyaTuru,Paths.get(properties.getLocation()+dosyaTuru+"\\"))
				);
	}

	@Override
	public void store(MultipartFile file, DosyaTuru dosyaTuru) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream,load(filename,dosyaTuru),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}


	@Override
	public Path load(String filename,DosyaTuru dosyaTuru){
		return locations.get(dosyaTuru).resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename,DosyaTuru dosyaTuru) {
		try {
			Path file = load(filename,dosyaTuru);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void init() {

			locations.entrySet().forEach(location -> {
				try {
					Files.createDirectories(location.getValue());
				} catch (IOException e) {
					throw new StorageException("Could not initialize storage", e);
				}
			});
	}
}
