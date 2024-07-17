CREATE DATABASE hospital;

USE hospital;

CREATE TABLE patients(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULl,
    gender VARCHAR(10) NOT NULL,
    disease VARCHAR(255)
);

CREATE TABLE doctors(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    specialization VARCHAR(255) NOT NULL
);

CREATE TABLE appointments(
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

INSERT INTO doctors(name, gender, specialization) VALUES("Dr. Pankaj Jain", "Male", "Physician");
INSERT INTO doctors(name, gender, specialization) VALUES("Dr. Sayanti Karak", "Female", "Dental");
INSERT INTO doctors(name, gender, specialization) VALUES("Dr. Nabanita Sarkar", "Male", "Gynecologist");
INSERT INTO doctors(name, gender, specialization) VALUES("Dr. Madhabi Ruma", "Female", "Cardiologist");

