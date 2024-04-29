package presentation.pojo;

import java.util.List;

public class PojoFermataFE {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private String nomeLinea;
	private String direzione;
	private String previsioneMeteo;
	private String posizioneMezzo;
	private List<PojoOrarioFE> orariMezzi;
	private Integer numeroMezzi;
	private List<Integer> numIdMezzi;

	public PojoFermataFE() {
	}

	public PojoFermataFE(Integer id, Integer numFermata, String nomeFermata,
			String nomeLinea, String direzione, String previsioneMeteo,
			String posizioneMezzo, Integer numeroMezzi) {
		super();
		this.id = id;
		this.numFermata = numFermata;
		this.nomeFermata = nomeFermata;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
		this.previsioneMeteo = previsioneMeteo;
		this.posizioneMezzo = posizioneMezzo;
		this.numeroMezzi = numeroMezzi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumFermata() {
		return numFermata;
	}

	public void setNumFermata(Integer numFermata) {
		this.numFermata = numFermata;
	}

	public String getNomeFermata() {
		return nomeFermata;
	}

	public void setNomeFermata(String nomeFermata) {
		this.nomeFermata = nomeFermata;
	}

	public String getNomeLinea() {
		return nomeLinea;
	}

	public void setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
	}

	public String getPrevisioneMeteo() {
		return previsioneMeteo;
	}

	public void setPrevisioneMeteo(String previsioneMeteo) {
		this.previsioneMeteo = previsioneMeteo;
	}

	public String getPosizioneMezzo() {
		return posizioneMezzo;
	}

	public void setPosizioneMezzo(String posizioneMezzo) {
		this.posizioneMezzo = posizioneMezzo;
	}

	public List<PojoOrarioFE> getOrariMezzi() {
		return orariMezzi;
	}

	public void setOrariMezzi(List<PojoOrarioFE> orariMezzi) {
		this.orariMezzi = orariMezzi;
	}

	public Integer getNumeroMezzi() {
		return numeroMezzi;
	}

	public void setNumeroMezzi(Integer numeroMezzi) {
		this.numeroMezzi = numeroMezzi;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public List<Integer> getNumIdMezzi() {
		return numIdMezzi;
	}

	public void setNumIdMezzi(List<Integer> numIdMezzi) {
		this.numIdMezzi = numIdMezzi;
	}

	@Override
	public String toString() {
		return "PojoFermataFE [id=" + id + ", numFermata=" + numFermata
				+ ", nomeFermata=" + nomeFermata + ", nomeLinea=" + nomeLinea
				+ ", direzione=" + direzione + ", previsioneMeteo="
				+ previsioneMeteo + ", posizioneMezzo=" + posizioneMezzo
				+ ", orariMezzi=" + orariMezzi + ", numeroMezzi=" + numeroMezzi
				+ ", numIdMezzi=" + numIdMezzi + "]";
	}
}