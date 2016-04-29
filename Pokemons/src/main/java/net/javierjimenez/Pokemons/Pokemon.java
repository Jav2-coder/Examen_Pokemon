package net.javierjimenez.Pokemons;

public class Pokemon {

	private String nom;
	private double pes;
	private int[] tipus;
	private double vida;
	private double poder;
	private int id;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPes() {
		return pes;
	}

	public void setPes(double d) {
		this.pes = d;
	}

	public int[] getTipus() {
		return tipus;
	}

	public void setTipus(int[] tipus2) {
		this.tipus = tipus2;
	}

	public double getVida() {
		return vida;
	}

	public void setVida(long vida) {
		this.vida = vida;
	}

	public double getPoder() {
		return poder;
	}

	public void setPoder(double d) {
		this.poder = d;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pokemon() {
		
	}
	
}
