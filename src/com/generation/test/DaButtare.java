package com.generation.test;

import com.generation.entity.Libro;
import com.generation.library.Console;
import com.generation.library.SQLConnection;
import com.generation.repository.LibroRepository;

public class DaButtare
{
	public static void main(String[] args)
	{
		
		
		Libro nomeR = new Libro();
		nomeR.setId(50);
		nomeR.setTitolo("Il nome del tulipano");
		nomeR.setGenere("narrativa");
		nomeR.setAnno_uscita(2023);
		nomeR.setPrezzo_unitario(17.99);
		nomeR.setCopie_vendute(7);
		nomeR.setId_autore(3);
		nomeR.setId_publisher(2);
		
	//Console.print(nomeR);
		
		SQLConnection db = new SQLConnection ("config.txt");
		
		LibroRepository repo = new LibroRepository (db,"libro");	
		//repo.delete(nomeR);
//		
	for (Libro l: repo.findAll())
			Console.print(l);
		
	//	System.out.println(repo.findById(95));
		
		
	}
}
