INSERT INTO sexes (sexe) VALUES
('Homme'),
('Femme');


INSERT INTO roles (role) VALUES
('Administrateur'),
('Utilisateur');


INSERT INTO utilisateurs (nom, prenoms, email, adresse, mdp, poids, date_naissance, Id_roles, Id_sexes) VALUES
('ismael', 'blaise', 'isma@gmail.com', '123 Rue Admin, Ville', '$2a$10$4WrIJZ05VKxkzF7adSFAOOBUo/lVOlG6YNaYX.DWU240imznMAeZq', 75, '1990-01-01', 1, 1);


INSERT INTO types_mouvement(type_mouvement) VALUES('Vente'),('Achat');


INSERT INTO types_medicament (type_medicament) VALUES 
('Antibiotique'), 
('Antidouleur'), 
('Antipyrétique'), 
('Antiviral');


INSERT INTO voies_administration (voie_administration) VALUES 
('Orale'), 
('Injectable'), 
('Topique'), 
('Inhalée');


INSERT INTO formes (forme, Id_voies_administration) VALUES 
('Comprimé', 1), 
('Capsule', 1), 
('Sirop', 1), 
('Injection', 2), 
('Crème', 3);


INSERT INTO laboratoires (nom) VALUES 
('Sanofi'), 
('Pfizer'), 
('Novartis'), 
('Roche');


INSERT INTO compositions (nom, description) VALUES 
('Paracétamol', 'Analgésique antipyrétique'), 
('Ibuprofène', 'Anti-inflammatoire non stéroïdien'), 
('Amoxicilline', 'Antibiotique pénicilline'), 
('Oseltamivir', 'Inhibiteur de la neuraminidase antiviral');


INSERT INTO maladies (maladie, description) VALUES 
('Grippe', 'Infection virale respiratoire'), 
('Infection bactérienne', 'Infection causée par des bactéries'), 
('Migraine', 'Céphalées intenses'), 
('Arthrite', 'Inflammation des articulations');


INSERT INTO symptomes (symptome) VALUES 
('Fièvre'), 
('Douleurs musculaires'), 
('Toux'), 
('Maux de tête'), 
('Inflammation');

INSERT INTO medicaments (nom_commercial, nom_scientifique) VALUES 
('Doliprane', 'Paracétamol'), 
('Nurofen', 'Ibuprofène'), 
('Amoxicilline Mylan', 'Amoxicilline'), 
('Tamiflu', 'Oseltamivir');


INSERT INTO medicament_fiches (photo, dosage, age_min, age_max, poid_min, poid_max, indication, Id_formes, Id_types_medicament, Id_medicaments) VALUES 
('doliprane.jpg', 500, 6, 99, 20, 200, 'Soulagement de la douleur et de la fièvre', 1, 3, 1),
('nurofen.jpg', 200, 12, 99, 30, 200, 'Douleurs inflammatoires', 1, 2, 2),
('amoxicilline.jpg', 500, 1, 99, 10, 200, 'Infections bactériennes', 2, 1, 3),
('tamiflu.jpg', 75, 1, 99, 10, 200, 'Traitement de la grippe', 4, 4, 4);



INSERT INTO statut_commande(statut) VALUES('valider'),('annuler'),('en attente');

