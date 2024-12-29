CREATE TABLE observation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ilot VARCHAR(255),
    distance_bord_enm DOUBLE,
    date_observation TIMESTAMP,
    taille_estimee_en_cm DOUBLE,
    qualite VARCHAR(255),
    type VARCHAR(255),
    -- Colonnes spécifiques aux types d'observation
    temps_apnee_observe INT,
    espece_mammifere_marin VARCHAR(255),
    est_un_banc BOOLEAN,
    nombre_individus INT,
    espece_poisson VARCHAR(255)
);