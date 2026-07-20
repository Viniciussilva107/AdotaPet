-- ============================================================
-- Script de povoamento do banco AdotaPet 
-- ============================================================

USE adotapet;

-- ---------- Animal ----------

INSERT INTO Animal (nome, raca, porte, status_animal) VALUES
('Thor', 'Vira-lata', 'Grande', false),      -- 1: já adotado
('Mimi', 'Siamês', 'Pequeno', false),        -- 2: já adotado
('Caramelo', 'Vira-lata', 'Médio', true),    -- 3: disponível
('Luna', 'Poodle', 'Pequeno', true),         -- 4: disponível
('Rex', 'Pastor Alemão', 'Grande', true),    -- 5: disponível
('Nina', 'SRD', 'Médio', true),              -- 6: disponível
('Bidu', 'Beagle', 'Médio', false),          -- 7: já adotado
('Mel', 'Persa', 'Pequeno', true);           -- 8: disponível

-- ---------- Adotante ----------
INSERT INTO Adotante (cpf, nome, celular) VALUES
('11122233344', 'Vinícius Silva', '31999990001'),
('22222222222', 'Ana Beatriz Souza', '31999990002'),
('33344455566', 'Carlos Eduardo Lima', '31999990003'),
('44455566677', 'Fernanda Alves', '31999990004'),
('55566677788', 'Rodrigo Martins', '31999990005');

-- ---------- Vacina ----------
INSERT INTO Vacina (nome, fabricante) VALUES
('V10', 'Zoetis'),               -- 1
('V8', 'MSD Saúde Animal'),      -- 2
('Antirrábica', 'Boehringer'),   -- 3
('Giárdia', 'Merial'),           -- 4
('Gripe Canina', 'Zoetis');      -- 5

-- ---------- Adocao ----------

INSERT INTO Adocao (id_animal, cpf_adotante, data_adocao) VALUES
(1, '11122233344', '2026-05-10'),  -- Thor adotado por Vinícius
(2, '22222222222', '2026-06-02'),  -- Mimi adotada por Ana Beatriz
(7, '33344455566', '2026-06-20');  -- Bidu adotado por Carlos Eduardo

-- ---------- Vacinacao ----------
INSERT INTO Vacinacao (id_animal, id_vacina, data_aplicacao) VALUES
(1, 1, '2026-01-15'),  -- Thor tomou V10
(1, 3, '2026-02-15'),  -- Thor tomou Antirrábica
(2, 2, '2026-03-01'),  -- Mimi tomou V8
(3, 1, '2026-04-05'),  -- Caramelo tomou V10
(4, 4, '2026-04-20'),  -- Luna tomou Giárdia
(5, 3, '2026-05-01'),  -- Rex tomou Antirrábica
(7, 1, '2026-01-30'),  -- Bidu tomou V10
(7, 5, '2026-03-10');  -- Bidu tomou Gripe Canina

