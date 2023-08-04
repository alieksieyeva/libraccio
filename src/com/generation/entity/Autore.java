package com.generation.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Autore
{

//	id int PK 
//	nome text 
//	cognome text 
//	sesso text 
//	dob date 
//	nazionalita text
	
	private int id;
	private String nome, cognome, sesso, nazionalita;
	private LocalDate dob;
	private ArrayList <Libro> libri = new ArrayList <Libro>();
	
	public Autore() {}
	
	public Autore(int id, String nome, String cognome, String sesso, String nazionalita, LocalDate dob) 
	{
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.nazionalita = nazionalita;
		this.dob = dob;
	}

	public ArrayList <Libro> getLibri()
	{
		return libri;
	}
	
	public void addLibro(Libro l)
	{
		if(!libri.contains(l))
			libri.add(l);
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getNome()
	{
		return nome;
	}
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public String getCognome() 
	{
		return cognome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public String getSesso()
	{
		return sesso;
	}
	public void setSesso(String sesso) 
	{
		this.sesso = sesso;
	}
	public String getNazionalita() 
	{
		return nazionalita;
	}
	public void setNazionalita(String nazionalita) 
	{
		this.nazionalita = nazionalita;
	}
	public LocalDate getDob() 
	{
		return dob;
	}
	public void setDob(LocalDate dob) 
	{
		this.dob = dob;
	}
	
	public void setDob(String dob) 
	{
		this.dob = LocalDate.parse(dob);
	}

	public String toString() 
	{
		return "{id:'" + id + "', nome:'" + nome + "', cognome:'" + cognome + "', sesso:'" + sesso + "', nazionalita:'"
				+ nazionalita + "', dob:'" + dob + "'}";
	}
	
	
}
