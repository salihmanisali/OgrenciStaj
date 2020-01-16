package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.entities.Okul;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Salih Efe
 *
 */
public interface OgrenciRepository extends JpaRepository<Ogrenci, Integer>{
    Optional<Ogrenci> findByEmail(String email);
    List<Ogrenci> findAllByOkul(Okul okul);
}
