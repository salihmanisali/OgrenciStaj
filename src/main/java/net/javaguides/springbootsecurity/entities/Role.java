package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="roles")
@Data
public class Role
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;
		
	@ManyToMany(mappedBy="roles")
	private List<User> users;
}
