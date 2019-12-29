package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Role;
import net.javaguides.springbootsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @author Salih Efe
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
