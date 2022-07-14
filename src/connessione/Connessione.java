package connessione;

import java.sql.*;
import java.util.Scanner;
import operations.Operations;


public class Connessione {

	

	public static void main(String[] args)  {
		Connection con = null;
		Statement st=null;
		ResultSet rs=null;
		String deletegcf = null;
		String delcittà = null;
		String custom_cf = null;
		String azname= null;
		String azaddr= null;
		String go_on= null;
		
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://localhost:3306/VG_Stores";
		con = DriverManager.getConnection(url,"root", "debian");
		System.out.println("Connessione OK \n");
		
		}
		catch(Exception e) {
			System.out.println("Connessione Fallita \n");
			System.out.println(e);
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		}
		catch(ClassNotFoundException e) {
			System.err.print("ClassNotFoundException:" + e.getMessage());
		}
		
		try {
			 Scanner continues= new Scanner(System.in); 
			 go_on="y";
			 while(go_on.equals("y")) {
			 
			 st= con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					 ResultSet.CONCUR_READ_ONLY);
			 
			
			 Scanner key= new Scanner(System.in); 
			 Operations op=new Operations();
			 int choice= op.menu(key);
				 
			 if (choice==1) {
			  int choiceop= op.opgestore(key);
			 
			  if(choiceop==1) { 
			  Scanner board= new Scanner(System.in);
			  String sql= "SELECT * FROM gestore";
			  rs=st.executeQuery(sql);
			  System.out.println();
			  System.out.println("Elenco gestori attuali:");
			  System.out.println("Codice Fiscale, Nome Cognome, Data di nascita");
			  while(rs.next()) {
				  String gcf = rs.getString("g_cf");
				  String gnome= rs.getString("g_nome");
				  String gcognome= rs.getString("g_cognome");
				  String bdate= rs.getString("g_birthdate");
				  System.out.printf("%s, %s %s, %s\n", gcf,gnome,gcognome,bdate);
				  
			  }
			  System.out.println();
			  
			  System.out.println("Inserisci il codice fiscale del gestore da cambiare:"); 
			  String oldcf= board.nextLine();
			  System.out.println("Inserisci in ordine, separando con invio: codice fiscale, nome, cognome e data di nascita del nuovo gestore:"); 
			  String cf=board.nextLine();
			  String name=board.nextLine();
			  String surname=board.nextLine();
			  String birthdate=board.nextLine(); 
			  String usql = "UPDATE gestore SET g_cf='"+cf+"', g_nome='"+name+"', g_cognome='"+surname+"', g_birthdate='"+birthdate+"' WHERE g_cf='"+oldcf+"'";
			  int res = st.executeUpdate(usql);
			  System.out.println();
			  String sql1="SELECT * FROM gestore";
			  rs= st.executeQuery(sql1);
			  System.out.println("Elenco aggiornato gestori: ");
			  System.out.println("Codice Fiscale, Nome Cognome, Data di nascita");
			 while (rs.next()) {
		        String gescf= rs.getString("g_cf");
				String gesname= rs.getString("g_nome");
				String gessurname= rs.getString("g_cognome");
				String bdate= rs.getString("g_birthdate");
				System.out.printf("%s, %s %s, %s\n",gescf,gesname,gessurname,bdate);
			 }
			}
			
			  else if(choiceop==2) {
				  Scanner board= new Scanner(System.in);
				  String sql= "SELECT * FROM telefono";
				  rs=st.executeQuery(sql);
				  System.out.println();
				  System.out.println("Elenco attuale numeri di telefono:");
				  System.out.println("Cod. fiscale del gestore, Numero di telefono");
				  while(rs.next()) {
					  String gcf = rs.getString("ges_cf_tel");
					  String number= rs.getString("numero");
					  System.out.printf("%s, %s\n", gcf, number);
				  }
				  System.out.println();
				  
				  System.out.println("Inserisci il numero di telefono da cambiare :"); 
				  String oldnumber= board.nextLine();
				  System.out.println("Inserisci il nuovo numero:"); 
				  String newnumber=board.nextLine();
				  String usql = "UPDATE telefono SET numero='"+newnumber+"' WHERE numero='"+oldnumber+"'";
				  int res = st.executeUpdate(usql);
				  
				  System.out.println();
				  String sql1="SELECT * FROM telefono";
				  rs= st.executeQuery(sql1);
				  System.out.println("Elenco aggiornato numeri di telefono:");
				  System.out.println("Cod. fiscale del gestore, Numero di telefono");
				  while(rs.next()) {
					  String cfg = rs.getString("ges_cf_tel");
					  String tnumber= rs.getString("numero");
					  System.out.printf("%s, %s\n", cfg, tnumber);
				  }
			}
		 }
		 else if (choice==2) {
			 int choiceop= op.opsedeacquisto(key);
			 
			 if(choiceop==1) { 
				  Scanner board= new Scanner(System.in);
				  String sql= "SELECT * FROM sede_acquisto";
				  rs=st.executeQuery(sql);
				  System.out.println();
				  System.out.println("Elenco attuale delle sedi d'acquisto:");
				  System.out.println("ID sede, Nome, Cod. Fiscale gestore, Indirizzo sede.");
				  while(rs.next()) {
					  String id = rs.getString("id");
					  String snome= rs.getString("sede_nome");
					  String scf= rs.getString("s_cf");
					  String indirizzo = rs.getString("indirizzo_sede");
					  System.out.printf("%s, %s, %s, %s\n", id,snome,scf, indirizzo);
				  }
				  System.out.println();
				  
				  System.out.println("Inserisci in ordine, separando con invio: codice fiscale, nome, cognome e data di nascita del nuovo gestore: ");
				  String newgescf= board.nextLine();
				  String newgesname= board.nextLine();
				  String newgessurname= board.nextLine();
				  String newgesbdate= board.nextLine();
				  String usql = "INSERT INTO gestore VALUES( '"+newgescf+"', '"+newgesname+"', '"+newgessurname+"', '"+newgesbdate+"')";
				  int res = st.executeUpdate(usql);

				  
				  System.out.println("Inserisci quanti numeri di telefono ha il gestore: ");
				  Scanner numkey= new Scanner(System.in);
				  int num = numkey.nextInt();
				  System.out.println();
				  int i=1;
				  while (i<=num) {
					  System.out.println("Inserisci numero:");
					  String newnumber= board.nextLine();
					  String usql1 = "INSERT INTO telefono VALUES ('"+newnumber+"', '"+newgescf+"')";
					  int res1 = st.executeUpdate(usql1);
					  i++;
					 }
				  
				  System.out.println("Inserisci in ordine, separando con invio: id numerico, nome e indirizzo della nuova sede: "); 
				  int newid= numkey.nextInt();
				  String newname= board.nextLine();
				  String newaddress= board.nextLine();
				  
				  String usql2 = "INSERT INTO sede_acquisto VALUES( '"+newid+"', '"+newname+"', '"+newgescf+"', '"+newaddress+"')";
				  int res2 = st.executeUpdate(usql2);
				  
				  System.out.println();
				  
				  String sql1= "SELECT * FROM sede_acquisto";
				  rs=st.executeQuery(sql1);
				  System.out.println();
				  System.out.println("Elenco aggiornato sedi:");
				  System.out.println("ID sede, Nome, Cod. Fiscale gestore, Indirizzo sede.");
				  while(rs.next()) {
					  String id= rs.getString("id");
					  String snome= rs.getString("sede_nome");
					  String scf= rs.getString("s_cf");
					  String saddress= rs.getString("indirizzo_sede");
					  System.out.printf("%s, %s, %s, %s\n", id,snome,scf,saddress);
				  }
				  
			  }
			 else if(choiceop==2) {
				 Scanner board= new Scanner(System.in);
				  String sql= "SELECT * FROM sede_acquisto";
				  rs=st.executeQuery(sql);
				  System.out.println();
				  System.out.println("Elenco attuale delle sedi d'acquisto:");
				  System.out.println("ID sede, Nome, Cod. Fiscale gestore, Indirizzo sede.");
				  while(rs.next()) {
					  String id = rs.getString("id");
					  String snome= rs.getString("sede_nome");
					  String scf= rs.getString("s_cf");
					  String sindirizzo= rs.getString("indirizzo_sede");
					  System.out.printf("%s, %s, %s, %s\n", id,snome,scf,sindirizzo);
				  }
				  System.out.println();
				  Scanner numkey=new Scanner(System.in);
				  System.out.println("Inserisci id numerico della sede da eliminare:"); 
				  int deleteid=numkey.nextInt();
				  
				  String sql2= "SELECT s_cf FROM sede_acquisto WHERE id='"+deleteid+"'";
				  rs=st.executeQuery(sql2);
				  while(rs.next()) {
					  deletegcf = rs.getString("s_cf");
				  }
				  String usql1= "DELETE FROM telefono WHERE ges_cf_tel='"+deletegcf+"'";
				  int res1 = st.executeUpdate(usql1);
				  				  
				  String usql2= "DELETE FROM sede_acquisto WHERE id='"+deleteid+"'";
				  int res2 = st.executeUpdate(usql2);
					
				  String usql3= "DELETE FROM gestore WHERE g_cf='"+deletegcf+"'";
				  int res3 = st.executeUpdate(usql3);
									  
				  String sql4= "SELECT * FROM sede_acquisto";
				  rs=st.executeQuery(sql4);
				  System.out.println();
				  System.out.println("Elenco aggiornato delle sedi d'acquisto:");
				  System.out.println("ID sede, Nome, Cod. Fiscale gestore, Indirizzo sede.");
				  while(rs.next()) {
					  String id = rs.getString("id");
					  String snome= rs.getString("sede_nome");
					  String scf= rs.getString("s_cf");
					  String indirizzosede= rs.getString("indirizzo_sede");
					  System.out.printf("%s, %s, %s, %s\n", id,snome,scf,indirizzosede);
				  }
				 
				 }
			 
			 else if(choiceop==3) {
				 String sql= "SELECT COUNT(*) AS NumeroFatture, id FROM fattura, sede_acquisto WHERE fattura_id_sede=id GROUP BY id HAVING NumeroFatture > 0";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("id, n° di fatture");
				  while(rs.next()) {
					  int numfat = rs.getInt("NumeroFatture");
					  int numid = rs.getInt("id");
					  System.out.printf("%d,     %d\n", numid, numfat);
				  }
			    }
			else if(choiceop==4) {
				String sql= "SELECT COUNT(*) AS NumeroProd, id FROM prodotto, sede_acquisto WHERE prod_id_sede=id GROUP BY id HAVING NumeroProd > 0";
				rs=st.executeQuery(sql);
				System.out.println();
				System.out.println("id, n° di prodotti venduti");
				 while(rs.next()) {
					  int numprod = rs.getInt("NumeroProd");
					  int idshop = rs.getInt("id");
					  System.out.printf("%d,     %d\n", idshop, numprod);
				  }
				
			    }
				
		 }
		 else if(choice==3) {
			 int choiceop=op.opcliente(key);
			 
			 if(choiceop==1) {
				 Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Lista dei clienti:");
				 System.out.println("Cod. fisc, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
					  }
				  
				 System.out.println();
				 System.out.println("Inserisci in ordine, separando con invio: cod. fiscale, nome e cognome del nuovo cliente:");
				 String newcustcf= board.nextLine();
				 String newcustname= board.nextLine();
				 String newcustsurname= board.nextLine();
				 String usql = "INSERT INTO cliente VALUES( '"+newcustcf+"', '"+newcustname+"', '"+newcustsurname+"')";
				 int res = st.executeUpdate(usql);
				  
				 String sql1= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql1);
				 System.out.println();
				 System.out.println("Lista aggiornata clienti:");
				 System.out.println("Cod. fisc, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
				  }  
			 }
			 
			 else if(choiceop==2) {
				 Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Lista dei clienti");
				 System.out.println("Cod. fisc, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
					  }
				 
				 String sql1= "SELECT * FROM azienda";
				 rs=st.executeQuery(sql1);
				 System.out.println();
				 System.out.println("Lista aziende produttrici:");
				 System.out.println("Nome, indirizzo, e-mail");
				 while(rs.next()) {
					  String nameaz= rs.getString("azienda_nome");
					  String addressaz= rs.getString("azienda_indirizzo");
					  String azemail=rs.getString("e_mail");
					  System.out.printf("%s, %s, %s\n",nameaz, addressaz, azemail);
				 }
				 
				 Scanner keynum= new Scanner(System.in);
				 System.out.println();
				 System.out.println("Inserisci codice fiscale del cliente che acquista i prodotti:");
				 String cs_cf=board.nextLine();
				 
				 String sql2= "SELECT c_cf FROM cliente WHERE c_cf='"+cs_cf+"'";
				 rs=st.executeQuery(sql2);
				 while(rs.next()) {
					  custom_cf = rs.getString("c_cf");
				 } 
				 
				 System.out.println("Inserisci il numero di prodotti acquistati:");
				 int numprod=keynum.nextInt();
				 int count=1;
				 while(count<=numprod) {
					 System.out.println("Inserisci, separando con invio, in ordine: il codice alfanumerico del prodotto, il nome,"
					 		+ " il prezzo (intero,decimale), il numero di fattura, data della fattura(formato aaaa-mm-gg),\n id numerico della sede d'acquisto,"
					 		+ " nome e indirizzo dell'azienda che l'ha realizzato:");
					 String prodcod=board.nextLine();
					 String prodname=board.nextLine();
					 double prodprice=keynum.nextDouble();
					 String prodnumfat=board.nextLine();
					 String datefat=board.nextLine();
					 int prodid=keynum.nextInt();
					 String prodazname=board.nextLine();
					 String prodazaddr=board.nextLine();
					
					 String usql = "INSERT INTO fattura VALUES('"+prodnumfat+"','"+prodid+"', '"+datefat+"')";
					 int res = st.executeUpdate(usql);
					 String sql4= "SELECT * FROM azienda WHERE azienda_nome='"+prodazname+"' AND '"+prodazaddr+"'";
					 rs=st.executeQuery(sql4);
					 while(rs.next()) {
						  azname = rs.getString("azienda_nome");
						  azaddr= rs.getString("azienda_indirizzo");
						  String email=rs.getString("e_mail");
						  //System.out.printf("%s %s %s\n",azname, azaddr, email);
					  }
					 if((azname==null) || (azaddr==null)) {
						 System.out.println("Azienda non presente nel database. Aggiungere email dell'azienda:");
						 String emailaz= board.nextLine();
						 String usql2="INSERT INTO azienda VALUES('"+prodazname+"','"+prodazaddr+"', '"+emailaz+"')";
						 int res2= st.executeUpdate(usql2);
					 }
					 String usql1 = "INSERT INTO prodotto VALUES('"+prodcod+"','"+prodname+"', '"+prodprice+"','"+prodnumfat+"','"+prodid+"','"+custom_cf+"','"+prodazname+"','"+prodazaddr+"')";
					 int res1= st.executeUpdate(usql1);
					 count++; 
				 }
				    
				 String sql3= "SELECT c_cf, prod_codice FROM cliente, prodotto WHERE c_cf='"+custom_cf+"' AND prod_cf_cliente=c_cf";
				 rs=st.executeQuery(sql3);
				 System.out.println();
				 System.out.println("Lista prodotti che ha acquistato questo cliente: ");
				 System.out.println("Cod. fiscale, cod. prodotto");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String codprod= rs.getString("prod_codice");
					  System.out.printf("%s, %s\n",cf_customer, codprod);
				  }  
				 
			 }
		else if(choiceop==3) {
			  String sql= "SELECT c_cf, c_nome , c_cognome, COUNT(*) AS NumProd FROM cliente, prodotto WHERE c_cf=prod_cf_cliente GROUP BY c_cf HAVING NumProd > 0";
			  rs=st.executeQuery(sql);
			  System.out.println();
			  System.out.println("Cod. fiscale, nome cognome, num. prodotti acquistati");
			  while(rs.next()) {
				  String cf_ctm = rs.getString("c_cf");
				  String name_ctm= rs.getString("c_nome");
				  String surname_ctm = rs.getString("c_cognome");
				  int numprod_ctm = rs.getInt("NumProd");
				  System.out.printf("%s, %s %s, %d\n",cf_ctm, name_ctm, surname_ctm, numprod_ctm);
			  }
			} 
			 
		 }
		 else if(choice==4) {
			 int choiceop=op.opprodotto(key);
			 
			 if (choiceop==1) {
				 Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Elenco dei clienti");
				 System.out.println("Cod. fiscale, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
					  }
				 
				 String sql1= "SELECT * FROM azienda";
				 rs=st.executeQuery(sql1);
				 System.out.println();
				 System.out.println("Elenco aziende produttrici");
				 System.out.println("Nome, indirizzo, e-mail");
				 while(rs.next()) {
					  String nameaz= rs.getString("azienda_nome");
					  String addressaz= rs.getString("azienda_indirizzo");
					  String azemail=rs.getString("e_mail");
					  System.out.printf("%s, %s, %s\n",nameaz, addressaz, azemail);
				 }
				 
				 Scanner keynum= new Scanner(System.in);
				 System.out.println();
				 System.out.println("Inserisci codice fiscale del cliente che acquista i prodotti:");
				 String cs_cf=board.nextLine();
				 
				 String sql2= "SELECT c_cf FROM cliente WHERE c_cf='"+cs_cf+"'";
				 rs=st.executeQuery(sql2);
				 while(rs.next()) {
					  custom_cf = rs.getString("c_cf");
				 } 
				 
				 System.out.println("Inserisci il numero di prodotti acquistati:");
				 int numprod=keynum.nextInt();
				 int count=1;
				 while(count<=numprod) {
					 System.out.println("Inserisci in ordine, separando con invio: il codice alfanumerico del prodotto, il nome,"
					 		+ " il prezzo (intero,decimale), il numero di fattura, data della fattura (formato aaaa-mm-gg),\n id numerico della sede d'acquisto,"
					 		+ " nome e indirizzo dell'azienda che l'ha realizzato:");
					 String prodcod=board.nextLine();
					 String prodname=board.nextLine();
					 double prodprice=keynum.nextDouble();
					 String prodnumfat=board.nextLine();
					 String datefat=board.nextLine();
					 int prodid=keynum.nextInt();
					 String prodazname=board.nextLine();
					 String prodazaddr=board.nextLine();
					
					 String usql = "INSERT INTO fattura VALUES('"+prodnumfat+"','"+prodid+"', '"+datefat+"')";
					 int res = st.executeUpdate(usql);
					 String sql4= "SELECT * FROM azienda WHERE azienda_nome='"+prodazname+"' AND '"+prodazaddr+"'";
					 rs=st.executeQuery(sql4);
					 while(rs.next()) {
						  azname = rs.getString("azienda_nome");
						  azaddr= rs.getString("azienda_indirizzo");
						  String email=rs.getString("e_mail");
						 // System.out.printf("%s %s %s\n",azname, azaddr, email);
					  }
					 if((azname==null) || (azaddr==null)) {
						 System.out.println("Azienda non presente nel db . Aggiungere email dell'azienda:");
						 String emailaz= board.nextLine();
						 String usql2="INSERT INTO azienda VALUES('"+prodazname+"','"+prodazaddr+"', '"+emailaz+"')";
						 int res2= st.executeUpdate(usql2);
					 }
					 String usql1 = "INSERT INTO prodotto VALUES('"+prodcod+"','"+prodname+"', '"+prodprice+"','"+prodnumfat+"','"+prodid+"','"+custom_cf+"','"+prodazname+"','"+prodazaddr+"')";
					 int res1= st.executeUpdate(usql1);
					 count++; 
				 }
				    
				 String sql3= "SELECT c_cf, prod_codice FROM cliente, prodotto WHERE c_cf='"+custom_cf+"' AND prod_cf_cliente=c_cf";
				 rs=st.executeQuery(sql3);
				 System.out.println();
				 System.out.println("Elenco prodotti acquistati dal cliente");
				 System.out.println("Cod. fiscale, cod. prodotto");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String codprod= rs.getString("prod_codice");
					  System.out.printf("%s %s\n",cf_customer, codprod);
				  }  
			   }
			 else if (choiceop==2) { //acquista un videogioco
				 Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Lista clienti:");
				 System.out.println("Cod. fiscale, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
					  }
				 
				 String sql1= "SELECT * FROM azienda";
				 rs=st.executeQuery(sql1);
				 System.out.println();
				 System.out.println("Lista aziende produttrici:");
				 System.out.println("Nome, indirizzo, e-mail");
				 while(rs.next()) {
					  String nameaz= rs.getString("azienda_nome");
					  String addressaz= rs.getString("azienda_indirizzo");
					  String azemail=rs.getString("e_mail");
					  System.out.printf("%s, %s, %s\n",nameaz, addressaz, azemail);
				 }
				 
				 Scanner keynum= new Scanner(System.in);
				 System.out.println();
				 System.out.println("Inserisci codice fiscale del cliente che acquista i prodotti:");
				 String cs_cf=board.nextLine();
				 
				 String sql2= "SELECT c_cf FROM cliente WHERE c_cf='"+cs_cf+"'";
				 rs=st.executeQuery(sql2);
				 while(rs.next()) {
					  custom_cf = rs.getString("c_cf");
				 } 
				 
				 System.out.println("Inserisci il numero di prodotti acquistati:");
				 int numprod=keynum.nextInt();
				 int count=1;
				 while(count<=numprod) {
					 System.out.println("Inserisci in ordine, separando con invio: il codice alfanumerico del prodotto, il nome,"
					 		+ "il prezzo (intero,decimale), il numero di fattura, data della fattura (formato: aaaa-mm-gg),\n id numerico della sede d'acquisto,"
					 		+ "nome e indirizzo dell'azienda che l'ha realizzato:");
					 String prodcod=board.nextLine();
					 String prodname=board.nextLine();
					 double prodprice=keynum.nextDouble();
					 String prodnumfat=board.nextLine();
					 String datefat=board.nextLine();
					 int prodid=keynum.nextInt();
					 String prodazname=board.nextLine();
					 String prodazaddr=board.nextLine();
					
					 String usql = "INSERT INTO fattura VALUES('"+prodnumfat+"','"+prodid+"', '"+datefat+"')";
					 int res = st.executeUpdate(usql);
					 String sql4= "SELECT * FROM azienda WHERE azienda_nome='"+prodazname+"' AND '"+prodazaddr+"'";
					 rs=st.executeQuery(sql4);
					 while(rs.next()) {
						  azname = rs.getString("azienda_nome");
						  azaddr= rs.getString("azienda_indirizzo");
						  String email=rs.getString("e_mail");
						  //System.out.printf("%s %s %s\n",azname, azaddr, email);
					  }
					 if((azname==null) || (azaddr==null)) {
						 System.out.println("Azienda non presente nel database. Aggiungere email dell'azienda:");
						 String emailaz= board.nextLine();
						 String usql2="INSERT INTO azienda VALUES('"+prodazname+"','"+prodazaddr+"', '"+emailaz+"')";
						 int res2= st.executeUpdate(usql2);
					 }
					 String usql1 = "INSERT INTO prodotto VALUES('"+prodcod+"','"+prodname+"', '"+prodprice+"','"+prodnumfat+"','"+prodid+"','"+custom_cf+"','"+prodazname+"','"+prodazaddr+"')";
					 int res1= st.executeUpdate(usql1);
					 System.out.println();
					 System.out.println("Inserisci il nome della piattaforma per cui è stato rilasciato il videogioco:");
					 String platform=board.nextLine();
					 String usql2 = "INSERT INTO videogiochi VALUES('"+prodcod+"','"+platform+"')";
					 int res2= st.executeUpdate(usql2);
					 
					 System.out.println();
					 System.out.println("Inserisci a quanti generi appartiene il videogioco: " );
					 int numgen=keynum.nextInt();
					 int numcount=1;
					 while(numcount<=numgen) {
						 System.out.println("Inserisci un genere:");
						 String category= board.nextLine();
						 String usql3 = "INSERT INTO categoria VALUES('"+category+"','"+prodcod+"')";
						 int res3= st.executeUpdate(usql3);	 
						 numcount++;
					 }
					 
					 count++;
				 }
				 
				    
				 String sql3= "SELECT c_cf, vg_codice, genere FROM cliente, prodotto, videogiochi, categoria WHERE c_cf='"+custom_cf+"' AND prod_cf_cliente=c_cf AND vg_codice=prod_codice AND vg_codice=cat_codice_vg";
				 rs=st.executeQuery(sql3);
				 System.out.println();
				 System.out.println("Lista videogiochi acquistati dal cliente");
				 System.out.println("Cod. fiscale, cod. videogioco, genere videogioco");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String codvgprod= rs.getString("vg_codice");
					  String genre=rs.getString("genere");
					  System.out.printf("%s, %s, %s\n",cf_customer, codvgprod, genre);
				  }  
			 }
		  else if(choiceop==3) {
			  Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM cliente";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Lista clienti:");
				 System.out.println("Cod. fiscale, nome cognome");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String customer_name= rs.getString("c_nome");
					  String customer_surname= rs.getString("c_cognome");
					  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
					  }
				 
				 String sql1= "SELECT * FROM azienda";
				 rs=st.executeQuery(sql1);
				 System.out.println();
				 System.out.println("Lista aziende produttrici:");
				 System.out.println("Nome, indirizzo, e-mail");
				 while(rs.next()) {
					  String nameaz= rs.getString("azienda_nome");
					  String addressaz= rs.getString("azienda_indirizzo");
					  String azemail=rs.getString("e_mail");
					  System.out.printf("%s, %s, %s\n",nameaz, addressaz, azemail);
				 }
				 
				 Scanner keynum= new Scanner(System.in);
				 System.out.println();
				 System.out.println("Inserisci cod. fiscale del cliente che acquista i prodotti:");
				 String cs_cf=board.nextLine();
				 
				 String sql2= "SELECT c_cf FROM cliente WHERE c_cf='"+cs_cf+"'";
				 rs=st.executeQuery(sql2);
				 while(rs.next()) {
					  custom_cf = rs.getString("c_cf");
				 } 
				 
				 System.out.println("Inserisci il numero di prodotti acquistati :");
				 int numprod=keynum.nextInt();
				 int count=1;
				 while(count<=numprod) {
					 System.out.println("Inserisci in ordine, separando con invio: il codice alfanumerico del prodotto, il nome,"
					 		+ "il prezzo (intero,decimale), il numero di fattura, data della fattura (formato aaaa-mm-gg),\n id numerico della sede d'acquisto,"
					 		+ "nome e indirizzo dell'azienda che l'ha realizzato:");
					 String prodcod=board.nextLine();
					 String prodname=board.nextLine();
					 double prodprice=keynum.nextDouble();
					 String prodnumfat=board.nextLine();
					 String datefat=board.nextLine();
					 int prodid=keynum.nextInt();
					 String prodazname=board.nextLine();
					 String prodazaddr=board.nextLine();
					
					 String usql = "INSERT INTO fattura VALUES('"+prodnumfat+"','"+prodid+"', '"+datefat+"')";
					 int res = st.executeUpdate(usql);
					 String sql4= "SELECT * FROM azienda WHERE azienda_nome='"+prodazname+"' AND '"+prodazaddr+"'";
					 rs=st.executeQuery(sql4);
					 while(rs.next()) {
						  azname = rs.getString("azienda_nome");
						  azaddr= rs.getString("azienda_indirizzo");
						  String email=rs.getString("e_mail");
						 // System.out.printf("%s %s %s\n",azname, azaddr, email);
					  }
					 if((azname==null) || (azaddr==null)) {
						 System.out.println("Azienda non presente nel database. Aggiungere email dell'azienda:");
						 String emailaz= board.nextLine();
						 String usql2="INSERT INTO azienda VALUES('"+prodazname+"','"+prodazaddr+"', '"+emailaz+"')";
						 int res2= st.executeUpdate(usql2);
					 }
					 String usql1 = "INSERT INTO prodotto VALUES('"+prodcod+"','"+prodname+"', '"+prodprice+"','"+prodnumfat+"','"+prodid+"','"+custom_cf+"','"+prodazname+"','"+prodazaddr+"')";
					 int res1= st.executeUpdate(usql1);
					 
					 System.out.println();
					 System.out.println("Inserisci il modello della console: ");
					 String model=board.nextLine();
					 String usql2 = "INSERT INTO console VALUES('"+prodcod+"','"+model+"')";
					 int res2= st.executeUpdate(usql2);
					 
					 count++; 
				 }
				    
				 String sql3= "SELECT c_cf, prod_codice, console_modello FROM cliente, prodotto, console WHERE c_cf='"+custom_cf+"' AND prod_cf_cliente=c_cf AND prod_codice=console_codice";
				 rs=st.executeQuery(sql3);
				 System.out.println();
				 System.out.println("Lista console acquistate dall'utente:");
				 System.out.println("Cod. fiscale, Cod. prodotto, modello console");
				 while(rs.next()) {
					  String cf_customer = rs.getString("c_cf");
					  String codprod= rs.getString("prod_codice");
					  String consolemodel=rs.getString("console_modello");
					  System.out.printf("%s, %s, %s\n",cf_customer, codprod, consolemodel);
				  }  
			 }
	     else if(choiceop==4) {
	    	 Scanner board= new Scanner(System.in);
			 String sql= "SELECT * FROM cliente";
			 rs=st.executeQuery(sql);
			 System.out.println();
			 System.out.println("Lista clienti:");
			 System.out.println("Cod. fiscale, nome cognome");
			 while(rs.next()) {
				  String cf_customer = rs.getString("c_cf");
				  String customer_name= rs.getString("c_nome");
				  String customer_surname= rs.getString("c_cognome");
				  System.out.printf("%s, %s %s\n",cf_customer, customer_name, customer_surname);
				  }
			 
			 String sql1= "SELECT * FROM azienda";
			 rs=st.executeQuery(sql1);
			 System.out.println();
			 System.out.println("Lista aziende produttrici:");
			 System.out.println("Nome, indirizzo, e-mail");
			 while(rs.next()) {
				  String nameaz= rs.getString("azienda_nome");
				  String addressaz= rs.getString("azienda_indirizzo");
				  String azemail=rs.getString("e_mail");
				  System.out.printf("%s, %s, %s\n",nameaz, addressaz, azemail);
			 }
			 
			 Scanner keynum= new Scanner(System.in);
			 System.out.println();
			 System.out.println("Inserisci codice fiscale del cliente che acquista i prodotti:");
			 String cs_cf=board.nextLine();
			 
			 String sql2= "SELECT c_cf FROM cliente WHERE c_cf='"+cs_cf+"'";
			 rs=st.executeQuery(sql2);
			 while(rs.next()) {
				  custom_cf = rs.getString("c_cf");
			 } 
			 
			 System.out.println("Inserisci il numero di prodotti acquistati:");
			 int numprod=keynum.nextInt();
			 int count=1;
			 while(count<=numprod) {
				 System.out.println("Inserisci in ordine, separando con invio: il codice alfanumerico del prodotto, il nome,"
				 		+ "il prezzo (intero,decimale), il numero di fattura, data della fattura (formato aaaa-mm-gg) , id numerico della sede d'acquisto,"
				 		+ "nome e indirizzo dell'azienda che l'ha realizzato:");
				 String prodcod=board.nextLine();
				 String prodname=board.nextLine();
				 double prodprice=keynum.nextDouble();
				 String prodnumfat=board.nextLine();
				 String datefat=board.nextLine();
				 int prodid=keynum.nextInt();
				 String prodazname=board.nextLine();
				 String prodazaddr=board.nextLine();
				
				 String usql = "INSERT INTO fattura VALUES('"+prodnumfat+"','"+prodid+"', '"+datefat+"')";
				 int res = st.executeUpdate(usql);
				 String sql4= "SELECT * FROM azienda WHERE azienda_nome='"+prodazname+"' AND '"+prodazaddr+"'";
				 rs=st.executeQuery(sql4);				 
				 while(rs.next()) {
					  azname = rs.getString("azienda_nome");
					  azaddr= rs.getString("azienda_indirizzo");
					  String email=rs.getString("e_mail");
					 // System.out.printf("%s %s %s\n",azname, azaddr, email);
				  }
				 if((azname==null) || (azaddr==null)) {
					 System.out.println("Azienda non presente nel database. Aggiungere email dell'azienda:");
					 String emailaz= board.nextLine();
					 String usql2="INSERT INTO azienda VALUES('"+prodazname+"','"+prodazaddr+"', '"+emailaz+"')";
					 int res2= st.executeUpdate(usql2);
				 }
				 String usql1 = "INSERT INTO prodotto VALUES('"+prodcod+"','"+prodname+"', '"+prodprice+"','"+prodnumfat+"','"+prodid+"','"+custom_cf+"','"+prodazname+"','"+prodazaddr+"')";
				 int res1= st.executeUpdate(usql1);
				 
				 System.out.println();
				 System.out.println("Inserisci il modello dell'accessorio: ");
				 String acmodel=board.nextLine();
				 String usql2 = "INSERT INTO accessori VALUES('"+prodcod+"','"+acmodel+"')";
				 int res2= st.executeUpdate(usql2);
				 
				 count++; 
			 }
			    
			 String sql3= "SELECT c_cf, prod_codice, accessori_modello FROM cliente, prodotto, accessori WHERE c_cf='"+custom_cf+"' AND prod_cf_cliente=c_cf AND prod_codice=accessori_codice";
			 rs=st.executeQuery(sql3);
			 System.out.println();
			 System.out.println("lista accessori acquistati dal cliente");
			 System.out.println("Cod. fiscale, cod. accessorio, modello accessorio");
			 while(rs.next()) {
				  String cf_customer = rs.getString("c_cf");
				  String codprod= rs.getString("prod_codice");
				  String accessoriesmodel=rs.getString("accessori_modello");
				  System.out.printf("%s, %s, %s\n",cf_customer, codprod, accessoriesmodel);
			  }  

		     }
			 
		 }
	else if (choice==5) {
			 int choiceop=op.opazienda(key);
			
			 if(choiceop==1) {
				 Scanner board= new Scanner(System.in);
				 String sql= "SELECT * FROM azienda";
				 rs=st.executeQuery(sql);
				 System.out.println();
				 System.out.println("Lista aziende produttrici");
				 System.out.println("Nome, indirizzo, e-mail");
				 while(rs.next()) {
					  String companyname = rs.getString("azienda_nome");
					  String companyaddress= rs.getString("azienda_indirizzo");
					  String companyemail= rs.getString("e_mail");
					  System.out.printf("%s, %s, %s\n",companyname, companyaddress, companyemail);
					  
				  }
				 System.out.println();
				  
				 System.out.println("Inserisci, in ordine, separando con invio: nome e indirizzo dell'azienda da cambiare: "); 
				 String oldcmpname= board.nextLine();
				 String oldcmpaddr= board.nextLine();
				 System.out.println("Inserisci in ordine, separando con invio: nome, indirizzo e e-mail della nuova azienda: "); 
				 String newcmpname=board.nextLine();
				 String newcmpaddr=board.nextLine();
				 String newcmpemail=board.nextLine(); 
				 String usql = "UPDATE azienda SET azienda_nome='"+newcmpname+"', azienda_indirizzo='"+newcmpaddr+"', e_mail='"+newcmpemail+"' WHERE azienda_nome='"+oldcmpname+"' AND azienda_indirizzo='"+oldcmpaddr+"'";
				 int res = st.executeUpdate(usql);
				  
				 System.out.println();
				 String sql1="SELECT * FROM azienda";
				 rs= st.executeQuery(sql1);
				 System.out.println("Elenco aggiornato aziende produttrici");
				 System.out.println("Nome, indirizzo, e-mail");
				 while (rs.next()) {
					String cmpname= rs.getString("azienda_nome");
					String cmpaddr= rs.getString("azienda_indirizzo");
					String cmpemail= rs.getString("e_mail");
					System.out.printf("%s, %s, %s\n",cmpname, cmpaddr, cmpemail);
				 }
			 }
	    else if(choiceop==2) {
	   	 Scanner board= new Scanner(System.in);
		 String sql= "SELECT * FROM azienda";
		 rs=st.executeQuery(sql);
		 System.out.println();
		 System.out.println("Elenco aziende produttrici");
		 System.out.println("Nome, indirizzo, e-mail");
		 while(rs.next()) {
			  String compname = rs.getString("azienda_nome");
			  String compaddress= rs.getString("azienda_indirizzo");
			  String compemail= rs.getString("e_mail");
			  System.out.printf("%s %s %s\n",compname, compaddress, compemail);
			  
		  }
		 System.out.println();
		  
		 System.out.println("Inserisci in ordine, separando con invio: nome e indirizzo dell'azienda che deve cambiare l'email: "); 
		 String namecomp= board.nextLine();
		 String addrcomp= board.nextLine();
		 System.out.println("Inserisci il nuovo indirizzo e-mail: "); 
		 String newcompemail=board.nextLine(); 
		 String usql = "UPDATE azienda SET e_mail='"+newcompemail+"' WHERE azienda_nome='"+namecomp+"' AND azienda_indirizzo='"+addrcomp+"'";
		 int res = st.executeUpdate(usql);
		  
		 System.out.println();
		 String sql1="SELECT * FROM azienda";
		 rs= st.executeQuery(sql1);
		 System.out.println("Elenco aggiornato aziende produttrici");
		 System.out.println("Nome, indirizzo, e-mail");
		 while (rs.next()) {
			String cmpname= rs.getString("azienda_nome");
			String cmpaddr= rs.getString("azienda_indirizzo");
			String cmpemail= rs.getString("e_mail");
			System.out.printf("%s %s %s\n",cmpname, cmpaddr, cmpemail);
		 }
	     	 }
		 }
		System.out.println();
		System.out.println("Per continuare a fare operazioni premere 'y' altrimenti qualsiasi altro tasto:");
		go_on=continues.nextLine();
		System.out.println();
		}
		} 
		catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage()); 
	    }
		
		
		finally{
		   try {
			System.out.println("Chiusura operazioni");
			rs.close();
			st.close();
			if(con!=null) {
			  con.close();
			}
		   }
		     catch(SQLException e) {
			   System.err.println("SQLException:" + e.getMessage()); 
		     }

	}

			
  }
	
}