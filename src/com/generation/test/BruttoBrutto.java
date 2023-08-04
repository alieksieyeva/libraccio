package com.generation.test;

import java.util.List;

import com.generation.entity.Autore;
import com.generation.entity.Libro;
import com.generation.library.Console;
import com.generation.repository.Database;

public class BruttoBrutto 
{

	public static void main(String[] args) 
	{
		Database db = new Database("config.txt","autore","libro","publisher");
		
		List<Libro> libri = db.findAllLibri();
		Libro l = libri.get(21);
		Libro l1 = libri.get(20);
//		Console.print(l);
//		Console.print(l1);
		db.findAllLibri();
		db.findAllLibri();
		db.findAllLibri();
		db.findAllLibri();
		db.findAllLibri();
		Autore by = l.getAutore();
		Autore by2 = l1.getAutore();
		Console.print(by);
		Console.print(by.getLibri());
		
	}

}
