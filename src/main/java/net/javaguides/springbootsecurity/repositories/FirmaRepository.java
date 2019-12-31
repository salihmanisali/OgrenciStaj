package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Firma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Salih Efe
 *
 */
public interface FirmaRepository extends JpaRepository<Firma, Integer>{
    Optional<Firma> findByEmail(String email);
}
