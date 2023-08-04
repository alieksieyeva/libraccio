package com.generation.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entity.Libro;
import com.generation.library.Console;
import com.generation.library.SQLConnection;
//questa repository si occuperà di fare CRUD
//4 operazioni base su database: Create, Read, Update, Delete
public class LibroRepository 
{
	
	//questa mappa contiene i template delle query
	private static HashMap <String, String> commands = new HashMap <String, String>(); //proprietà static -- di classe, non di un oggett concret
	//creiamo un blocco di inizializzazione statica per commands
	//viene eseguito quando la classe viene compilata, prima che il programma parta
	static
	{ 
		String insert = "Insert into [tableName] (id, titolo, genere, anno_uscita, prezzo_unitario, copie_vendute, id_autore, id_publisher)"+
						"values ([id], [titolo], [genere], [anno_uscita], [prezzo_unitario], [copie_vendute], [id_autore], [id_publisher])";
								// tra le parentesi quadre ci sono i placeholder, verranno sostituiti con i valori di corrispettive proprietà
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set titolo=[titolo], genere=[genere], anno_uscita=[anno_uscita],"+ 
				"prezzo_unitario=[prezzo_unitario], copie_vendute=[copie_vendute], id_autore= [id_autore], id_publisher=[id_publisher] WHERE id=[id]";
		commands.put("update", update);
		
		String delete = "DELETE from [tableName] WHERE id=[id]";
		commands.put("delete", delete);
		
		String read = "SELECT * from [tableName] [where]";
		commands.put("read", read);
	}
	
	//ho aggiunto caching ai metodi findById, findWhere, save
	//fatelo per le repo di autori e publisher
	private HashMap <Integer, Libro> cache = new HashMap <Integer, Libro>();
	private SQLConnection con;
	private String tableName;
	
	
	public LibroRepository (SQLConnection con,String tableName)
	{
		this.con = con;
		this.tableName = tableName;
	}
	//questo metodo prende un libro e lo trasforma in una mappa
	private HashMap<String, String> objectToMap (Libro l)
	{
		HashMap <String, String> res = new HashMap <String, String>();
		res.put("id", l.getId()+"");
		res.put("titolo", l.getTitolo());
		res.put("genere", l.getGenere());
		res.put("anno_uscita", l.getAnno_uscita()+"");
		res.put("prezzo_unitario", l.getPrezzo_unitario()+"");
		res.put("copie_vendute", l.getCopie_vendute()+"");
		res.put("id_autore", l.getId_autore()+"");
		res.put("id_publisher", l.getId_publisher()+"");
		
		return res;
	}
	//questo metodo prende una mappa e la trasforma in un libro
	private Libro mapToObject(Map<String,String> mappa)
	{
		Libro res  = new Libro();
		res.setId(Integer.parseInt( mappa.get("id")) );
		res.setTitolo(mappa.get("titolo")) ;
		res.setGenere(mappa.get("genere")) ;
		res.setAnno_uscita(Integer.parseInt( mappa.get("anno_uscita")) );
		res.setPrezzo_unitario(Double.parseDouble(mappa.get("prezzo_unitario")) );
		res.setCopie_vendute(Integer.parseInt( mappa.get("copie_vendute")));
		res.setId_autore(Integer.parseInt( mappa.get("id_autore")));
		res.setId_publisher(Integer.parseInt( mappa.get("id_publisher")));
		
		return res;
	}
	
	private List<HashMap<String, String>> objectsToMaps(List<Libro> lista)
	{
		ArrayList<HashMap<String, String>> res = new ArrayList<HashMap<String, String>>();
		
		for(Libro l : lista)
			res.add(objectToMap(l));
		
		return res;
	}
	
	private List<Libro> mapsToObjects(List<Map<String, String>> maps)
	{
		ArrayList<Libro> res = new ArrayList<Libro>();
		
		for(Map<String, String> m : maps)
			res.add(mapToObject(m));
		
		return res;
	}

	private String apicizza (String s)
	{
		return "'"+s+"'";
	}
	
	private void save (Libro l, boolean update)
	{
		String query;
		Map <String, String> converted = objectToMap(l);
		if(update)
			query = commands.get("update");
		else
			query = commands.get("insert");
		
		for (String key: converted.keySet())
			query =	query.replace("["+key+"]",apicizza(converted.get(key)));
			//key = prezzo_unitario
			//query.replace("[prezzo_unitario]", "'5'"); 
		//"Insert into [tableName] (id, titolo, genere, anno_uscita, prezzo_unitario, copie_vendute, id_autore, id_publisher)"+
		//"values ([id], [titolo], [genere], [anno_uscita], '5', [copie_vendute], [id_autore], [id_publisher])";
		//questa sostituzione avviene per ogni valore di proprietà di libro
		query = query.replace("[tableName]", tableName);
		
		con.executeDML(query);
		
		cache.put(l.getId(), l);
	}
	
	private void saveMultiple (List <Libro> libri, boolean update)
	{
		for (Libro l: libri)
			save(l, update);
		
	}
	
	public void insert (Libro l)
	{
		save(l, false);
	}
	
	public void insert(List <Libro> libri)
	{
		saveMultiple(libri, false);
	}
	
	public void update (Libro l)
	{
		save(l, true);
	}
	
	public void update(List <Libro> libri)
	{
		saveMultiple(libri, true);
	}
	
	public void delete (Libro l)
	{
		String query = commands.get("delete");
		query = query.replace("[id]", l.getId()+"");
		query = query.replace("[tableName]", tableName);
		
		con.executeDML(query);
	}
	
	public void delete (int id)
	{
		String query = commands.get("delete");
		query = query.replace("[id]", id+"");
		query = query.replace("[tableName]", tableName);
		
		con.executeDML(query);
	}
	
	//metodo per la lettura completa
	public List<Libro> findAll ()
	{
		return findWhere("1=1");
	}
	//metodo per la lettura di un libro
	public Libro findById (int id)
	{
		if(cache.containsKey(id))
			return	cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "where id ="+id);
		
		List<Map <String, String>> result = con.executeQuery(query);
		Libro res;
		if(result.size()==1)
			res = mapToObject(result.get(0));
		else
			res=null;
		
		cache.put(id, res);
		
		return res;
	}
	
	public List<Libro> findWhere (String where)
	{
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "where "+where);
		// "SELECT * from libro where ....";
		
		List <Map <String, String>> result = con.executeQuery(query);
		List <Libro> res = mapsToObjects(result);
		
		for(int i =0;i<res.size();i++)
			if(!cache.containsKey(res.get(i).getId())) //se la cache non contiene l'iesimo libro letto
				cache.put                               //aggiungilo  
					(
						res.get(i).getId(),           //utilizzando il suo id come chiave
						res.get(i) 					  //se stesso come valore
					);
			else 								//se invece già lo abbiamo
				res.set                        //sostituisci nella lista 
					(
						i,                         //l'elemento alla sua posizione(se stesso)
						cache.get(res.get(i).getId())//con quello che già abbiamo in cache
					);
		
		return res;
	}
	
}
