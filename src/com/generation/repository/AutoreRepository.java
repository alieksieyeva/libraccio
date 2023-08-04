package com.generation.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entity.Autore;
import com.generation.entity.Libro;
import com.generation.library.SQLConnection;

public class AutoreRepository 
{
	private static HashMap<String,String> commands = new HashMap<String,String>();
	static
	{
		String insert = "INSERT INTO [tableName] (id, nome, cognome, sesso, dob, nazionalità)" +
						"VALUES ([id], [nome], [cognome], [sesso], [dob], [nazionalita]";
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set nome=[nome], cognome=[cognome], sesso=[sesso]," + 
				"dob=[dob], nazionalita=[nazionalita] WHERE id=[id]";
		commands.put("update", update);
		
		String delete = "DELETE FROM [tableName] WHERE id=[id]";
		commands.put("delete", delete);
		
		String read = "SELECT * FROM [tableName] [where]";
		commands.put("read", read);
	}
	
	private HashMap <Integer, Autore> cache = new HashMap <Integer, Autore>();
	private SQLConnection con;
	private String tableName;
	
	
	public AutoreRepository(SQLConnection con, String tableName) 
	{
		this.con = con;
		this.tableName = tableName;
	}
	
	private HashMap<String,String> objectToMap(Autore a)
	{
		HashMap<String, String> res = new HashMap<String,String>();
		res.put("id", String.valueOf(a.getId()));
		res.put("nome", a.getNome());
		res.put("cognome", a.getCognome());
		res.put("sesso", a.getSesso());
		res.put("dob", String.valueOf(a.getDob()));
		res.put("nazionalita", a.getNazionalita());
		
		return res;
	}
	
	private Autore mapToObject(Map<String,String> mappa)
	{
		Autore res  = new Autore();
		 res.setId(Integer.parseInt( mappa.get("id")) );
		 res.setNome(mappa.get("nome")) ;
		 res.setCognome(mappa.get("cognome")) ;
		 res.setSesso(mappa.get("sesso"));
		 res.setDob(LocalDate.parse(mappa.get("dob")));
		 res.setNazionalita(mappa.get("nazionalita"));
		
		return res;
	}
	
	public List<HashMap<String,String>> objectsToMaps(List<Autore> lista)
	{
		ArrayList<HashMap<String,String>> res = new ArrayList<HashMap<String,String>>();
		
		for(Autore a : lista)
			res.add(objectToMap(a));
		
		return res;
	}
	
	private List<Autore> mapsToObjects(List<Map<String,String>> maps)
	{
		ArrayList<Autore> res = new ArrayList<Autore>();
		
		for(Map<String, String> m : maps)
			res.add(mapToObject(m));
		
		return res;
	}
	
	private String apicizza(String s)
	{
		return "'" + s + "'";
	}
	
	private void save(Autore a, boolean update)
	{
		String query; 
		Map<String,String> converted = objectToMap(a);
		if (update)
			query = commands.get("update");
		else
			query = commands.get("insert");
		
		for (String key : converted.keySet())
			query = query.replace("[" + key + "]", apicizza(converted.get(key)));
			// key = prezzo_unitario
			// query.replace("[prezzo_unitario]", "'5'");
		query = query.replace("[tableName]", tableName);
		con.executeDML(query);
		
		cache.put(a.getId(), a);
	}
	
	private void saveMultiple(List<Autore> autori, boolean update)
	{
		for (Autore l : autori)
			save(l,update);
	}
	
	public void insert(Autore a)
	{
		save(a, false);
	}
	
	public void insert(List<Autore> autori)
	{
		saveMultiple(autori, false);
	}
	
	public void update(Autore a)
	{
		save(a, true);
	}
	
	public void update(List<Autore> autori)
	{
		saveMultiple(autori, true);
	}
	
	public void delete(Autore a)
	{
		String query = commands.get("delete");
		query = query.replace("[id]", a.getId() + "");
		query = query.replace("[tableName]", tableName);
		
		con.executeDML(query);
	}
	
	public void delete(int id)
	{
		String query = commands.get("delete");
		query = query.replace("[id]", id + "");
		query = query.replace("[tableName]", tableName);
		
		con.executeDML(query);
	}
	
	public List<Autore> findAll()
	{
		return findWhere("1 = 1");
	}
	
	public Autore findById(int id)
	{
		if(cache.containsKey(id))
			return	cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "WHERE id = " + id);
		
		List<Map<String,String>> result = con.executeQuery(query);
		Autore res;
		if (result.size() == 1)
			res = mapToObject(result.get(0));
		else
			res = null;
		
		cache.put(id, res);
		
		return res;
	}
	
	public List<Autore> findWhere(String where)
	{
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "WHERE" + where);
		
		List<Map<String,String>> result = con.executeQuery(query);
		List<Autore> res = mapsToObjects(result);
		
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
