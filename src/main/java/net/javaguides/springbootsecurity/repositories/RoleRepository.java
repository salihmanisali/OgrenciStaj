package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Salih Efe
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
