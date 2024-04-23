package service.builder;

import java.time.LocalTime;
import java.util.List;

import db.entity.Mezzo;
import presentation.pojo.PojoFermataFE;

public class PojoFermataFEBuilder {
	private Integer id;
	private Integer numero_fermata;
	private String nome_fermata;
	private String nome_linea;
	private String direzione;
	private String previsione_meteo;
	private List<LocalTime> tempi_arrivo;
	private List<LocalTime> ritardi_stimato;
	private String posizione_mezzo;
	private Integer numero_mezzi;
	private List<Mezzo> listaMezzi;

	public PojoFermataFEBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public PojoFermataFEBuilder setNumero_fermata(Integer numero_fermata) {
		this.numero_fermata = numero_fermata;
		return this;
	}

	public PojoFermataFEBuilder setNome_fermata(String nome_fermata) {
		this.nome_fermata = nome_fermata;
		return this;
	}

	public PojoFermataFEBuilder setNome_Linea(String nome_linea) {
		this.nome_linea = nome_linea;
		return this;
	}

	public PojoFermataFEBuilder setDirezione(String direzione) {
		this.direzione = direzione;
		return this;
	}

	public PojoFermataFEBuilder setPrevisione_meteo(String previsione_meteo) {
		this.previsione_meteo = previsione_meteo;
		return this;
	}

	public PojoFermataFEBuilder setTempi_arrivo(List<LocalTime> tempi_arrivo) {
		this.tempi_arrivo = tempi_arrivo;
		return this;
	}

	public PojoFermataFEBuilder setRitardi_stimato(
			List<LocalTime> ritardi_stimato) {
		this.ritardi_stimato = ritardi_stimato;
		return this;
	}

	public PojoFermataFEBuilder setPosizione_mezzo(String posizione_mezzo) {
		this.posizione_mezzo = posizione_mezzo;
		return this;
	}

	public PojoFermataFEBuilder setNumero_mezzi(Integer numero_mezzi) {
		this.numero_mezzi = numero_mezzi;
		return this;
	}

	public PojoFermataFEBuilder setListaMezzi(List<Mezzo> listaMezzi) {
		this.listaMezzi = listaMezzi;
		return this;
	}

	public PojoFermataFE costruisci() {
		PojoFermataFE nuovoPojoFermataFE = new PojoFermataFE();

		nuovoPojoFermataFE.setId(id);
		nuovoPojoFermataFE.setNumero_fermata(numero_fermata);
		nuovoPojoFermataFE.setNome_fermata(nome_fermata);
		nuovoPojoFermataFE.setNome_linea(nome_linea);
		nuovoPojoFermataFE.setDirezione(direzione);
		nuovoPojoFermataFE.setPrevisione_meteo(previsione_meteo);
		nuovoPojoFermataFE.setTempi_arrivo(tempi_arrivo);
		nuovoPojoFermataFE.setRitardi_stimato(ritardi_stimato);
		nuovoPojoFermataFE.setPosizione_mezzo(posizione_mezzo);
		nuovoPojoFermataFE.setNumero_mezzi(numero_mezzi);
		nuovoPojoFermataFE.setListaMezzi(listaMezzi);

		return nuovoPojoFermataFE;
	}
}
