package service.builder;

import java.time.LocalDate;

import presentation.pojo.PojoFermataFE;

public class PojoFermataFEBuilder {
	private Integer id;
	private Integer numero_fermata;
	private String nome_fermata;
	private String nome_linea;
	private String direzione;
	private String previsione_meteo;
	private LocalDate tempo_arrivo;
	private LocalDate ritardo_stimato;

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

	public PojoFermataFEBuilder setTempo_arrivo(LocalDate tempo_arrivo) {
		this.tempo_arrivo = tempo_arrivo;
		return this;
	}

	public PojoFermataFEBuilder setRitardo_stimato(LocalDate ritardo_stimato) {
		this.ritardo_stimato = ritardo_stimato;
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
		nuovoPojoFermataFE.setTempo_arrivo(tempo_arrivo);
		nuovoPojoFermataFE.setRitardo_stimato(ritardo_stimato);

		return nuovoPojoFermataFE;
	}
}
