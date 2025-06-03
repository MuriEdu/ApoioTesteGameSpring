-- Inserção de projetos
INSERT INTO project_tb (name, description, created_at)
VALUES
    ('Projeto Alpha', 'Primeiro projeto de exemplo', NOW()),
    ('Projeto Beta', 'Segundo projeto de exemplo', NOW()),
    ('Projeto Gama', 'Terceiro projeto de exemplo', NOW());

-- Inserção de estratégias
INSERT INTO strategy_tb (name, description, tips, images)
VALUES
    (
        'Defesa de Torres',
        'Estratégia baseada na proteção de pontos estratégicos.',
        'Construa torres em gargalos e rotas obrigatórias.',
        JSON_ARRAY('https://cdn.example.com/images/tower1.jpg', 'https://cdn.example.com/images/tower2.jpg')
    ),
    (
        'Ataque Relâmpago',
        'Movimentação rápida para surpreender o inimigo.',
        'Utilize unidades leves e rápidas no início da partida.',
        JSON_ARRAY('https://cdn.example.com/images/rush1.jpg', 'https://cdn.example.com/images/rush2.jpg')
    ),
    (
        'Controle de Área',
        'Tomar o controle de áreas centrais para dominar o campo.',
        'Concentre-se em regiões elevadas e cruzamentos estratégicos.',
        JSON_ARRAY('https://cdn.example.com/images/area1.jpg', 'https://cdn.example.com/images/area2.jpg')
    );

INSERT INTO project_tb_members (project_tb_id, members_id) VALUES
     -- Projeto Alpha: admin + Et Bilu
     (1, 1),
     (1, 2),

     -- Projeto Beta: Arnold + Mickey
     (2, 3),
     (2, 4),

     -- Projeto Gama: todos
     (3, 1),
     (3, 2),
     (3, 3),
     (3, 4);
