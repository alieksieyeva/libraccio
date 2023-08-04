package com.generation.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entity.Libro;
import com.generation.entity.Publisher;
import com.generation.library.SQLConnection;

public class PublisherRepository 
{
	private static HashMap<String,String> commands = new HashMap<String,String>();
	static
	{
		String insert = "INSERT INTO [tableName] (id, ragione_sociale, numero_impiegati, capitale_sociale, citta_sede)" +
						"VALUES ([id], [ragione_sociale], [numero_impiegati], [capitale_sociale], [citta_sede])";
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set ragione_sociale=[ragione_sociale], numero_impiegati=[numero_impiegati], capitale_sociale=[capitale_sociale]," + 
				"citta_sede=[citta_sede] WHERE id=[id]";
		commands.put("update", update);
		
		String delete = "DELETE FROM [tableName] WHERE id=[id]";
		commands.put("delete", delete);
		
		String read = "SELECT * FROM [tableName] [where]";
		commands.put("read", read);
	}
	
	private HashMap <Integer, Publisher> cache = new HashMap <Integer, Publisher>();
	private SQLConnection con;
	private String tableName;
	
	public PublisherRepository(SQLConnection con, String tableName) 
	{
		this.con = con;
		this.tableName = tableName;
	}
	
	private HashMap<String,String> objectToMap(Publisher p)
	{
		HashMap<String, String> res = new HashMap<String,String>();
		res.put("id", String.valueOf(p.getId()));
		res.put("ragione_sociale", p.getRagione_sociale());
		res.put("numero_impiegati", String.valueOf(p.getNumero_impiegati()));
		res.put("capitale_sociale", String.valueOf(p.getCapitale_sociale()));
		res.put("citta_sede", p.getCitta_sede());
		
		return res;
	}
	
	private Publisher mapToObject(Map<String,String> mappa)
	{
		Publisher res  = new Publisher();
		 res.setId(Integer.parseInt( mappa.get("id")) );
		 res.setRagione_sociale(mappa.get("ragione_sociale"));
		 res.setNumero_impiegati(Integer.parseInt(mappa.get("numero_impiegati")));
		 res.setCapitale_sociale(Integer.parseInt(mappa.get("capitale_sociale")));
		 res.setCitta_sede(mappa.get("citta_sede"));
		 
		return res;
	}
	
	public List<HashMap<String,String>> objectsToMaps(List<Publisher> lista)
	{
		ArrayList<HashMap<String,String>> res = new ArrayList<HashMap<String,String>>();
		
		for(Publisher p : lista)
			res.add(objectToMap(p));
		
		return res;
	}
	
	private List<Publisher> mapsToObjects(List<Map<String,String>> maps)
	{
		ArrayList<Publisher> res = new ArrayList<Publisher>();
		
		for(Map<String, String> m : maps)
			res.add(mapToObject(m));
		
		return res;
	}
	
	private String apicizza(String s)
	{
		return "'" + s + "'";
	}
	
	private void save(Publisher p, boolean update)
	{
		String query; 
		Map<String,String> converted = objectToMap(p);
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
		
		cache.put(p.getId(), p);
	}
	
	private void saveMultiple(List<Publisher> publishers, boolean update)
	{
		for (Publisher p : publishers)
			save(p,update);
	}
	
	public void insert(Publisher p)
	{
		save(p, false);
	}
	
	public void insert(List<Publisher> publishers)
	{
		saveMultiple(publishers, false);
	}
	
	public void update(Publisher p)
	{
		save(p, true);
	}
	
	public void update(List<Publisher> publishers)
	{
		saveMultiple(publishers, true);
	}
	
	public void delete(Publisher p)
	{
		String query = commands.get("delete");
		query = query.replace("[id]", p.getId() + "");
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
	
	public List<Publisher> findAll()
	{
		return findWhere("1 = 1");
	}
	
	public Publisher findById(int id)
	{
		if(cache.containsKey(id))
			return	cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "WHERE id = " + id);
		
		List<Map<String,String>> result = con.executeQuery(query);
		Publisher res;
		if (result.size() == 1)
			res = mapToObject(result.get(0));
		else
			res = null;
		cache.put(id, res);
		
		return res;
	}
	
	public List<Publisher> findWhere(String where)
	{
		String query = commands.get("read");
		query = query.replace("[tableName]", tableName);
		query = query.replace("[where]", "WHERE" + where);
		
		List<Map<String,String>> result = con.executeQuery(query);
		List<Publisher> res = mapsToObjects(result);
		
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
