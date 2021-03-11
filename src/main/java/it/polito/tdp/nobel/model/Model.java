package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	
	private List<Esame> esami;
	
	private double bestMedia;
	private Set<Esame> soluzione;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		
		
		this.esami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		this.soluzione = new HashSet<>();
		this.bestMedia = 0.0;
		Set<Esame> parziale = new HashSet<>();
		this.cerca2(parziale, 0, numeroCrediti);
		
		return soluzione;
	}
	
	private void cerca(Set<Esame> parziale, int livello, int m) {
		
		//CASI TERMINALI
		int crediti = sommaCrediti(parziale);
		if(crediti>m) {
			return;
		}
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media>bestMedia) {
				soluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		if(livello == esami.size()) {
			return;
		}
		
		//generiamo i sottoproblemi
		//esami[L] è da aggiungere o no? Provo entrambe le cose
		
		//provo ad aggiungere
		parziale.add(esami.get(livello));
		cerca(parziale, livello+1, m);
		parziale.remove(esami.get(livello));
		
		//provo a non aggiungerlo
		cerca(parziale, livello+1, m);
		
	}
	
private void cerca2(Set<Esame> parziale, int livello, int m) {
		
		//CASI TERMINALI
		int crediti = sommaCrediti(parziale);
		if(crediti>m) {
			return;
		}
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media>bestMedia) {
				soluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		if(livello == esami.size()) {
			return;
		}
		
		//generiamo i sottoproblemi
		for(Esame e : esami) {
				if(!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, livello+1, m);
				parziale.remove(e);
			}
		}
		
	}
	
	public double calcolaMedia(Set<Esame> parziale) {
		int crediti = 0;
		int voti = 0;
		
		for(Esame e : parziale) {
			crediti += e.getCrediti();
			voti += e.getVoto() * e.getCrediti();
		}
		return (double) voti/crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma = 0;
		for(Esame e : parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}

}
