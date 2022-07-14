Drop database if Exists VG_Stores;
Create database VG_Stores; /* It is a synonym for CREATE SCHEMA*/
Use VG_Stores;

   /* Begin table creation */
Create Table cliente (
	c_cf Char(16) Not Null,
    c_nome Varchar(20) Not Null,
    c_cognome Varchar(30) Not Null,
    Primary Key (c_cf)
    );
    
Create Table gestore (
	g_cf Char(16) Not Null,
	g_nome Varchar(20) Not Null,
    g_cognome Varchar(30) Not Null,
    g_birthdate Date Not Null,
    Primary Key(g_cf)
    );

Create Table telefono(
	numero Varchar(15) Not Null,
    ges_cf_tel Char(16) Not Null,
    Primary Key (numero),
    Foreign Key (ges_cf_tel) References gestore(g_cf)
    On Update cascade On Delete Cascade
    );
    
Create Table sede_acquisto(
	id Int Not Null,
    sede_nome Varchar(20) Not Null,
    s_cf Char(16) Not Null,
    indirizzo_sede Varchar(45) Not Null,
    Primary Key (id),
    Foreign Key (s_cf) References gestore(g_cf)
    On Update cascade
	);
    
Create Table fattura(
	numero_fattura Varchar(30) Not Null,
    fattura_id_sede Int Not Null,
    f_data Date Not Null,
	Primary Key (numero_fattura, fattura_id_sede),
    Foreign Key (fattura_id_sede) References sede_acquisto (id)
    On Delete cascade On Update cascade
    );
    
Create Table azienda(
	azienda_nome Varchar(20) Not Null,
    azienda_indirizzo Varchar(45) Not Null,
    e_mail Varchar(45) Not Null,
    Primary Key(azienda_nome, azienda_indirizzo)
    );
    
Create Table prodotto(
	prod_codice Varchar(20) Not Null,
    prod_nome Varchar(30) Not Null,
    prezzo Double Not Null,
    prod_num_fattura Varchar(30) Not Null,
    prod_id_sede Int Not Null,
    prod_cf_cliente Char(16) Not Null,
    prod_nome_az Varchar(20) Not Null,
    prod_indirizzo_az Varchar(45) Not Null,
    Primary Key (prod_codice),
    Foreign Key(prod_num_fattura, prod_id_sede) References fattura(numero_fattura, fattura_id_sede)
    On Update cascade On Delete cascade,
    /*Foreign Key(prod_id_sede) References fattura(fattura_id_sede),*/
    Foreign Key(prod_cf_cliente) References cliente(c_cf),
    Foreign Key(prod_nome_az, prod_indirizzo_az) References azienda(azienda_nome, azienda_indirizzo)
    On Update cascade
    /*Foreign Key(prod_indirizzo_az) References azienda(azienda_indirizzo)*/
    );
    
Create Table console(
	console_codice Varchar(20) Not Null,
    console_modello Varchar(20) Not Null,
    Primary Key (console_codice),
    Foreign Key (console_codice) References prodotto(prod_codice)
    On Update cascade On Delete cascade
    );
    
Create Table videogiochi(
	vg_codice Varchar(20) Not Null,
    piattaforma Varchar(20) Not Null,
    Primary Key (vg_codice),
    Foreign Key (vg_codice) References prodotto(prod_codice)
    On Update cascade On Delete cascade
    );
    
Create Table categoria(
	genere Varchar(30) Not Null,
	cat_codice_vg Varchar(20) Not Null,
    Primary Key (genere, cat_codice_vg),
    Foreign Key (cat_codice_vg) References videogiochi(vg_codice)
	On Update cascade On Delete cascade
    );

Create Table accessori(
	accessori_codice Varchar(20) Not Null,
    accessori_modello Varchar(20) Not Null,
    Primary Key (accessori_codice),
    Foreign Key (accessori_codice) References prodotto(prod_codice)
	On Update cascade On Delete cascade
	);
    /* End table creation */
    /*Begin data population */
    
    /* cliente data */
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('SGSMNL82R21D643D', 'Manuele', 'Saggese');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('MLNDLA94D54B590W', 'Dalia', 'Milani');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('SPSJND97M57F839F', 'Jolanda', 'Esposito');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('LNGGNN86A29I805E', 'Giovanni', 'Longo');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('MZZGCR90S23G942Q', 'Giancarlo', 'Mazzanti');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('PGNFLC79T08D612T', 'Felice', 'Pagnotto');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('LVRTTV99C30L736Q', 'Ottavio', 'Olivieri');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('NLNNHN95P26H501E', 'Anthony', 'Nolan');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('VLZCHR98A45G273W', 'Chiara', 'Velez');
Insert Into cliente(c_cf, c_nome, c_cognome)
	values ('LDCRCR85T10A509V', 'Riccardo', 'Lo Duca');
    
    /* gestore data */
