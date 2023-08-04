package com.generation.entity;

public class Libro
{

//	id int PK 
//	titolo text 
//	genere text 
//	anno_uscita int 
//	prezzo_unitario decimal(4,2) 
//	copie_vendute int 
//	id_autore int 
//	id_publisher int
	
			//dichiaro proprietà
	private int id, id_autore, id_publisher;	//chiave primaria e due chiavi esterne(
	

	private int anno_uscita, copie_vendute;
	private double prezzo_unitario;
	private String titolo, genere;
	//riferimento all'oggetto padre --collegamentro diretto
	private Autore autore;
	private Publisher publisher;
	
	//costruttori
	public Libro () {}
	
	public Libro(int id, int id_autore, int id_publisher, int anno_uscita, int copie_vendute, double prezzo_unitario,
			String titolo, String genere) 
	{
		this.id = id;
		this.id_autore = id_autore;
		this.id_publisher = id_publisher;
		this.anno_uscita = anno_uscita;
		this.copie_vendute = copie_vendute;
		this.prezzo_unitario = prezzo_unitario;
		this.titolo = titolo;
		this.genere = genere;
	}
	
	//ho generato getter e setter stupidi -- DUMB
	public Autore getAutore() 
	{
		return autore;
	}

	public void setAutore(Autore autore)
	{
		this.autore = autore;
	}

	public Publisher getPublisher() 
	{
		return publisher;
	}

	public void setPublisher(Publisher publisher) 
	{
		this.publisher = publisher;
	}
	
	public int getId()
	{
		return id;
	}
	//Java lavora con il principio di località delle variabili 
	//in caso di variabili omonime vince quella più specifica 
	//THIS è una parola chiave che indica l'oggetto in cui siamo
	//quando invochiamo un metodo su un oggetto 'this' prende il valore dell'oggetto
	//è sempre messo implicitamente quando richiamate proprietà o metodi dichiarati dentro la classe stessa
	public void setId(int id) 
	{
		this.id = id;
	}
	//Libro l = new Libro();
	//l.getId_autore();  
	public int getId_autore() 
	{
		return this.id_autore;
	}
	
	public void setId_autore(int id_autore)
	{
		this.id_autore = id_autore;
	}
	
	public int getId_publisher() 
	{
		return id_publisher;
	}
	
	public void setId_publisher(int id_publisher) 
	{
		this.id_publisher = id_publisher;
	}
	
	public int getAnno_uscita() {
		return anno_uscita;
	}
	
	public void setAnno_uscita(int anno_uscita) 
	{
		this.anno_uscita = anno_uscita;
	}
	
	public int getCopie_vendute()
	{
		return copie_vendute;
	}
	
	public void setCopie_vendute(int copie_vendute)
	{
		this.copie_vendute = copie_vendute;
	}
	
	public double getPrezzo_unitario() 
	{
		return prezzo_unitario;
	}
	
	public void setPrezzo_unitario(double prezzo_unitario) 
	{
		this.prezzo_unitario = prezzo_unitario;
	}
	
	public String getTitolo() 
	{
		return titolo;
	}
	
	public void setTitolo(String titolo) 
	{
		this.titolo = titolo;
	}
	
	public String getGenere() 
	{
		return genere;
	}
	
	public void setGenere(String genere)
	{
		this.genere = genere;
	}

	//toString() è la rappesentazione grafica veloc che serve a programmatori
	//per fare debug e/o vedere lo stato di oggetto
	
	public String toString() {
		return "{id:'" + id + "', id_autore:'" + id_autore + "', id_publisher:'" + id_publisher + "', anno_uscita:'"
				+ anno_uscita + "', copie_vendute:'" + copie_vendute + "', prezzo_unitario:'" + prezzo_unitario
				+ "', titolo:'" + titolo + "', genere:'" + genere + "'}";
	}

	
	
}
