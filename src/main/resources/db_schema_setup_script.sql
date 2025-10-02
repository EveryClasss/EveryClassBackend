-- everyclass_seed.sql
-- Script: drop/create schema + seed data for EveryClass
-- ATENTIE: ruleaza pe o baza de date de test. Sterge date existente.

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS attachments;
DROP TABLE IF EXISTS reports;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS teacher_subjects;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS teacher_profiles;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

-- Create tables
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'TEACHER', 'ADMIN') NOT NULL,
    profile_picture_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teacher_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    city VARCHAR(100),
    county VARCHAR(100),
    price_per_hour DECIMAL(10,2),
    description TEXT,
    photo_url VARCHAR(255),
    rating_avg DECIMAL(3,2) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subjects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teacher_subjects (
    teacher_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    PRIMARY KEY (teacher_id, subject_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher_profiles(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teacher_profiles(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CHECK (rating >= 1 AND rating <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, teacher_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teacher_profiles(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reported_user_id BIGINT NOT NULL,
    reporter_id BIGINT NOT NULL,
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE attachments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id BIGINT NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Populate subjects
INSERT INTO subjects (name) VALUES
('Matematica'), ('Limba Română'), ('Engleză'),
('Fizică'), ('Chimie'), ('Informatică'),
('Biologie'), ('Istorie');

-- Populate users: 10 teachers then 20 students
INSERT INTO users (name, email, password_hash, role, profile_picture_url, created_at) VALUES
-- 10 profesori
('Ana Popescu', 'ana.popescu@example.com', 'hashedpass1', 'TEACHER', 'https://picsum.photos/200?1', NOW()),
('Mihai Ionescu', 'mihai.ionescu@example.com', 'hashedpass2', 'TEACHER', 'https://picsum.photos/200?2', NOW()),
('Ioana Radu', 'ioana.radu@example.com', 'hashedpass3', 'TEACHER', 'https://picsum.photos/200?3', NOW()),
('Radu Pavel', 'radu.pavel@example.com', 'hashedpass4', 'TEACHER', 'https://picsum.photos/200?4', NOW()),
('Simona Ene', 'simona.ene@example.com', 'hashedpass5', 'TEACHER', 'https://picsum.photos/200?5', NOW()),
('Carmen Vlad', 'carmen.vlad@example.com', 'hashedpass6', 'TEACHER', 'https://picsum.photos/200?6', NOW()),
('Andrei Toma', 'andrei.toma@example.com', 'hashedpass7', 'TEACHER', 'https://picsum.photos/200?7', NOW()),
('Raluca Stoica', 'raluca.stoica@example.com', 'hashedpass8', 'TEACHER', 'https://picsum.photos/200?8', NOW()),
('Florin Munteanu', 'florin.munteanu@example.com', 'hashedpass9', 'TEACHER', 'https://picsum.photos/200?9', NOW()),
('Diana Matei', 'diana.matei@example.com', 'hashedpass10', 'TEACHER', 'https://picsum.photos/200?10', NOW()),
-- 20 studenti
('Andrei Georgescu', 'andrei.g@example.com', 's1', 'STUDENT', 'https://picsum.photos/200?11', NOW()),
('Maria Dumitrescu', 'maria.d@example.com', 's2', 'STUDENT', 'https://picsum.photos/200?12', NOW()),
('George Enescu', 'george.e@example.com', 's3', 'STUDENT', 'https://picsum.photos/200?13', NOW()),
('Alina Cristea', 'alina.c@example.com', 's4', 'STUDENT', 'https://picsum.photos/200?14', NOW()),
('Daniel Pop', 'daniel.p@example.com', 's5', 'STUDENT', 'https://picsum.photos/200?15', NOW()),
('Madalina Petrescu', 'madalina.p@example.com', 's6', 'STUDENT', 'https://picsum.photos/200?16', NOW()),
('Victor Stan', 'victor.s@example.com', 's7', 'STUDENT', 'https://picsum.photos/200?17', NOW()),
('Sorina Ilie', 'sorina.i@example.com', 's8', 'STUDENT', 'https://picsum.photos/200?18', NOW()),
('Gabriel Vasile', 'gabriel.v@example.com', 's9', 'STUDENT', 'https://picsum.photos/200?19', NOW()),
('Alexandru Tudor', 'alex.t@example.com', 's10', 'STUDENT', 'https://picsum.photos/200?20', NOW()),
('Oana Neagu', 'oana.n@example.com', 's11', 'STUDENT', 'https://picsum.photos/200?21', NOW()),
('Paul Marinescu', 'paul.m@example.com', 's12', 'STUDENT', 'https://picsum.photos/200?22', NOW()),
('Cristina Rusu', 'cristina.r@example.com', 's13', 'STUDENT', 'https://picsum.photos/200?23', NOW()),
('Ionut Barbu', 'ionut.b@example.com', 's14', 'STUDENT', 'https://picsum.photos/200?24', NOW()),
('Raluca Iacob', 'raluca.i@example.com', 's15', 'STUDENT', 'https://picsum.photos/200?25', NOW()),
('Mihnea Dragomir', 'mihnea.d@example.com', 's16', 'STUDENT', 'https://picsum.photos/200?26', NOW()),
('Laura Preda', 'laura.p@example.com', 's17', 'STUDENT', 'https://picsum.photos/200?27', NOW()),
('Sebastian Dima', 'sebastian.d@example.com', 's18', 'STUDENT', 'https://picsum.photos/200?28', NOW()),
('Adina Florescu', 'adina.f@example.com', 's19', 'STUDENT', 'https://picsum.photos/200?29', NOW()),
('Cosmin Petcu', 'cosmin.p@example.com', 's20', 'STUDENT', 'https://picsum.photos/200?30', NOW());

-- Teacher profiles (for users 1..10)
INSERT INTO teacher_profiles (user_id, city, county, price_per_hour, description, photo_url, rating_avg, created_at) VALUES
(1, 'Cluj-Napoca', 'Cluj', 70.00, 'Profesor cu experiență liceu.', 'https://picsum.photos/200?101', 4.80, NOW()),
(2, 'București', 'București', 100.00, 'Meditatii pentru BAC/admitere.', 'https://picsum.photos/200?102', 4.50, NOW()),
(3, 'Iași', 'Iași', 60.00, 'Meditatii interactive, răbdare.', 'https://picsum.photos/200?103', 4.20, NOW()),
(4, 'Brașov', 'Brașov', 80.00, 'Fizică pentru liceu.', 'https://picsum.photos/200?104', 4.60, NOW()),
(5, 'Constanța', 'Constanța', 90.00, 'Chimie pentru olimpiade.', 'https://picsum.photos/200?105', 4.70, NOW()),
(6, 'Timișoara', 'Timiș', 75.00, 'Română + eseuri BAC.', 'https://picsum.photos/200?106', 4.30, NOW()),
(7, 'Oradea', 'Bihor', 65.00, 'Matematica gimnaziu + liceu.', 'https://picsum.photos/200?107', 4.40, NOW()),
(8, 'Galați', 'Galați', 85.00, 'Biologie generală.', 'https://picsum.photos/200?108', 4.10, NOW()),
(9, 'Sibiu', 'Sibiu', 95.00, 'Engleză conversațională.', 'https://picsum.photos/200?109', 4.90, NOW()),
(10, 'Craiova', 'Dolj', 55.00, 'Istorie pentru BAC.', 'https://picsum.photos/200?110', 4.00, NOW());

-- Teacher -> subject mappings
INSERT INTO teacher_subjects (teacher_id, subject_id) VALUES
(1, 1),(1,4),    -- Ana: Matematica, Fizica
(2, 2),(2,3),    -- Mihai: Romana, Engleza
(3, 1),(3,6),    -- Ioana: Matematica, Informatica
(4, 4),          -- Radu: Fizica
(5, 5),          -- Simona: Chimie
(6, 2),          -- Carmen: Romana
(7, 1),          -- Andrei: Matematica
(8, 7),          -- Raluca: Biologie
(9, 3),          -- Florin: Engleza
(10, 8);         -- Diana: Istorie

-- Reviews (some students rate some teachers)
INSERT INTO reviews (teacher_id, user_id, rating, comment, created_at) VALUES
(1, 11, 5, 'Explică clar și răbdător.', NOW()),
(1, 12, 4, 'Bună dar uneori ocupată.', NOW()),
(1, 13, 5, 'Foarte utilă pentru lecții.', NOW()),
(2, 14, 5, 'M-a ajutat enorm pentru BAC.', NOW()),
(2, 15, 4, 'Lucrăm bine la eseuri.', NOW()),
(3, 16, 3, 'Explică ok dar se grăbește.', NOW()),
(4, 17, 5, 'Super prof de fizică!', NOW()),
(5, 18, 4, 'Foarte bună la chimie.', NOW()),
(6, 19, 5, 'Eseurile au devenit mai simple!', NOW()),
(7, 20, 3, 'Explică ok dar lipsit de materiale.', NOW()),
(8, 11, 4, 'Bine pregătită pentru test.', NOW()),
(9, 12, 5, 'Excelentă la engleză!', NOW()),
(10, 13, 4, 'Prof de istorie ok.', NOW());

-- Favorites (students bookmarking teachers)
INSERT INTO favorites (user_id, teacher_id, created_at) VALUES
(11,1,NOW()),(12,2,NOW()),(13,3,NOW()),(14,4,NOW()),
(15,5,NOW()),(16,6,NOW()),(17,7,NOW()),(18,8,NOW()),
(19,9,NOW()),(20,10,NOW()),
(21,1,NOW()),(22,2,NOW()),(23,3,NOW()),(24,4,NOW());

-- Messages (sample threads)
INSERT INTO messages (sender_id, receiver_id, content, created_at) VALUES
(11,1,'Bună ziua, vreau meditații la mate.',NOW()),
(1,11,'Sigur, putem stabili orarul.',NOW()),
(12,2,'Pregătiți pentru BAC română?',NOW()),
(2,12,'Da, lucrăm pe eseuri și gramatică.',NOW()),
(13,9,'Faceți engleză conversațională?',NOW()),
(9,13,'Da, putem face simulări de interviu.',NOW()),
(14,4,'Bună, cât costa o ședință?',NOW()),
(4,14,'80 RON / oră, depinde de nivel.',NOW()),
(15,5,'Aveți materiale pentru chimie?',NOW()),
(5,15,'Da, trimit pe email după prima lecție.',NOW());

-- Reports (example)
INSERT INTO reports (reported_user_id, reporter_id, reason, created_at) VALUES
(3, 11, 'Nu a răspuns la mesaje.', NOW()),
(6, 14, 'Comunicare neadecvată.', NOW());

-- Attachments example (tie to first message id; ensure message ids exist)
-- We'll attach to message id 1 and 3 etc.
INSERT INTO attachments (message_id, file_url, file_type, created_at) VALUES
(1, 'https://example.com/homework1.pdf', 'pdf', NOW()),
(3, 'https://example.com/material_bac.zip', 'zip', NOW());

-- End of seed
