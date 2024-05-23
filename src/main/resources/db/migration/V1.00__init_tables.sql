CREATE TABLE public.user
(
    id varchar not null constraint user_id_pkey primary key,
    role varchar,
    password varchar,
    email varchar
);

CREATE TABLE public.user_friend
(
    user1 varchar,
    user2 varchar
);

CREATE TABLE public.post
(
    id bigserial not null constraint post_id_pkey primary key,
    user_id varchar,
    post_text varchar
);

CREATE TABLE public.subject
(
    id bigserial not null constraint subject_id_pkey primary key,
    subject_name varchar
);