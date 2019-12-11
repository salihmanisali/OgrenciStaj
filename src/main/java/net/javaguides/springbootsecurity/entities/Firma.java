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
public class Firma
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false,length = 300)
	@NotNull
	private String adi;

	@Column(nullable=false,length = 300)
	@NotNull
	private String insanKaynaklariSorumlusuAdi;

	@Column(nullable=false, unique=true,length = 200)
	private String insanKaynaklariSorumlusuEmail;

	@Column(length = 30)
	@NotNull
	private String insanKaynaklariSorumlusuTelefonu;

	@Column(nullable=false,length = 40)
	private Long sicil;

	@Column(nullable=false,length = 500)
	private String faaliyetAlanlari;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] logo;
}
