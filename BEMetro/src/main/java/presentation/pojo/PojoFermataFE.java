package presentation.pojo;

import java.time.LocalTime;
import java.util.List;

import db.entity.Mezzo;

public class PojoFermataFE {
	private Integer id;
	private Integer numero_fermata;
	private String nome_fermata;
	private String nome_linea;
	private String direzione;
	private String previsione_meteo;
	private String posizione_mezzo;
	private List<LocalTime> tempi_arrivo;
	private List<LocalTime> ritardi_stimato;
	private Integer numero_mezzi;
	private List<Mezzo> listaMezzi;

	public PojoFermataFE() {
	}

	public PojoFermataFE(Integer id, Integer numero_fermata,
			String nome_fermata, String nome_linea, String direzione,
			String previsione_meteo, String posizione_mezzo,
			List<LocalTime> tempi_arrivo, List<LocalTime> ritardi_stimato,
			Integer numero_mezzi) {
		super();
		this.id = id;
		this.numero_fermata = numero_fermata;
		this.nome_fermata = nome_fermata;
		this.nome_linea = nome_linea;
		this.direzione = direzione;
		this.previsione_meteo = previsione_meteo;
		this.posizione_mezzo = posizione_mezzo;
		this.tempi_arrivo = tempi_arrivo;
		this.ritardi_stimato = ritardi_stimato;
		this.numero_mezzi = numero_mezzi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero_fermata() {
		return numero_fermata;
	}

	public void setNumero_fermata(Integer numero_fermata) {
		this.numero_fermata = numero_fermata;
	}

	public String getNome_fermata() {
		return nome_fermata;
	}

	public void setNome_fermata(String nome_fermata) {
		this.nome_fermata = nome_fermata;
	}

	public String getNome_linea() {
		return nome_linea;
	}

	public void setNome_linea(String nome_linea) {
		this.nome_linea = nome_linea;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public String getPrevisione_meteo() {
		return previsione_meteo;
	}

	public void setPrevisione_meteo(String previsione_meteo) {
		this.previsione_meteo = previsione_meteo;
	}

	public String getPosizione_mezzo() {
		return posizione_mezzo;
	}

	public void setPosizione_mezzo(String posizione_mezzo) {
		this.posizione_mezzo = posizione_mezzo;
	}

	public List<LocalTime> getTempi_arrivo() {
		return tempi_arrivo;
	}

	public void setTempi_arrivo(List<LocalTime> tempi_arrivo) {
		this.tempi_arrivo = tempi_arrivo;
	}

	public List<LocalTime> getRitardi_stimato() {
		return ritardi_stimato;
	}

	public void setRitardi_stimato(List<LocalTime> ritardi_stimato) {
		this.ritardi_stimato = ritardi_stimato;
	}

	public Integer getNumero_mezzi() {
		return numero_mezzi;
	}

	public void setNumero_mezzi(Integer numero_mezzi) {
		this.numero_mezzi = numero_mezzi;
	}

	public List<Mezzo> getListaMezzi() {
		return listaMezzi;
	}

	public void setListaMezzi(List<Mezzo> listaMezzi) {
		this.listaMezzi = listaMezzi;
	}

	@Override
	public String toString() {
		return "PojoFermataFE [id=" + id + ", numero_fermata=" + numero_fermata
				+ ", nome_fermata=" + nome_fermata + ", nome_linea="
				+ nome_linea + ", direzione=" + direzione
				+ ", previsione_meteo=" + previsione_meteo
				+ ", posizione_mezzo=" + posizione_mezzo + ", tempi_arrivo="
				+ tempi_arrivo + ", ritardi_stimato=" + ritardi_stimato
				+ ", numero_mezzi=" + numero_mezzi + ", listaMezzi="
				+ listaMezzi + "]";
	}
}