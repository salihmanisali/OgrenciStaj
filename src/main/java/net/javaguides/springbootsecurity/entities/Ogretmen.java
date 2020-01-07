package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="OGRETMEN")
@Data
public class Ogretmen  extends User
{
	@Column(length = 100)
	private String telefon;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_okul")
	@NotNull
	private Okul okul;

}
