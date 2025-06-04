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
        JSON_ARRAY('https://a-static.mlcdn.com.br/1500x1500/jogo-jenga-novo-hasbro/magazineluiza/080256000/d397d30d406fff5f48d5df530f2e45ba.jpg')
    ),
    (
        'Ataque Relâmpago',
        'Movimentação rápida para surpreender o inimigo.',
        'Utilize unidades leves e rápidas no início da partida.',
        JSON_ARRAY('https://pbs.twimg.com/profile_images/832334977039794177/KhQsLe1-_400x400.jpg')
    ),
    (
        'Controle de Área',
        'Tomar o controle de áreas centrais para dominar o campo.',
        'Concentre-se em regiões elevadas e cruzamentos estratégicos.',
        JSON_ARRAY('https://www.pm.ro.gov.br/wp-content/uploads/2023/02/WhatsApp-Image-2023-02-08-at-13.43.30-1-e1675869739444.jpeg')
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
