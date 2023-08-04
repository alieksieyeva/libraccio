package com.generation.controller;

import java.sql.SQLException;
import java.util.List;

import com.generation.entity.Autore;
import com.generation.entity.Libro;
import com.generation.library.Console;
import com.generation.repository.Database;

public class Main
{

	private static Database db;
	
	public static void main(String[] args) 
	{
		try
		{
			db = new Database("config.txt","autore","libro","publisher");
		}
		catch (SQLException e)
		{
			Console.print("Controlla il tuo file di configurazione, conessione al db fallita");
			System.exit(-1);
		}
		
		String cmd = "";
		
		do
		{
			Console.print("Inserisci un comando");
			cmd = Console.readString();
			
			switch(cmd)
			{
				case "leggi libri":
					_leggiTuttiLibri();
				break;
				
				case "leggi autore":
					_leggiAutore();
				break;
				
				case "inserisci autore":
					_inserireAutore();
				break;
				
				case "inserisci libro":
					_inserireLibro();
			}
			
			
		}
		while(!cmd.equalsIgnoreCase("quit"));

	}

	private static void _inserireLibro() 
	{
		Libro daInserire = new Libro();
		Console.print("Inserisci id del nuovo libro");
		daInserire.setId(Console.readInt());
		Console.print("Inserisci il titolo del nuovo libro");
		daInserire.setTitolo(Console.readString());
		Console.print("Inserisci il genere del nuovo libro");
		daInserire.setGenere(Console.readString());
		Console.print("Inserisci anno di uscita del nuovo libro");
		daInserire.setAnno_uscita(Console.readInt());
		Console.print("Inserisci prezzo del nuovo libro");
		daInserire.setPrezzo_unitario(Console.readDouble());
		Console.print("Inserisci copie vendute del nuovo libro");
		daInserire.setCopie_vendute(Console.readInt());
		Console.print("Inserisci id dell'autore del nuovo libro");
		daInserire.setId_autore(Console.readInt());
		Console.print("Inserisci id del publisher del nuovo libro");
		daInserire.setId_publisher(Console.readInt());
		
		
		db.insertLibro(daInserire);
		Console.print("Libro inserito");
		
	}

	private static void _inserireAutore()
	{
		Autore daInserire = new Autore();
		Console.print("Inserisci id del nuovo autore");
		daInserire.setId(Console.readInt());
		Console.print("Inserisci il nome del nuovo autore");
		daInserire.setNome(Console.readString());
		Console.print("Inserisci il cognome del nuovo autore");
		daInserire.setCognome(Console.readString());
		Console.print("Inserisci il genere del nuovo autore");
		daInserire.setSesso(Console.readString());
		Console.print("Inserisci la data di nascita del nuovo autore");
		daInserire.setDob(Console.readString());
		Console.print("Inserisci la nazionalità del nuovo autore");
		daInserire.setNazionalita(Console.readString());
		
		db.insertAutore(daInserire);
		Console.print("Autore inserito");
	}

	private static void _leggiAutore() 
	{
		Console.print("Inserisci id dell'autore da leggere");
		Autore a = db.findAutoreById(Console.readInt());
		Console.print(a);
			
			for(Libro l: a.getLibri())
				Console.print(l);
		
	}

	private static void _leggiTuttiLibri() 
	{
		List <Libro> res = db.findAllLibri();
			for(Libro l: res)
				Console.print(l);
	}

}
