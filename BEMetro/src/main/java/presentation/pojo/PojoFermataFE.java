package presentation.pojo;

import java.time.LocalTime;

public class PojoFermataFE {
	private Integer id;
	private Integer numero_fermata;
	private String nome_fermata;
	private String nome_linea;
	private String direzione;
	private String previsione_meteo;
	private LocalTime tempo_arrivo;
	private LocalTime ritardo_stimato;

	public PojoFermataFE() {
	}

	public PojoFermataFE(Integer id, Integer numero_fermata,
			String nome_fermata, String nome_linea, String direzione,
			String previsione_meteo, LocalTime tempo_arrivo,
			LocalTime ritardo_stimato) {
		super();
		this.id = id;
		this.numero_fermata = numero_fermata;
		this.nome_fermata = nome_fermata;
		this.nome_linea = nome_linea;
		this.direzione = direzione;
		this.previsione_meteo = previsione_meteo;
		this.tempo_arrivo = tempo_arrivo;
		this.ritardo_stimato = ritardo_stimato;
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

	public LocalTime getTempo_arrivo() {
		return tempo_arrivo;
	}

	public void setTempo_arrivo(LocalTime tempo_arrivo) {
		this.tempo_arrivo = tempo_arrivo;
	}

	public LocalTime getRitardo_stimato() {
		return ritardo_stimato;
	}

	public void setRitardo_stimato(LocalTime ritardo_stimato) {
		this.ritardo_stimato = ritardo_stimato;
	}

	@Override
	public String toString() {
		return "PojoFermataFE [id=" + id + ", numero_fermata=" + numero_fermata
				+ ", nome_fermata=" + nome_fermata + ", nome_linea="
				+ nome_linea + ", direzione=" + direzione
				+ ", previsione_meteo=" + previsione_meteo + ", tempo_arrivo="
				+ tempo_arrivo + ", ritardo_stimato=" + ritardo_stimato + "]";
	}
}