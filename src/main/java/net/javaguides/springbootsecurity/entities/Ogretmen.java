package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="FIRMA")
@Data
public class Ogretmen
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(nullable=false,length = 300)
	@NotNull
	private String adi;

	@Column(nullable=false,length = 100)
	@NotNull
	private String email;

	@Column(nullable=false, unique=true,length = 100)
	private String telefon;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] resim;

}
