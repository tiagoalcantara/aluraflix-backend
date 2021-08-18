CREATE TABLE public.videos (
	id serial NOT NULL,
	title varchar NOT NULL,
	description varchar NOT NULL,
	url varchar NOT NULL,
	CONSTRAINT videos_pk PRIMARY KEY (id)
);
