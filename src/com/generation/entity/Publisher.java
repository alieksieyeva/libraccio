package com.generation.entity;

import java.util.ArrayList;

//una entità è un oggetto del nostro DOMINIO (ciò che riguarda il nostro progetto)
//qualcosa che esiste nel mondo reale e che ci è utile modelizzare

public class Publisher
{
//	Columns:
//		id int PK 
//		ragione_sociale text 
//		numero_impiegati int 
//		capitale_sociale int 
//		citta_sede text
	
	
	private int id;
	private String ragione_sociale, citta_sede;
	private int numero_impiegati, capitale_sociale;
	private ArrayList <Libro> libriPubblicati = new ArrayList <Libro>();
	
	//costruttori
	public Publisher () {}
	
	public Publisher(int id, String ragione_sociale, String citta_sede, int numero_impiegati, int capitale_sociale) 
	{
		this.id = id;
		this.ragione_sociale = ragione_sociale;
		this.citta_sede = citta_sede;
		this.numero_impiegati = numero_impiegati;
		this.capitale_sociale = capitale_sociale;
	}

	//getter e setter 
	public ArrayList <Libro> getLibri()
	{
		return libriPubblicati;
	}
	
	public void addLibro(Libro l)
	{
		if(!libriPubblicati.contains(l))
			libriPubblicati.add(l);
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getRagione_sociale() 
	{
		return ragione_sociale;
	}
	public void setRagione_sociale(String ragione_sociale) 
	{
		this.ragione_sociale = ragione_sociale;
	}
	public String getCitta_sede() 
	{
		return citta_sede;
	}
	public void setCitta_sede(String citta_sede) 
	{
		this.citta_sede = citta_sede;
	}
	public int getNumero_impiegati() 
	{
		return numero_impiegati;
	}
	public void setNumero_impiegati(int numero_impiegati) 
	{
		this.numero_impiegati = numero_impiegati;
	}
	public int getCapitale_sociale()
	{
		return capitale_sociale;
	}
	public void setCapitale_sociale(int capitale_sociale) 
	{
		this.capitale_sociale = capitale_sociale;
	}
	public ArrayList<Libro> getLibriPubblicati() 
	{
		return libriPubblicati;
	}
	public void setLibriPubblicati(ArrayList<Libro> libriPubblicati) 
	{
		this.libriPubblicati = libriPubblicati;
	}

	public String toString() {
		return "{id:'" + id + "', ragione_sociale:'" + ragione_sociale + "', citta_sede:'" + citta_sede
				+ "', numero_impiegati:'" + numero_impiegati + "', capitale_sociale:'" + capitale_sociale + "'}";
	}
	
	
	
}
