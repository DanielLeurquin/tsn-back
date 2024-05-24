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
    post_text varchar,
    created_at timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE public.subject
(
    subject_name varchar not null constraint subject_name_pkey primary key

);

CREATE TABLE public.user_subject
(
    user_id varchar,
    subject_name varchar
);