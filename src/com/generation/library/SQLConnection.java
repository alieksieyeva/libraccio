package com.generation.library;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.nocrala.tools.texttablefmt.Table;
/**
 * Gli oggetti di questa classe si occupano di creare una connessione con il database <br/>
 * Tramite essi potrete leggere e scrivere dal database
 * 
 * @author Stefano Rubinetti/Irene Alieksieieva
 *
 */
public class SQLConnection 
{
	public static void printResult(List<Map<String,String>> result)
	{
		if(result.size()==0)
		{
			System.out.println("Ho letto 0 righe");
		}
		
		;
		Iterator<String>keys = new LinkedList<String>(result.get(0).keySet()).iterator();
		Table t = new Table(result.get(0).keySet().size());
		while(keys.hasNext())
		{
			t.addCell(keys.next());
		}
		
		for(Map<String,String> row : result)
		{
			
			Iterator<String>k = new LinkedList<String>(row.keySet()).iterator();
			
			while(k.hasNext())
			{
				t.addCell(row.get(k.next()));
			}
		}
		System.out.println(t.render());
		
	}
	
	
	private String username;
	private String password;
	private String dbname;
	private String filename;
	private Connection connection;

	/**
	 * Passate come parametro a questo costruttore il nome di un file di configurazione inserito nella
	 * directory principale del progetto <br/>
	 * Il file dovrà avere la seguente forma: <br/>
	 * username=root						  <br/>
	 * password=vostraPassword				  <br/>
	 * dbname=nomeDBinUso					  
	 * @param configFileName
	 */
	public SQLConnection(String configFileName)
	{
		this.filename = configFileName;
		readConfig();
		
		try
		{
		  String url = "jdbc:mysql://localhost:3306/"+dbname;
		  connection = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e)
		{
			System.out.println("Problemi nella connessione al DB, termino");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	private void readConfig()
	{
		try
		{
			Scanner s = new Scanner(new File(filename));
			Map<String,String> mapped = new HashMap<>();
			
			while(s.hasNextLine())
			{
				String[] parts = s.nextLine().split("=");
				mapped.put(parts[0].trim(), parts[1].trim());
			}
			
			username = mapped.get("username");
			password = mapped.get("password");
			dbname = mapped.get("dbname");
			
			if(username==null || password==null || dbname==null)
			{
				System.out.println("Mancano dati nel file di configurazione, controllalo");
				System.exit(-1);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("ERRORE-TERMINO");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Passate come parametro a questo metodo una query di lettura (SELECT) <br/>
	 * Il metodo restituirà una lista di mappe<String,String> <br/>
	 * Ogni elemento della lista rappresenta una riga della tabella letta<br/>
	 * Ogni elemento contiene come chiavi i nomi delle colonne e come valori il valore di quella cella <br/>
	 * un elemento letto dalla tabella persona avrà ad esempio questa forma: <br/>
	 * {id=0, nome=Stefano, cognome=Rubinetti, eta=27} <br/>
	 *  id, nome, cognome e eta saranno le chiavi della mappa <br/>
	 *  dopo l'= trovate i rispettivi valori
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String,String>> executeQuery(String query)
	{
		try
		{
			List<Map<String,String>> res = new ArrayList<Map<String,String>>();
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next())
			{
				
				Map <String,String> row = new LinkedHashMap <String,String>();
				for(int i=0;i <rs.getMetaData().getColumnCount();i++)
				row.put
				(
					rs.getMetaData().getColumnLabel(i+1).toLowerCase(),
					rs.getString(i+1)
				);
				res.add(row);
			}
			s.close();
			return res;
		}
		catch(Exception e)
		{
			System.out.println("Query sbagliata, leggi errore qui sotto, CARATTERE PER CARATTERE");
			System.out.println("Query: "+query);
			System.out.println("Errore: "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Esegue sul database la DML passata come parametro <br/>
	 * Una DML è un'operazione di scrittura/modifica/cancellazione di una o più righe
	 * @param dml
	 */
	public void executeDML(String dml)
	{
		try
		{
			if(dml.toLowerCase().contains("create") || dml.toLowerCase().contains("drop") || dml.toLowerCase().contains("alter"))
			{
				System.out.println("La query che stai inserendo non è una DML, controlla bene");
				return;
			}	
			Statement s = connection.createStatement();
			s.execute(dml);
			s.close();
		}
		catch(Exception e)
		{
			System.out.println("DML sbagliata, leggi errore qui sotto, CARATTERE PER CARATTERE");
			System.out.println("DML: "+dml);
			System.out.println("Errore: "+e.getMessage());
		}
	}
	
	/**
	 * FOR THE FUTURE, IGNORARE
	 * @param tableName
	 * @param map
	 */
	public void executeInsertByMap(String tableName, Map<String,String> map)
	{
		String sql="";
		try
		{
			Statement s = connection.createStatement();
			
			String valuesName = "(";
			String values = "(";
			
			for(String key : map.keySet())
			{
				valuesName += key+",";
				values     += "'"+map.get(key)+"',";
			}
			
			valuesName = valuesName.substring(0, valuesName.length()-1)+")";
			values = values.substring(0, values.length()-1)+")";
			
			sql = "INSERT INTO "+tableName+" "+valuesName+" VALUES "+values;
			
			s.execute(sql);
			s.close();
		}
		catch(Exception e)
		{
			System.out.println("DML sbagliata, leggi errore qui sotto, CARATTERE PER CARATTERE");
			System.out.println("DML: "+sql);
			System.out.println("Errore: "+e.getMessage());
		}
	}
	
	/**
	 * Esegue sul database la DDL passata come parametro <br/>
	 * Una DDL è un'operazione di creazione/modifica/cancellazione di metadata, come interi db, tabelle, colonne, ecc...
	 * 
	 * @param ddl
	 */
	public void executeDDL(String ddl)
	{
		try
		{
			Statement s = connection.createStatement();
			s.execute(ddl);
			s.close();
		}
		catch(Exception e)
		{
			System.out.println("DDL sbagliata, leggi errore qui sotto, CARATTERE PER CARATTERE");
			System.out.println("DDL: "+ddl);
			System.out.println("Errore: "+e.getMessage());
		}
	}
	
	
	
}
