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
