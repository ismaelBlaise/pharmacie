ALTER TABLE medicament_fiches
ADD CONSTRAINT unique_f
UNIQUE (dosage, Id_formes, Id_types_medicament, Id_medicaments);
