package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name = "messages")
@Data
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String content;

}
