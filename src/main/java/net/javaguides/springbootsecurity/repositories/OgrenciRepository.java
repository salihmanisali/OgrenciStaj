package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Salih Efe
 *
 */
public interface OgrenciRepository extends JpaRepository<Ogrenci, Integer>{
    Optional<Ogrenci> findByEmail(String email);
}
