package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Ogretmen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Salih Efe
 *
 */
public interface OgretmenRepository extends JpaRepository<Ogretmen, Integer>{
    Optional<Ogretmen> findByEmail(String email);
}