Insert Into gestore(g_cf, g_nome, g_cognome, g_birthdate)
	values ('MNLDGR67E13I452G', 'Edgar', 'Monaldo','1967-05-13');
Insert Into gestore(g_cf, g_nome, g_cognome, g_birthdate)
	values ('RLAMRC71D26H501A', 'Marco', 'Aurelio','1971-04-26');
    
    /*telefono data*/
Insert Into telefono(numero, ges_cf_tel)
	values ('0349 5888028', 
    (Select g_cf From gestore Where g_nome='Edgar' AND g_cognome='Monaldo'));
Insert Into telefono(numero, ges_cf_tel)
	values ('0329 7359035', 
    (Select g_cf From gestore Where g_nome='Marco' AND g_cognome='Aurelio'));
    
    /*sede_acquisto data*/
Insert Into sede_acquisto(id, sede_nome, s_cf, indirizzo_sede)
	values ('00001', 'Games Universe', 
    (Select g_cf From gestore Where g_nome='Edgar' AND g_cognome='Monaldo'),'10 Via dei Due Principati AV');
Insert Into sede_acquisto(id, sede_nome, s_cf, indirizzo_sede)
	values ('00002', 'VG Store', 
    (Select g_cf From gestore Where g_nome='Marco' AND g_cognome='Aurelio'),'1 Piazza del Colosseo RM');

    /*fattura data*/
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171623', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-17');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171624', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-17');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171625', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-18');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171626', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-18');    
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171627', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-18');    
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20171628', 
    (Select id From sede_acquisto Where sede_nome='Games Universe' AND indirizzo_sede='10 Via dei Due Principati AV'),'2017-09-19');    
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173284', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-23'); 
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173285', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-23');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173286', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-24');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173287', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-24');   
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173288', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-25');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173289', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-26');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173290', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-26');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173291', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-26');
Insert Into fattura(numero_fattura, fattura_id_sede, f_data)
	values ('20173292', 
    (Select id From sede_acquisto Where sede_nome='VG Store' AND indirizzo_sede='1 Piazza del Colosseo RM'),'2017-10-26');
    
    /*azienda data*/
Insert Into azienda(azienda_nome, azienda_indirizzo, e_mail)
	values ('Sony', '1-7-1 Konan Minato-ku, Tokyo, 108-0075', 'sony@email.sonycorporation.com');
Insert Into azienda(azienda_nome, azienda_indirizzo, e_mail)
	values ('Microsoft', '1 Redmond, WA 98052-7329', 'microsoft@email.microsoftcorporation.com');
Insert Into azienda(azienda_nome, azienda_indirizzo, e_mail)
	values ('Naughty Dog', '2425 Olympic Blvd, Santa Monica, CA 90404', 'ndi-dog@naughtydog.com');
Insert Into azienda(azienda_nome, azienda_indirizzo, e_mail)
	values ('Ubisoft', '28 Rue Armand Carrel, 93100 Montreuil', 'ubi@ubisoft.com');
