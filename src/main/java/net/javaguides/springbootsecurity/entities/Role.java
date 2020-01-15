package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="roles")
@Data
public class Role extends BaseEntity
{
	@ManyToMany(mappedBy="roles")
	private List<User> users;
}
