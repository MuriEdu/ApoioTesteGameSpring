-- Inserção de exemplos na tabela project_tb
-- Supondo que 'id' é AUTO_INCREMENT

INSERT INTO project_tb (name, description, created_at)
VALUES
    ('Projeto Alpha', 'Primeiro projeto de exemplo', NOW()),
    ('Projeto Beta', 'Segundo projeto de exemplo', NOW()),
    ('Projeto Gama', 'Terceiro projeto de exemplo', NOW());

-- Inserção de exemplos na tabela strategy_tb
-- O campo 'images' é do tipo TEXT contendo uma string JSON

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
