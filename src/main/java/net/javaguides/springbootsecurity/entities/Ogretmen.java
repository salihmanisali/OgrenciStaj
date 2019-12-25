package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="OGRETMEN")
@Data
public class Ogretmen  extends BaseEntity
{
	@Column(length = 100)
	private String email;

	@Column(length = 100)
	private String telefon;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_okul")
	private Okul okul;

	@Transient
	private MultipartFile resim;

	@Column(length = 300)
	private String resimUrl;
}
