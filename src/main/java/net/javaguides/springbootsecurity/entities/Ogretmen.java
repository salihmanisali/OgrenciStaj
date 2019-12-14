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
@Table(name="OGRETMEN")
@Data
public class Ogretmen
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(length = 300)
	@NotNull
	private String adi;

	@Column(length = 100)
	@NotNull
	private String email;

	@Column(length = 100)
	private String telefon;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] resim;

}
