package net.javaguides.springbootsecurity.repositories;

import net.javaguides.springbootsecurity.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Salih Efe
 *
 */
public interface MessageRepository extends JpaRepository<Message, Integer>{

}
