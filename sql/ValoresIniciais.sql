INSERT INTO usuarios (nome, email) VALUES ('Dr. Ana Paula', 'ana.paula@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Enf. Bruno Santos', 'bruno.santos@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Tec. Carla Moura', 'carla.moura@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Bioq. Diego Almeida', 'diego.almeida@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Dr. Fernanda Luz', 'fernanda.luz@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Tec. Gabriel Pires', 'gabriel.pires@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Enf. Helena Duarte', 'helena.duarte@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Tec. Igor Martins', 'igor.martins@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Bioq. Juliana Reis', 'juliana.reis@hospital.com');
INSERT INTO usuarios (nome, email) VALUES ('Enf. Lucas Rocha', 'lucas.rocha@hospital.com');


-- 1 Luvas descartáveis
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Luvas de Látex', 'Luvas descartáveis tamanho M', 'EPI', 500, 'Prateleira E1', TIMESTAMP '2025-01-10 08:30:00', 1, TIMESTAMP '2025-02-01 10:00:00', 2);

-- 2 Máscaras cirúrgicas
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Máscaras Cirúrgicas', 'Máscaras descartáveis tripla camada', 'EPI', 1000, 'Prateleira E2', TIMESTAMP '2025-01-11 09:15:00', 2, TIMESTAMP '2025-02-05 08:00:00', 7);

-- 3 Seringas descartáveis
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Seringas 5ml', 'Seringas estéreis 5ml com agulha', 'Materiais', 800, 'Prateleira M1', TIMESTAMP '2025-01-12 14:00:00', 3, NULL, NULL);

-- 4 Álcool 70%
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Álcool 70%', 'Frascos de 1L para assepsia', 'Higienização', 200, 'Prateleira H1', TIMESTAMP '2025-01-13 10:20:00', 4, TIMESTAMP '2025-02-08 11:00:00', 6);

-- 5 Reagente PCR
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Reagente PCR', 'Kit reagente para PCR em tempo real', 'Reagentes', 50, 'Geladeira R1', TIMESTAMP '2025-01-14 16:40:00', 5, TIMESTAMP '2025-02-12 09:30:00', 9);

-- 6 Tubos de ensaio
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Tubos de Ensaio 10ml', 'Tubos estéreis de vidro 10ml', 'Materiais', 600, 'Prateleira L1', TIMESTAMP '2025-01-15 12:10:00', 6, NULL, NULL);

-- 7 Pipetas automáticas
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Pipetas Automáticas', 'Micropipetas ajustáveis 10-100µl', 'Equipamentos', 20, 'Armário EQ1', TIMESTAMP '2025-01-16 13:00:00', 7, TIMESTAMP '2025-02-14 14:00:00', 8);

-- 8 Corantes histológicos
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Corantes Histológicos', 'Hematoxilina e eosina para análise de tecidos', 'Reagentes', 30, 'Geladeira R2', TIMESTAMP '2025-01-18 15:20:00', 8, NULL, NULL);

-- 9 Agulhas descartáveis
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Agulhas 25x7', 'Agulhas descartáveis estéreis', 'Materiais', 1200, 'Prateleira M2', TIMESTAMP '2025-01-20 08:45:00', 9, TIMESTAMP '2025-02-15 16:00:00', 10);

-- 10 Swabs estéreis
INSERT INTO estoque (nome, descricao, categoria, quantidade, localizacao, data_entrada, usuarioEntrada, data_retirada, usuarioRetirada)
VALUES ('Swabs Estéreis', 'Swabs para coleta de amostras clínicas', 'Materiais', 700, 'Prateleira C1', TIMESTAMP '2025-01-22 09:50:00', 10, NULL, NULL);
