INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_AGENT');        /* Agence (Ecran FO) */
INSERT INTO roles(name) VALUES('ROLE_CTN');          /* CTN (Ecran BO) */
INSERT INTO roles(name) VALUES('ROLE_IBANKING');     /* I-Banking (flux) */
INSERT INTO roles(name) VALUES('ROLE_MBANKING');     /* M-banking (flux) */
INSERT INTO roles(name) VALUES('ROLE_GAB');          /* GAB (flux) */
INSERT INTO roles(name) VALUES('ROLE_PARTNER');      /* Partenaires (Flux de masse) */
INSERT INTO roles(name) VALUES('ROLE_PERMANENT');    /* Ordres permanents (contrat) */
INSERT INTO roles(name) VALUES('ROLE_CENTRAL_FCT');  /* les fonctions centrales (Ecran BO) */

INSERT INTO roles(name) VALUES('ROLE_CTRL');

/* les combinaisons de devises  */
INSERT INTO privileges(name) VALUES('MAD_MAD');
INSERT INTO privileges(name) VALUES('MAC_MAC');
INSERT INTO privileges(name) VALUES('MAC_MAD');
INSERT INTO privileges(name) VALUES('MAD_MAC');
INSERT INTO privileges(name) VALUES('MAD_OTHER');
INSERT INTO privileges(name) VALUES('MAC_OTHER');
INSERT INTO privileges(name) VALUES('OTHER_MAD');
INSERT INTO privileges(name) VALUES('OTHER_MAC');
INSERT INTO privileges(name) VALUES('OTHER_OTHER');

/**	Les combinaisons en devises possibles et les canaux y étant liés **/
/* Agence (Ecran FO) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_AGENT','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_AGENT','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_AGENT','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_AGENT','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_AGENT','MAD_OTHER');

/* CTN (Ecran BO) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAD_OTHER');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','MAC_OTHER');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','OTHER_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','OTHER_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CTN','OTHER_OTHER');

/* I-Banking (flux) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_IBANKING','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_IBANKING','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_IBANKING','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_IBANKING','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_IBANKING','MAD_OTHER');

/* M-banking (flux) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_MBANKING','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_MBANKING','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_MBANKING','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_MBANKING','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_MBANKING','MAD_OTHER');

/* GAB (flux) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_GAB','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_GAB','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_GAB','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_GAB','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_GAB','MAD_OTHER');

/* Partenaires (Flux de masse) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PARTNER','MAD_MAD');

/* Ordres permanents (contrat) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PERMANENT','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PERMANENT','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PERMANENT','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PERMANENT','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_PERMANENT','MAD_OTHER');

/* Les fonctions centrales (Ecran BO) */
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAD_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAC_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAC_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAD_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAD_OTHER');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','MAC_OTHER');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','OTHER_MAD');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','OTHER_MAC');
INSERT INTO role_privileges(role_name,privilege_name) VALUES('ROLE_CENTRAL_FCT','OTHER_OTHER');

