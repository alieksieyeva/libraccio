package com.generation.library;

import java.util.Scanner;

/**
 * Questa classe offre i metodi di base per comunicare con l'utente.
 * @author Ferdinando Primerano, 2023
 *
 */
public class Console 
{
	private static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * Il metodo print riceve qualunque cosa e la stampa. <br />
	 * print("Ferdinando") stamperà Ferdinando <br />
	 * print(3) stamperà 3 <br />
	 * print(4+5) stamperà 9 <br />
	 * @param obj (la cosa da stampare)
	 * @author Ferdinando Primerano, 2023
	 */
	public static void print(Object obj)
	{
		System.out.println(obj.toString());		
	}
	
	/**
	 * Il programma si ferma e chiede all'utente di inserire del testo. <br />
	 * Il testo viene memorizzato in una variabile di tipo String o usato in un qualche altro modo. <br />
	 * Esempio: String name = readString();, l'utente inserisce Ferdinando e ora name "contiene" Ferdinando <br />
	 * @return il valore inserito dall'utente <br />
	 */
	public static String readString()
	{
		return keyboard.nextLine();
	}
	
	/**
	 * Il programma si ferma e chiede all'utente di inserire un numero intero. <br />
	 * Il testo viene memorizzato in una variabile di tipo int o double o usato in un qualche altro modo. <br />
	 * Esempio: int age = readInt();, l'utente inserisce 43 e ora age contiene 43. <br />
	 * Attenzione: se l'utente non dovesse inserire un valore numerico il programma andrebbe in crash. <br />
	 * @return il valore inserito dall'utente
	 */
	public static int readInt()
	{
		try
		{
			return Integer.parseInt(keyboard.nextLine());
		}
		catch(Exception e)
		{
			System.out.println("Ci aspettavamo un numero intero. E' stato inserito un valore non numerico o non intero - assicurarsi di non avere spazi o punti. Termino.");			
			System.exit(-1);
			return 0;
		}
	}

	/**
	 * Il programma si ferma e chiede all'utente di inserire un numero POTENZIALMENTE con la virgola. <br />
	 * Il testo viene memorizzato in una variabile di tipo double o usato in un qualche altro modo. <br />
	 * Esempio: int age = readInt();, l'utente inserisce 43 e ora age contiene 43. <br />
	 * Attenzione: se l'utente non dovesse inserire un valore numerico il programma andrebbe in crash. <br />
	 * @return il valore inserito dall'utente
	 */
	public static double readDouble()
	{
		try
		{
			return Double.parseDouble(keyboard.nextLine());
		}
		catch(Exception e)
		{
			System.out.println("Ci aspettavamo un numero. E' stato inserito un valore non numerico o mal formattato. Assicurarsi di non avere spazi e ricordarsi che si usa il punto, non la virgola, per i decimali. Termino.");			
			System.exit(-1);
			return 0;
		}
	}
	
	
	
}
