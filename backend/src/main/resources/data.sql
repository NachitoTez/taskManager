-- 👨‍💼 Manager: Mace Windu
INSERT INTO users (id, username, password, role)
VALUES (
           '11111111-1111-1111-1111-111111111111',
           'mace',
           '$2a$10$LDKdf2pKh2tErz4jJt6nDOlmfAfe9pv7LBZEDYOa/NGOaZ2UmnC0C', -- windu123
           'MANAGER'
       );

-- 👨‍🔧 Member: Anakin Skywalker
INSERT INTO users (id, username, password, role)
VALUES (
           '22222222-2222-2222-2222-222222222222',
           'anakin',
           '$2a$10$EoMwK8FQy9UwK7tV3fhZ9ePBvE6r5FSqt5aBqZbIXMJZLwV6tHjq2', -- vader123
           'MEMBER'
       );

-- 🌌 Project creado por Mace
INSERT INTO projects (id, name, creator_id)
VALUES (
           'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
           'Jedi Council Missions',
           '11111111-1111-1111-1111-111111111111'
       );

-- 🧩 TaskComponent: Diplomacy
INSERT INTO components (id, name, project_id)
VALUES (
           'bbbbbbb1-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
           'Diplomatic Missions',
           'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
       );

-- 🧩 TaskComponent: Battles
INSERT INTO components (id, name, project_id)
VALUES (
           'bbbbbbb2-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
           'Outer Rim Battles',
           'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa'
       );
