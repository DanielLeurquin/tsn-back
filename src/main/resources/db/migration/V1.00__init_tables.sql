CREATE TABLE public.user
(
    id varchar not null constraint user_id_pkey primary key,
    role varchar,
    password varchar,
    email varchar
);