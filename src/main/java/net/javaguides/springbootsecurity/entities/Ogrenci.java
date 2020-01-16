package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import net.javaguides.springbootsecurity.enums.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Salih Efe
 *
 */
@Entity
@Table(name="OGRENCI")
@Data
public class Ogrenci extends User
{
	@Column(length = 11)
	private Long tcKimlikNo;

	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	private Cinsiyet cinsiyet;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date dogumTarihi;

	@Column(length = 20)
	private String cepTelefonu;

	@Column(length = 200)
	private String adres;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Ilce ilce;

	@Column(length = 200)
	private String semt;

	@Column(length = 100)
	private String veliAdi;

	@Column(length = 20)
	private String veliCepTelefonu;

	@Column(length = 20)
	private String ehliyet;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private AskerlikDurumu askerlikDurumu;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private StajYapacagiGun stajYapacagiGun;

	////Okul

	@Column(length = 300)
	private String okulAlani;

	@Column(length = 300)
	private String okulDali;

	@Column
	private Long notOrtalamasi;

	@Column(length = 1000)
	private String okuduguKitaplar;

	@Column(length = 1000)
	private String gorevAldigiEtkinlikler;

	@Column(length = 300)
	private String referansOgretmenAdi;

	@Column(length = 30)
	private String referansOgretmenTelefon;

	@Column(length = 100)
	private String referansOgretmenEmail;

	////// Mezuniyet

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date mezuniyetTarihi1;

	@Column(length = 300)
	private String MezunOkulAdi2;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date mezuniyetTarihi2;

	@Column(length = 300)
	private String MezunOkulAdi3;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date mezuniyetTarihi3;

	@Column(length = 300)
	private String MezunOkulAdi4;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date mezuniyetTarihi4;

// Bilgisayar Becerileri

	@Column(length = 300)
	private String onParmakbeceri;

	@Column(length = 300)
	private String yazilimBeceri;

	@Column(length = 300)
	private String ofisBeceri;

	@Column(length = 300)
	private String donanimBeceri;

	@Column(length = 300)
	private String cizimBeceri;

	// Diger Beceriler

	@Column(length = 500)
	private String digerBeceriler;

	@Column(length = 500)
	private String isIleIlgiliBeceriler;

	@Column(length = 500)
	private String OrganizasyonelBeceriler;

	// Lisans

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private LisansTuru lisansTuru;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date lisansTarihi;

	@Column
	private Long lisansDerecesi;

	//region  Dil Becerileri

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Dil dil1;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Beceri dil1beceri;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Dil dil2;


	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Beceri dil2beceri;


	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Dil dil3;


	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Beceri dil3beceri;


	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Dil dil4;


	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Beceri dil4beceri;

	//endregion

	//region Proje ve Etkinlikler

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date projeTarihi1;

	@Column(length = 500)
	private String projeAdi1;

	@Column(length = 500)
	private String projedekiGorevi1;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date projeTarihi2;

	@Column(length = 500)
	private String projeAdi2;

	@Column(length = 500)
	private String projedekiGorevi2;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date projeTarihi3;

	@Column(length = 500)
	private String projeAdi3;

	@Column(length = 500)
	private String projedekiGorevi3;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date projeTarihi4;

	@Column(length = 500)
	private String projeAdi4;

	@Column(length = 500)
	private String projedekiGorevi4;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Column
	@Temporal(TemporalType.DATE)
	private Date projeTarihi5;

	@Column(length = 500)
	private String projeAdi5;

	@Column(length = 500)
	private String projedekiGorevi5;
	//endregion

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ogretmen")
	private Ogretmen sorumluOgretmen;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_okul")
	private Okul okul;

	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="ogrenci_firma",
			joinColumns={@JoinColumn(name="id_ogrenci", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="id_firma", referencedColumnName="ID")})
	private List<Firma> firmaList;

	@Transient
	private Boolean favori;

	@Transient
	private MultipartFile cv;

	@Column(length = 300)
	private String cvUrl;


}
