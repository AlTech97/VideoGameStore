package operations;

import java.util.Scanner;

public class Operations {
	public int menu(Scanner key) {
		System.out.println("Inserisci un numero da 1 a 5 per fare operazioni su:");
		System.out.println("1) Gestore");
		System.out.println("2) Sede d'acquisto");
		System.out.println("3) Cliente");
		System.out.println("4) Prodotto");
		System.out.println("5) Azienda");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
	
	public int opgestore(Scanner key) {
		System.out.println();
		System.out.println("Operazioni: ");
		System.out.println("1) Cambia un gestore ");
		System.out.println("2) Modifica il numero di telefono di un gestore ");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
	
	public int opsedeacquisto(Scanner key) {
		System.out.println();
		System.out.println("Operazioni: ");
		System.out.println("1) Inserisci una nuova sede e un nuovo gestore ");
		System.out.println("2) Cancella una sede d'acquisto e il suo gestore ");
		System.out.println("3) Calcola il numero di fatture emesse da ogni sede che ha emesso almeno una fattura");
		System.out.println("4) Stampa il numero dei prodotti venduti da ogni sede che ha venduto almeno un prodotto: ");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
	
	public int opcliente(Scanner key) {
		System.out.println();
		System.out.println("Operazioni: ");
		System.out.println("1) Inserisci un nuovo cliente ");
		System.out.println("2) Un cliente acquista dei prodotti ");
		System.out.println("3) Stampa le informazioni dei clienti che hanno acquistato almeno un prodotto,"
				+ " compreso il numero dei prodotti comprati da ognuno di questi ");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
	
	public int opprodotto(Scanner key) {
		System.out.println();
		System.out.println("Operazioni: ");
		System.out.println("1) Inserisci un nuovo prodotto ");
		System.out.println("2) Inserisci un nuovo videogioco");
		System.out.println("3) Inserisci una nuova console");
		System.out.println("4) Inserisci un nuovo accessorio");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
	
	public int opazienda(Scanner key) {
		System.out.println();
		System.out.println("Operazioni: ");
		System.out.println("1) Cambia azienda ");
		System.out.println("2) Modifica e-mail di un'azienda ");
		System.out.print("Inserisci: ");
		return key.nextInt();
	}
}