INSERT INTO role (id, type) VALUES
                                (1,'user'),
                                (2, 'admin');

INSERT INTO user (id, is_enabled, role_id, city, username, email, password, zip_code) VALUES
            (1, true, 1, null, 'louli', 'louli@mail.com', '$2a$10$7ZXMdQEmZn4XUW3WsdET6uia9YSKWAB3fyUyYaM03JWzUKH5Qy266', null),
            (2, true, 2, null, 'applePie', 'apple@mail.com', '$2a$10$w7.PKfBy/b4CotM03.utYOIfdlvHdW6BIXpJZgAcYxDfIEELjf8I.', null);

INSERT INTO category (id, category) VALUES
                                        (1, 'Bandes dessinées'),
                                        (2, 'Cuisine'),
                                        (3, 'Histoire');
