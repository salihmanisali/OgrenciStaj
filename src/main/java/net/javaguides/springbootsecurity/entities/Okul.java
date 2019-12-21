package net.javaguides.springbootsecurity.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="OKUL")
@Data
public class Okul
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(length = 300)
	@NotNull
	private String adi;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] logo;

	@Override
	public String toString() {
		return adi;
	}
}
