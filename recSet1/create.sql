--Nazwa, użytkownik i hasło na które są skonfigurowane programy javy to mydb, postgres asdasd

CREATE TABLE ip_in(
	ip VARCHAR PRIMARY KEY NOT NULL
	);

CREATE TABLE ip_out(
   ip VARCHAR PRIMARY KEY NOT NULL,
   batch_id INTEGER NOT NULL,
   t_stamp TIMESTAMP,
   country VARCHAR NOT NULL
   FOREIGN KEY (country) REFERENCES countries (name)
   );

CREATE TABLE countries(
	name varchar PRIMARY KEY NOT NULL,
	code char(2) NOT NULL,
	code3 char(3) NOT NULL,
	emoji varchar NOT NULL
	);

DO $$
	DECLARE counter INTEGER := 1;
	BEGIN
		WHILE counter < 101 LOOP
			INSERT INTO ip_in (ip) VALUES(concat(counter,'.1.2.3'));
			counter := counter+1;
		END LOOP;
END $$;

SELECT * FROM ip_in;