Insert Into azienda(azienda_nome, azienda_indirizzo, e_mail)
	values ('Electronic Arts', '209 Redwood Shores, Redwood City, CA 94065', 'ea@electronicarts.com');
    
    /*prodotto data*/
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('MB05622765', 'PlayStation 4', '299.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171623' AND fattura_id_sede='00001'), 
	(Select  distinct fattura_id_sede From fattura Where fattura_id_sede='00001'),
	(Select c_cf From cliente Where c_nome='Manuele' AND c_cognome='Saggese'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BCES23461407', 'Uncharted 4:Fine di un Ladro', '69.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171623' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Manuele' AND c_cognome='Saggese'),
	(Select azienda_nome From azienda Where azienda_nome='Naughty Dog'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Naughty Dog'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BLES95573495', 'FIFA 17', '49.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171623' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Manuele' AND c_cognome='Saggese'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('MB08736592', 'PlayStation 4 Pro', '399.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171624' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Dalia' AND c_cognome='Milani'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BCES32644170', 'Uncharted 4:Fine di un Ladro', '69.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171624' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Dalia' AND c_cognome='Milani'),
	(Select azienda_nome From azienda Where azienda_nome='Naughty Dog'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Naughty Dog'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('PX1069043', 'Crash Bandicoot', '3.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171624' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Dalia' AND c_cognome='Milani'),
	(Select azienda_nome From azienda Where azienda_nome='Naughty Dog'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Naughty Dog'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('XC4168736592', 'Xbox One S', '399.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171625' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Ottavio' AND c_cognome='Olivieri'),
	(Select azienda_nome From azienda Where azienda_nome='Microsoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Microsoft'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ONS89755863245', 'Assassins Creed Origins', '49.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171625' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Ottavio' AND c_cognome='Olivieri'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BCES97342352', 'Uncharted:The Lost Legacy', '39.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171626' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Giovanni' AND c_cognome='Longo'),
	(Select azienda_nome From azienda Where azienda_nome='Naughty Dog'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Naughty Dog'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('CD04710053', 'PlayStation 4', '299.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171627' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Jolanda' AND c_cognome='Esposito'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('NTD6432964', 'Rayman Legends', '29.55', 
	(Select numero_fattura From fattura Where numero_fattura='20171627' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Jolanda' AND c_cognome='Esposito'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('D11623A22885', 'DualShock 4', '69.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171627' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Jolanda' AND c_cognome='Esposito'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('LV35483542', 'PlayStation 4 Pro', '399.99', 
	(Select numero_fattura From fattura Where numero_fattura='20171628' AND fattura_id_sede='00001'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00001'), 
	(Select c_cf From cliente Where c_nome='Giovanni' AND c_cognome='Longo'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('LV96543825', 'PlayStation 4 Pro', '449.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173284' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Giancarlo' AND c_cognome='Mazzanti'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ONS7608698658', 'FIFA 17', '59.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173284' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Giancarlo' AND c_cognome='Mazzanti'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BLES21095432', 'Watch Dogs 2', '39.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173285' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Felice' AND c_cognome='Pagnotto'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('WND9024343840A', 'For Honor', '44.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173285' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Felice' AND c_cognome='Pagnotto'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft'));  
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ONS6576879997', 'Mass Effect: Andromeda', '59.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173285' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Felice' AND c_cognome='Pagnotto'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts'));  
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('WND6453246338S', 'BattleField 1', '19.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173286' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Giancarlo' AND c_cognome='Mazzanti'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('X12343C00004', 'X1 Wireless Controller', '69.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173286' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Giancarlo' AND c_cognome='Mazzanti'),
	(Select azienda_nome From azienda Where azienda_nome='Microsoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Microsoft')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ONS124354675329', 'Star Wars: Battlefront', '34.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173287' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Anthony' AND c_cognome='Nolan'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ZD6943022065', 'Xbox One', '329.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173288' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Anthony' AND c_cognome='Nolan'),
	(Select azienda_nome From azienda Where azienda_nome='Microsoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Microsoft')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('WND8745678786D', 'Assassins Creed Origins', '49.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173289' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Riccardo' AND c_cognome='Lo Duca'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('SWT4968432', 'Rayman Legends', '19.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173289' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Riccardo' AND c_cognome='Lo Duca'),
	(Select azienda_nome From azienda Where azienda_nome='Ubisoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Ubisoft')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('D84930V50107', 'DualShock 4', '59.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173289' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Riccardo' AND c_cognome='Lo Duca'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('B00I8S4E1C', 'Sony-Cuffie Wireless', '78.98', 
	(Select numero_fattura From fattura Where numero_fattura='20173290' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Chiara' AND c_cognome='Velez'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ID64467280', 'PlayStation 4', '329.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173291' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Chiara' AND c_cognome='Velez'),
	(Select azienda_nome From azienda Where azienda_nome='Sony'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Sony')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('ZD9964724711', 'Xbox One', '329.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173291' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Chiara' AND c_cognome='Velez'),
	(Select azienda_nome From azienda Where azienda_nome='Microsoft'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Microsoft'));
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('BLES743985094', 'Mass Effect: Andromeda', '39.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173291' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Chiara' AND c_cognome='Velez'),
	(Select azienda_nome From azienda Where azienda_nome='Electronic Arts'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Electronic Arts')); 
Insert Into prodotto(prod_codice, prod_nome, prezzo, prod_num_fattura, prod_id_sede, prod_cf_cliente,
prod_nome_az, prod_indirizzo_az)
	values ('SP24869708', 'Jak 3', '4.99', 
	(Select numero_fattura From fattura Where numero_fattura='20173292' AND fattura_id_sede='00002'), 
	(Select distinct fattura_id_sede From fattura Where fattura_id_sede='00002'), 
	(Select c_cf From cliente Where c_nome='Anthony' AND c_cognome='Nolan'),
	(Select azienda_nome From azienda Where azienda_nome='Naughty Dog'),
	(Select azienda_indirizzo From azienda Where azienda_nome='Naughty Dog'));      
    
    /*console data*/
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='MB05622765'), 'CUH-1110A'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='MB08736592'), 'CUH-7000B'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='XC4168736592'), 'MAS-C50010');
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='CD04710053'), 'CUH-1115B'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='LV35483542'), 'CUH-2015B'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='LV96543825'), 'CUH-2015C'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='ZD6943022065'), 'MAS-A10186'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='ID64467280'), 'CUH-2115A'); 
Insert Into console(console_codice, console_modello)
	values ((Select prod_codice From prodotto Where prod_codice='ZD9964724711'), 'MAS-B19230'); 
    
    /*videogiochi data*/
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BCES23461407'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BLES95573495'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BCES32644170'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='PX1069043'), 'PS1');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='ONS89755863245'), 'XBox One');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BCES97342352'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='NTD6432964'), 'Wii U');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='ONS7608698658'), 'XBox One');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BLES21095432'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='WND9024343840A'), 'Microsoft Windows');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='ONS6576879997'), 'Xbox One');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='WND6453246338S'), 'Microsoft Windows'); 
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='ONS124354675329'), 'Xbox One');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='WND8745678786D'), 'Microsoft Windows');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='SWT4968432'), 'Nintendo Switch');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='BLES743985094'), 'PS4');
Insert Into videogiochi(vg_codice, piattaforma)
	values((Select prod_codice From prodotto Where prod_codice='SP24869708'), 'PS2');
    
    /*categoria data*/
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='BCES23461407'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sparatutto', (Select vg_codice From videogiochi Where vg_codice='BCES23461407'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sportivo', (Select vg_codice From videogiochi Where vg_codice='BLES95573495'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='BCES32644170'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sparatutto', (Select vg_codice From videogiochi Where vg_codice='BCES32644170'));
Insert Into categoria(genere, cat_codice_vg)
	values('Platform', (Select vg_codice From videogiochi Where vg_codice='PX1069043'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='ONS89755863245'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='BCES97342352'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sparatutto', (Select vg_codice From videogiochi Where vg_codice='BCES97342352'));
Insert Into categoria(genere, cat_codice_vg)
	values('Platform', (Select vg_codice From videogiochi Where vg_codice='NTD6432964'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sportivo', (Select vg_codice From videogiochi Where vg_codice='ONS7608698658'));     
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='BLES21095432'));
Insert Into categoria(genere, cat_codice_vg)
	values('Adventure', (Select vg_codice From videogiochi Where vg_codice='BLES21095432'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='WND9024343840A'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='ONS6576879997'));
Insert Into categoria(genere, cat_codice_vg)
	values('GDR', (Select vg_codice From videogiochi Where vg_codice='ONS6576879997'));
Insert Into categoria(genere, cat_codice_vg)
	values('Sparatutto', (Select vg_codice From videogiochi Where vg_codice='WND6453246338S')); 
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='ONS124354675329')); 
Insert Into categoria(genere, cat_codice_vg)
	values('Sparatutto', (Select vg_codice From videogiochi Where vg_codice='ONS124354675329')); 
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='WND8745678786D')); 
Insert Into categoria(genere, cat_codice_vg)
	values('Platform', (Select vg_codice From videogiochi Where vg_codice='SWT4968432'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='BLES743985094'));
