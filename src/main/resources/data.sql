insert into Commission_Rate values(1,'commisionIntraAgence',0.1);
insert into Commission_Rate values(2,'commisionIntraBpr',0.15);
insert into Commission_Rate values(3,'commisionInterBpr',0.2);


insert into TVARate values (1,'tvaIntraAgence',0.14);
insert into TVARate values (2,'tvaIntraBpr',0.16);
insert into TVARate values (3,'tvaInterBpr',0.2);

insert into Transfer_Type  values(1,'IntraAgence',1,1);
insert into Transfer_Type  values(2,'IntraBpr',2,2);
insert into Transfer_Type  values(3,'InterBpr',3,3);

insert into Currency values(1,'MAD','Moroccan Dirham');
insert into Currency values(2,'MAC', 'Moroccan Dirham C');
insert into Currency values(3,'EUR','Euro');
insert into Currency values(4,'USD', 'US Dollar');

insert into Exchange_Rate values(1,'MAD','USD',0.103,0.104,1,0.105,null,null);
insert into Exchange_Rate values(2,'MAD','EUR',0.092,0.093,1,0.094,null,null;
insert into Exchange_Rate values(3,'USD','MAD',9.666,9.667,1,9.668,null,null);
insert into Exchange_Rate values(4,'EUR','MAD',10.787,10.788,1,10.789,null,null;

insert into Transfer_Source values (1,'IEA','Intra en agence');
insert into Transfer_Source values (2,'IAC','Intra au ctn');
insert into Transfer_Source values (3,'MBK','M-banking');
insert into Transfer_Source values (1,'EBK','E-banking');
insert into Transfer_Source values (1,'GAB','Guichet automatique bancaire');
insert into Transfer_Source values (1,'FDM','Flux de masse');
insert into Transfer_Source values (1,'PMI','Permanent intra');
insert into Transfer_Source values (1,'FTC','Fonction centrale');


insert into Account_Category values(1,'compte de gestion');
insert into Account_Category values(2,'compte financier');
insert into Account_Category values(3,'compte de charges et profit');
insert into Account_Category values(4,'compte tva');
insert into Account_Category values(5,'compte de liaiqon bpr agence');
insert into Account_Category values(6,'compte courant');
insert into Account_Category values(7,'compte epargne');
insert into Account_Category values(8,'compte titre');

insert into Account_Sub_Category values(1,'compte de gestion',1);
insert into Account_Sub_Category values(2,'compte financier',1);
insert into Account_Sub_Category values(3,'compte de charges et profit',1);
insert into Account_Sub_Category values(4,'compte courant',2);
insert into Account_Sub_Category values(5,'compte epargne',2);
insert into Account_Sub_Category values(6,'compte titre',2);

insert into Account_Sub_Category_Nature values(1,'compte individuel',4);
insert into Account_Sub_Category_Nature values(2,'compte joint',4);
insert into Account_Sub_Category_Nature values(3,'compte indivis',4);
insert into Account_Sub_Category_Nature values(4,'compte à terme',4);


insert into Bank_Central_Pop values(1,'boulevard zerktouni','280','banque centrale populaire');
insert into Bank_Regional VALUES(2,'casa boulevard d Anfa','270','bpr casa',1);
insert into Bank_Regional VALUES(3,'fes boulevard de France','260','bpr fes',1);
insert into Agency VALUES(4,'casa medina','250','agence medina',2);
insert into Agency VALUES(5,'zerktouni','240','agence zerktouni',2);
insert into Agency VALUES(6,'derbOmar','230','agence derbomar',3);

insert into State(code,libelle) values('200','active');
insert into State(code,libelle) values('400','compte cloturé');
insert into State(code,libelle) values('505','compte en opposition');
insert into State(code,libelle) values('606','décédé');
insert into State(code,libelle) values('707','saisie arret,atd,...');

insert into State(code,libelle) values('1000','en cours');
insert into State(code,libelle) values('2000','en attente de validation au ctn');
insert into State(code,libelle) values('3000','validée');
insert into State(code,libelle) values('4000','refusée');
insert into State(code,libelle) values('5000','annulée');
insert into State(code,libelle) values('6000','exécutée');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2627,5000,'200','MAD','230','230','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2628,5000,'200','MAD','230','230','compte tva');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2629,5000,'200','MAD','240','240','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2630,5000,'200','MAD','240','240','compte tva');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2631,5000,'200','MAD','250','250','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2632,5000,'200','MAD','250','250','compte tva');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2633,5000,'200','MAD','260','260','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2634,5000,'200','MAD','260','260','compte tva');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2635,5000,'200','MAD','260','260','compte de liaiqon bpr agence');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2636,5000,'200','MAD','270','270','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2637,5000,'200','MAD','270','270','compte tva');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2638,5000,'200','MAD','270','270','compte de liaiqon bpr agence');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2639,5000,'200','MAD','270','270','compte de charges et profit');

insert into Account(account_number,Balance,state,CURRENCY,resident,institute_Account,account_category)
values(2640,5000,'200','MAD','280','280','compte tva');


insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2728,1000,'200','MAD','230',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2729,1000,'400','MAD','230',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2730,1000,'505','MAD','230',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2731,1000,'200','MAD','230',1,'compte epargne');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2732,1000,'200','MAD','230',1,'compte titre');

insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2733,1000,'200','USD','240',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2734,1000,'400','MAD','240',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2735,1000,'505','MAD','240',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2736,1000,'200','MAD','240',1,'compte epargne');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2737,1000,'200','MAD','240',1,'compte titre');

insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2738,1000,'200','MAD','250',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2739,1000,'400','MAD','250',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2740,1000,'505','MAD','250',1,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2741,1000,'200','MAD','250',1,'compte epargne');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2742,1000,'200','MAD','250',1,'compte titre');


insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2743,5000,'200','MAD','230',3,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2744,5000,'400','MAD','240',3,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2745,5000,'505','MAD','250',3,'compte courant');



insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2746,5000,'200','MAD','230',4,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2747,5000,'400','MAD','240',4,'compte courant');
insert into Account(account_number,Balance,state,CURRENCY,resident,customer,account_category)
values(2748,5000,'505','MAD','250',4,'compte courant');




insert into Customer_Category values(1,'particulierProfessionel');
insert into Customer_Category values(2,'particulierSimple');
insert into Customer_Category values(3,'client salle de marché');
insert into Customer_Category values(4,'TPE');
insert into Customer_Category values(5,'PME');
insert into Customer_Category values(6,'GE');


insert into Customer_Sub_Category values(1,'particulierProfessionel',1);
insert into Customer_Sub_Category values(2,'particulierSimple',1);
insert into Customer_Sub_Category values(3,'client salle de marché',2);


insert into Customer_Particular(customer_id,first_name,last_name,state,phone_number) values(1,'Aissata','Sinaba','200',222);
insert into Customer_Particular(customer_id,first_name,last_name,state,phone_number) values(2,'Reda','Khouna','200',22);
insert into Customer_Particular(customer_id,first_name,last_name,state,phone_number) values(3,'xxx','yyy','606',222);
insert into Customer_Particular(customer_id,first_name,last_name,state,phone_number) values(4,'xxx','yyy','707',222);
