package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import net.javaguides.springbootsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Salih Efe
 *
 */
public interface FirmaRepository extends JpaRepository<Firma, Integer>{
    Optional<User> findByEmail(String email);
}