Insert Into categoria(genere, cat_codice_vg)
	values('GDR', (Select vg_codice From videogiochi Where vg_codice='BLES743985094'));
Insert Into categoria(genere, cat_codice_vg)
	values('Action', (Select vg_codice From videogiochi Where vg_codice='SP24869708'));
Insert Into categoria(genere, cat_codice_vg)
	values('Platform', (Select vg_codice From videogiochi Where vg_codice='SP24869708'));
Insert Into categoria(genere, cat_codice_vg)
	values('Adventure', (Select vg_codice From videogiochi Where vg_codice='SP24869708'));

    /*accessori data*/
Insert Into accessori(accessori_codice, accessori_modello)
	values((Select prod_codice From prodotto Where prod_codice='D11623A22885'), 'DS4-2CT1E');
Insert Into accessori(accessori_codice, accessori_modello)
	values((Select prod_codice From prodotto Where prod_codice='X12343C00004'), 'WCR0-142T'); 
Insert Into accessori(accessori_codice, accessori_modello)
	values((Select prod_codice From prodotto Where prod_codice='D84930V50107'), 'DS4-1FR4E');
Insert Into accessori(accessori_codice, accessori_modello)
	values((Select prod_codice From prodotto Where prod_codice='B00I8S4E1C'), 'SNY-CW997');