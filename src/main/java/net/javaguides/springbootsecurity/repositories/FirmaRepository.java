package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Firma;
import net.javaguides.springbootsecurity.entities.Ogrenci;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Salih Efe
 *
 */
public interface FirmaRepository extends JpaRepository<Firma, Integer>{

}
