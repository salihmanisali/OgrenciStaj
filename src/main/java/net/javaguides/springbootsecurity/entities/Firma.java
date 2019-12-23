package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="FIRMA")
@Data
public class Firma extends BaseEntity
{
	@Column(length = 40)
	private Long sicil;

	@Column(length = 500)
	private String faaliyetAlanlari;

	@Column(length = 300)
	private String insanKaynaklariSorumlusuAdi;

	@Column(length = 200)
	private String insanKaynaklariSorumlusuEmail;

	@Column(length = 30)
	private String insanKaynaklariSorumlusuTelefonu;

}
