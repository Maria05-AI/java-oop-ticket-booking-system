CREATE DATABASE travel_booking_db;
USE travel_booking_db;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)    NOT NULL,
  email VARCHAR(100)   NOT NULL UNIQUE,
  phone VARCHAR(20),
  password VARCHAR(100) NOT NULL,
  role ENUM('Passenger','Admin') DEFAULT 'Passenger'
);

CREATE TABLE reviews (
  review_id INT AUTO_INCREMENT PRIMARY KEY,
  rating    INT        CHECK (rating BETWEEN 1 AND 5),
  comment   TEXT,
  user_id   INT,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tickets (
  ticket_id    INT AUTO_INCREMENT PRIMARY KEY,
  passenger_id INT,
  type         ENUM('VIP','Standard') NOT NULL,
  price        DECIMAL(8,2),
  seat_number  INT,
  FOREIGN KEY (passenger_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE schedules (
  schedule_id         INT AUTO_INCREMENT PRIMARY KEY,
  transportation_type ENUM('Bus','Train')  NOT NULL,
  departure_time      TIME,
  arrival_time        TIME
);

CREATE TABLE reservations (
  reservation_id INT AUTO_INCREMENT PRIMARY KEY,
  schedule_id    INT,
  passenger_id   INT,
  status         ENUM('Booked','Cancelled') DEFAULT 'Booked',
  FOREIGN KEY (schedule_id)  REFERENCES schedules(schedule_id) ON DELETE CASCADE,
  FOREIGN KEY (passenger_id) REFERENCES users(id)         ON DELETE CASCADE
);

CREATE TABLE bookings (
  booking_id     INT AUTO_INCREMENT PRIMARY KEY,
  passenger_id   INT,
  ticket_id      INT,
  status         ENUM('Booked','Cancelled') DEFAULT 'Booked',
  booking_date   DATE,
  FOREIGN KEY (passenger_id) REFERENCES users(id)           ON DELETE CASCADE,
  FOREIGN KEY (ticket_id)    REFERENCES tickets(ticket_id)  ON DELETE CASCADE
);


INSERT INTO users (name, email, phone, password, role) VALUES
  ('Admin1',  'admin1@example.com', '0501234567', 'adminpass1', 'Admin'),
  ('Admin2',  'admin2@example.com', '0509876543', 'adminpass2', 'Admin'),
  ('Fatimah', 'fatimah@gmail.com',  '0554567890', 'pass123',     'Passenger'),
  ('Ali',     'ali@gmail.com',      '0559876543', 'pass456',     'Passenger');

INSERT INTO schedules (transportation_type, departure_time, arrival_time) VALUES
  ('Train', '09:00:00', '11:00:00'),
  ('Bus',   '13:00:00', '15:30:00'),
  ('Train', '17:00:00', '19:30:00'),
  ('Bus',   '08:00:00', '10:00:00');

INSERT INTO reviews (rating, comment, user_id) VALUES
  (5, 'Great service!',            3),  -- Fatimah
  (4, 'Very comfortable journey.', 4),  -- Ali
  (3, 'Could improve timing.',     4),
  (5, 'Loved the VIP service.',    3);

INSERT INTO tickets (passenger_id, type, price, seat_number) VALUES
  (3, 'VIP',      150.00, 1),
  (3, 'Standard',  80.00, 2),
  (4, 'VIP',      140.00, 5),
  (4, 'Standard',  75.00, 8);

INSERT INTO reservations (schedule_id, passenger_id, status) VALUES
  (1, 3, 'Booked'),
  (2, 3, 'Booked'),
  (3, 4, 'Booked'),
  (4, 4, 'Cancelled');

INSERT INTO bookings (passenger_id, ticket_id, status, booking_date) VALUES
  (3, 1, 'Booked',    '2025-05-02'),
  (3, 2, 'Booked',    '2025-05-02'),
  (4, 3, 'Booked',    '2025-05-03'),
  (4, 4, 'Cancelled', '2025-05-03');