CREATE TABLE public.categories (
	id serial NOT NULL,
	title varchar NOT NULL,
	color varchar NOT NULL,
	CONSTRAINT categories_pk PRIMARY KEY (id)
);

ALTER TABLE public.videos ADD id_category integer NOT NULL;
ALTER TABLE public.videos ADD CONSTRAINT videos_fk FOREIGN KEY (id_category) REFERENCES public.categories(id);
