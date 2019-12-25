package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Salih Efe
 *
 */

@MappedSuperclass
@Data
public class BaseEntity
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(length = 300)
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
}
