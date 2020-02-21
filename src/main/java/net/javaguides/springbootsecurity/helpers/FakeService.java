package net.javaguides.springbootsecurity.helpers;

import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.repositories.OgrenciRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakeService {
	@Autowired
	OgrenciRepository ogrenciRepository;

	public void start() {
		for (Integer i = 0; i < 1000; i++) {
			Ogrenci ogrenci=new Ogrenci();
			String text=RandomStringUtils.randomAlphanumeric(10);
			ogrenci.setAdi(text);
			ogrenci.setPassword(text);
			ogrenci.setEmail("q@"+text);
			ogrenciRepository.save(ogrenci);
		}
	}
}
