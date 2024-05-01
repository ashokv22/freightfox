create database demo;
use demo;

-- Task 1 Dynamic PDF Generator

create table pdf_files (
	id int  AUTO_INCREMENT PRIMARY KEY,
	buyer_gstin varchar(50) NOT NULL,
	seller_gstin varchar(50) NOT NULL,
	file_path varchar(255) NOT NULL
);

-- Task 2 Distance between pin codes

create table distance (
	id int  AUTO_INCREMENT PRIMARY KEY,
	source_pincode varchar(6) NOT NULL,
	destination_pincode varchar(6) NOT NULL,
	distance varchar(50) NOT NULL,
	duration varchar(50) NOT NULL
);

-- Task 3 Weather Info with GeoDecoding

create table pincodes (
	id int  AUTO_INCREMENT PRIMARY KEY,
	pincode varchar(6) not null,
	latitude double not null,
	longitude double not null
);

create table weather_data (
	id int  AUTO_INCREMENT PRIMARY KEY,
	pincode varchar(6) not null,
	for_date date not null,
	description varchar(255) not null,
	temperature double not null,
	humidity double
);


-- Task 4 Meeting Assistant

create table employee (
	id int  AUTO_INCREMENT PRIMARY KEY,
	name varchar(50) not null
);

CREATE TABLE MeetingSlot (
  id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL
);

CREATE TABLE meetings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  owner_id INT NOT NULL,
  participant_id INT NOT NULL
  FOREIGN KEY (owner_id) REFERENCES employee(id),
  FOREIGN KEY (participant_id) REFERENCES employee(id)
);
