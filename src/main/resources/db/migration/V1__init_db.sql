create sequence public.bill_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

alter sequence public.bill_seq
    OWNER TO bills;

-- SEQUENCE: public.bill_operation_id_seq

-- DROP SEQUENCE public.bill_operation_id_seq;

create sequence public.bill_operation_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

alter sequence public.bill_operation_seq
    OWNER TO bills;

-- SEQUENCE: public.driver_id_seq

-- DROP SEQUENCE public.driver_id_seq;

create sequence public.driver_seq
    increment 1
    start 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;

alter sequence public.driver_seq
    OWNER TO bills;


-- Table: public.driver

-- DROP TABLE public.driver;

create TABLE public.driver
(
    id bigint NOT NULL DEFAULT nextval('driver_seq'::regclass),
    fio character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT driver_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

alter table public.driver
    OWNER to bills;


-- Table: public.bill

-- DROP TABLE public.bill;

create TABLE public.bill
(
    id bigint NOT NULL DEFAULT nextval('bill_seq'::regclass),
    current_sum numeric(19,2) NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    driver_id bigint NOT NULL,
    CONSTRAINT bill_pkey PRIMARY KEY (id),
    CONSTRAINT fkjwsmshekrpi6e3t1aba25r44m FOREIGN KEY (driver_id)
        REFERENCES public.driver (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete NO ACTION
)

TABLESPACE pg_default;

alter table public.bill
    OWNER to bills;

-- Table: public.bill_operation

-- DROP TABLE public.bill_operation;

create TABLE public.bill_operation
(
    id bigint NOT NULL DEFAULT nextval('bill_operation_seq'::regclass),
    date timestamp without time zone,
    sum numeric(19,2),
    type character varying(255) COLLATE pg_catalog."default",
    bill_id bigint,
    CONSTRAINT bill_operation_pkey PRIMARY KEY (id),
    CONSTRAINT fkasbkfpxruycrk3pcueeqmu7gb FOREIGN KEY (bill_id)
        REFERENCES public.bill (id) MATCH SIMPLE
        ON update NO ACTION
        ON delete NO ACTION
)

TABLESPACE pg_default;

alter table public.bill_operation
    OWNER to bills;


insert into public.driver(
	id, fio)
	VALUES (1, 'Петров Петр Петрович');


insert into public.driver(
	id, fio)
	VALUES (2, 'Васильев Василий Васильевич');

insert into public.driver(
	id, fio)
	VALUES (3, 'Иванов Иван Иванович');

insert into public.bill (current_sum, name, driver_id)
	VALUES (5000,'счет 1',1);

insert into public.bill (current_sum, name, driver_id)
	VALUES (10000,'счет 2',1);

insert into public.bill (current_sum, name, driver_id)
	VALUES (33000,'счет1',2);

insert into public.bill (current_sum, name, driver_id)
	VALUES (600000,'счет 1',3);