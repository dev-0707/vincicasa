package com.tool.vincicasa.estrazioni;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Concorso
 */
@Entity
@Table(name = "concorsi")
public class Concorso {
	@Id
	private int numero;
	private int anno;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(unique=true)
	private Date dataEstrazione;
	private String combinazioneVincente;
	private Boolean dettaglioDisponibile;
	private String promozione;

	public Concorso() {
		super();
	}

	public Concorso(int numero, int anno, Date dataEstrazione, String combinazioneVincente) {
		super();
		this.numero = numero;
		this.dataEstrazione = dataEstrazione;
		this.combinazioneVincente = combinazioneVincente;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getDataEstrazione() {
		return dataEstrazione;
	}

	public void setDataEstrazione(Date dataEstrazione) {
		this.dataEstrazione = dataEstrazione;
	}

	public String getCombinazioneVincente() {
		return combinazioneVincente;
	}

	public void setCombinazioneVincente(String combinazioneVincente) {
		this.combinazioneVincente = combinazioneVincente;
	}

	public Boolean getDettaglioDisponibile() {
		return dettaglioDisponibile;
	}

	public void setDettaglioDisponibile(Boolean dettaglioDisponibile) {
		this.dettaglioDisponibile = dettaglioDisponibile;
	}

	public String getPromozione() {
		return promozione;
	}

	public void setPromozione(String promozione) {
		this.promozione = promozione;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
