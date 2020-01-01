package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Salih Efe
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name="users",uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@NotNull
	private String adi;

	@Override
	public String toString() {
		return adi;
	}

	@Transient
	private MultipartFile resim;

	@Column(length = 300)
	private String resimUrl;

	@Column(nullable=false, unique=true)
	@NotEmpty
	@Email(message="{errors.invalid_email}")
	@Size(min=3,max = 50)
	private String email;

	@Column(nullable=false)
	@NotEmpty
	@Size(min=4,max = 100)
	private String password;

	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="user_role",
			joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
	private List<Role> roles;
}
