CREATE SCHEMA IF NOT EXISTS airbnb;

CREATE TABLE IF NOT EXISTS airbnb.reviews (
    id int NOT NULL,
    owner varchar NOT NULL UNIQUE,
    address varchar NOT NULL,
    rate real NOT NULL,
    comment varchar NOT NULL
);

ALTER TABLE airbnb.reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);

INSERT INTO airbnb.reviews (id, owner, address, rate, comment) VALUES
    (1, 'John Carriot', '47 MySakila Drive', 4.5, 'Great place to stay, very clean and comfortable. The host was very helpful and friendly. I would definitely stay here again.'),
    (2, 'Mary Smith', '28 Himber Doll', 3, 'It was good, but the room was a bit small. The host was very nice and helpful.'),
    (3, 'Dan Doe', '12 Haul Street', 5, 'The best place I have ever stayed. The host was very friendly and helpful. I would definitely stay here again.'),
    (4, 'Linda Johnson', '42 Dill Street', 1, 'The worst place I have ever stayed. The host was very rude and unhelpful. I would never stay here again.'),
    (5, 'Tom Brown', '33 Haul Street', 4, 'Great place to stay, very clean and comfortable. The host was very helpful and friendly. I would definitely stay here again.'),
    (6, 'Sue White', '22 Dill Street', 2, 'It was good, but the room was a bit small. The host was very nice and helpful.'),
    (7, 'Jim Black', '11 Haul Street', 5, 'The best place I have ever stayed. The host was very friendly and helpful. I would definitely stay here again.'),
    (8, 'Jane Green', '44 Dill Street', 1, 'The worst place I have ever stayed. The host was very rude and unhelpful. I would never stay here again.'),
    (9, 'Bob Red', '35 Haul Street', 4, 'Great place to stay, very clean and comfortable. The host was very helpful and friendly. I would definitely stay here again.'),
    (10, 'Sally Blue', '26 Dill Street', 2, 'It was good, but the room was a bit small. The host was very nice and helpful.'),
    (11, 'Tim Yellow', '17 Haul Street', 5, 'The best place I have ever stayed. The host was very friendly and helpful. I would definitely stay here again.'),
    (12, 'Sandy Pink', '48 Dill Street', 1, 'The worst place I have ever stayed. The host was very rude and unhelpful. I would never stay here again.');

COMMIT;

ANALYZE;

