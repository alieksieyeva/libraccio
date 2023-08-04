package com.generation.repository;

import java.sql.SQLException;
import java.util.List;

import com.generation.entity.Autore;
import com.generation.entity.Libro;
import com.generation.entity.Publisher;
import com.generation.library.SQLConnection;

public class Database 
{

	private AutoreRepository repoAut;
	private LibroRepository repoLibri;
	private PublisherRepository repoPub;
	
	public Database (String filename, String autoreTabella, String libroTabella, String publisherTabella) throws SQLException
	{
		SQLConnection connection = new SQLConnection(filename);
		repoAut = new AutoreRepository(connection, autoreTabella);
		repoLibri = new LibroRepository(connection, libroTabella);
		repoPub = new PublisherRepository(connection, publisherTabella);
	}
	
	//tutti i metodi presenti nelle singole repository, ma prenderà in lettura anche gli oggetti collegati 
	//quando leggiamo un oggetto possiamo leggere anche i suoi pdri/figli (libro -- autore)
	
	//Obiettivo: leggere un libro COMPLETO
	//Significa un libro con tutte le proprietà impostate
	public Libro findLibroById (int id)
	{
		Libro res = repoLibri.findById(id);
		//completo ma non collegato ai padri
		
		//lo colleghiamo all'autore padre, chiedendo alla repository di autori di fornircelo
		linkPadri(res);
		
		//abbiamo un libro completo, con i padri collegati
		return res;
	}
	
	private void linkPadri(Libro l)
	{
		Autore a = repoAut.findById         //dammi mio PADRE, quello che ha chiave primaria uguale alla mia chiave esterna
				(
						l.getId_autore() //Chiave ESTERNA
				);
		l.setAutore(a);  //Prendendo la repository della tabella/entità collegata collego autore al libro
		a.addLibro(l); 	//collego il libro al suo autore
		
		Publisher p = repoPub.         //Prendendo la repository della tabella/entità collegata
			findById         //dammi mio PADRE, quello che ha chiave primaria uguale alla mia chiave esterna
				(
					l.getId_publisher() //Chiave ESTERNA
				);
		l.setPublisher(p);
		p.addLibro(l);
	}
	
	
	private void linkFigliAutore(Autore a) 
	{
		List<Libro> figli = repoLibri.findWhere(" id_autore = "+a.getId());
		
		for(Libro l : figli)
		{
			a.addLibro(l);
			l.setAutore(a);
		}
	}
	
	private void linkFigliPublisher(Publisher p) 
	{
		List<Libro> figli = repoLibri.findWhere(" id_publisher = "+p.getId());
		
		for(Libro l : figli)
		{
			p.addLibro(l);
			l.setPublisher(p);
		}
	}
	
	public Autore findAutoreById (int id)
	{
		Autore res = repoAut.findById(id);
		linkFigliAutore(res);
		return res;
	}
	
	public Publisher findPublisherById (int id)
	{
		Publisher res = repoPub.findById(id);
		linkFigliPublisher(res);
		return res;
	}
	
	public List <Libro> findAllLibri()
	{
		List <Libro> libri = repoLibri.findAll();
		for(Libro l: libri)
			linkPadri(l);
		
		return libri;
	}
	
	public List <Autore> findAllAutori()
	{
		List <Autore> autori = repoAut.findAll();
		for(Autore a: autori)
			linkFigliAutore (a); 
		
		return autori;
	}
	public List <Publisher> findAllPublisher()
	{
		List <Publisher> caseEditrici = repoPub.findAll();
			for(Publisher p: caseEditrici)
				linkFigliPublisher(p);
			
			return caseEditrici;
	}
	
	public List <Libro> findWhereLibri (String where)
	{
		List <Libro> res = repoLibri.findWhere(where);
		
		for(Libro l: res)
			linkPadri(l);
		
		return res;
	}
	
	public List <Autore> findWhereAutori (String where)
	{
		List <Autore> res = repoAut.findWhere(where);
		
		for(Autore a: res)
			linkFigliAutore(a);
		
		return res;
	}
	public List <Publisher> findWherePublisher (String where)
	{
		List <Publisher> res = repoPub.findWhere(where);
		
		for(Publisher p: res)
			linkFigliPublisher(p);
		
		return res;
	}
	
	public void deleteLibro (Libro l)
	{
		repoLibri.delete(l);
	}
	
	public void deleteLibro (int id)
	{
		repoLibri.delete(id);
	}
	
	public void deleteAutore (Autore a)
	{
		repoAut.delete(a);
	}
	
	public void deleteAutore (int id)
	{
		repoAut.delete(id);
	}
	
	public void deletePublisher (Publisher p)
	{
		repoPub.delete(p);
	}
	
	public void deletePublisher (int id)
	{
		repoPub.delete(id);
	}
	
	public void insertLibro (Libro l)
	{
		repoLibri.insert(l);
	}
	public void insertLibri (List <Libro> lista)
	{
		repoLibri.insert(lista);
	}
	
	public void insertAutore (Autore a)
	{
		repoAut.insert(a);
	}
	public void inserAutori(List <Autore> lista)
	{
		repoAut.insert(lista);
	}
	public void insertPublisher (Publisher p)
	{
		repoPub.insert(p);
	}
	public void inserPublisher(List <Publisher> lista)
	{
		repoPub.insert(lista);
	}
	
	public void updateLibro (Libro l)
	{
		repoLibri.update(l);
	}
	
	public void updateLibro (List <Libro> lista)
	{
		repoLibri.update(lista);
	}
	
	public void updateAutore (Autore a)
	{
		repoAut.update(a);
	}
	
	public void updateAutore (List <Autore> lista)
	{
		repoAut.update(lista);
	}
	
	public void updatePublisher (Publisher p)
	{
		repoPub.update(p);
	}
	
	public void updatePublisher (List <Publisher> lista)
	{
		repoPub.update(lista);
	}
}
