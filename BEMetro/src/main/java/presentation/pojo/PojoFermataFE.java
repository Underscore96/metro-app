package presentation.pojo;

import java.util.List;

public class PojoFermataFE {
	private Integer id;
	private Integer numFermata;
	private String nomeFermata;
	private String nomeLinea;
	private String direzione;
	private String orarioAttuale;
	private String previsioneMeteo;
	private String posizioneMezzo;
	private Integer numMezzi;
	private List<PojoStatoMezzoFE> statiMezzi;
	private List<PojoOrarioFE> orariMezzi;

	public PojoFermataFE() {
	}

	public PojoFermataFE(Integer id, Integer numFermata, String nomeFermata,
			String nomeLinea, String direzione, String orarioAttuale,
			String previsioneMeteo, Integer numMezzi,
			List<PojoStatoMezzoFE> statiMezzi, String posizioneMezzo) {
		super();
		this.id = id;
		this.numFermata = numFermata;
		this.nomeFermata = nomeFermata;
		this.nomeLinea = nomeLinea;
		this.direzione = direzione;
		this.orarioAttuale = orarioAttuale;
		this.previsioneMeteo = previsioneMeteo;
		this.numMezzi = numMezzi;
		this.statiMezzi = statiMezzi;
		this.posizioneMezzo = posizioneMezzo;
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

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public String getOrarioAttuale() {
		return orarioAttuale;
	}

	public void setOrarioAttuale(String orarioAttuale) {
		this.orarioAttuale = orarioAttuale;
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

	public Integer getNumMezzi() {
		return numMezzi;
	}

	public void setNumMezzi(Integer numMezzi) {
		this.numMezzi = numMezzi;
	}

	public List<PojoStatoMezzoFE> getStatiMezzi() {
		return statiMezzi;
	}

	public void setStatiMezzi(List<PojoStatoMezzoFE> statiMezzi) {
		this.statiMezzi = statiMezzi;
	}

	public List<PojoOrarioFE> getOrariMezzi() {
		return orariMezzi;
	}

	public void setOrariMezzi(List<PojoOrarioFE> orariMezzi) {
		this.orariMezzi = orariMezzi;
	}

	@Override
	public String toString() {
		return "PojoFermataFE [id=" + id + ", numFermata=" + numFermata
				+ ", nomeFermata=" + nomeFermata + ", nomeLinea=" + nomeLinea
				+ ", direzione=" + direzione + ", orarioAttuale="
				+ orarioAttuale + ", previsioneMeteo=" + previsioneMeteo
				+ ", posizioneMezzo=" + posizioneMezzo + ", numMezzi="
				+ numMezzi + ", statiMezzi=" + statiMezzi + ", orariMezzi="
				+ orariMezzi + "]";
	}
}