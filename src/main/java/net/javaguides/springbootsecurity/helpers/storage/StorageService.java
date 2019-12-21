package net.javaguides.springbootsecurity.helpers.storage;

import net.javaguides.springbootsecurity.enums.DosyaTuru;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Repository
public interface StorageService {

	void init();

	void store(MultipartFile file, DosyaTuru dosyaTuru);

	Path load(String filename,DosyaTuru dosyaTuru);

	Resource loadAsResource(String filename,DosyaTuru dosyaTuru);
}
