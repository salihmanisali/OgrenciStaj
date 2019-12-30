package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import net.javaguides.springbootsecurity.enums.Ilce;

import javax.persistence.*;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="FIRMA")
@Data
public class Firma extends User
{
	@Column(length = 40)
	private Long sicil;

	@Column(length = 500)
	private String faaliyetAlanlari;

	@Column(length = 300)
	private String insanKaynaklariSorumlusuAdi;

	@Column(length = 30)
	private String insanKaynaklariSorumlusuTelefonu;



	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Ilce ilce;

	@ManyToMany(mappedBy="firmaList")
	private List<Ogrenci> ogrenciList;

}
