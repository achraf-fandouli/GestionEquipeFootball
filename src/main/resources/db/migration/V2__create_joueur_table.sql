
CREATE TABLE IF NOT EXISTS joueur (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255),
    position VARCHAR(255),
    equipe_id BIGINT,
    FOREIGN KEY (equipe_id) REFERENCES equipe(id)
);
