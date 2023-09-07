DROP SCHEMA IF EXISTS posts CASCADE;
DROP SCHEMA IF EXISTS users CASCADE;
DROP SCHEMA IF EXISTS logging CASCADE;

CREATE SCHEMA posts;
CREATE SCHEMA users;
CREATE SCHEMA logging;

CREATE TABLE posts.answer (
id int8 NOT NULL,
question_id int8 NOT NULL,
PRIMARY KEY (id));

CREATE TABLE posts.comment (
id int8 NOT NULL,
TEXT TEXT NOT NULL,
author_id uuid NOT NULL,
post_id int8 NOT NULL,
deleted_date timestamp,
created_date timestamp NOT NULL,
last_modified_date timestamp NOT NULL,
PRIMARY KEY (id));

CREATE TABLE posts.post (
id int8 NOT NULL,
text text NOT NULL,
author_id uuid NOT NULL,
deleted_date timestamp,
created_date timestamp NOT NULL,
last_modified_date timestamp NOT NULL,
PRIMARY KEY (id));

CREATE TABLE posts.question (
id int8 NOT NULL,
title varchar(200) NOT NULL,
views int4 NOT NULL CHECK (views >= 0),
accepted_answer_id int8,
PRIMARY KEY (id));

CREATE TABLE posts.question_tags (
questions_id int8 NOT NULL,
tags_id int8 NOT NULL,
PRIMARY KEY (questions_id, tags_id));

CREATE TABLE posts.tag (
id int8 NOT NULL,
name varchar(100) NOT NULL,
description varchar(1000) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE users.authority (
id int8 NOT NULL,
name varchar(100) NOT NULL,
PRIMARY KEY (id));

CREATE TABLE users.qa_user (
id uuid NOT NULL,
username varchar(64) NOT NULL,
email varchar(255) NOT NULL,
name varchar(500) NOT NULL,
password varchar(60) NOT NULL,
about TEXT NOT NULL,
deleted_date timestamp,
created_date timestamp NOT NULL,
last_modified_date timestamp NOT NULL,
PRIMARY KEY (id));

CREATE TABLE users.qa_user_authorities (
user_id uuid NOT NULL,
authorities_id int8 NOT NULL,
PRIMARY KEY (user_id, authorities_id));

CREATE TABLE users.reputation (
id int8 NOT NULL,
points int4 NOT NULL,
PRIMARY KEY (id));

CREATE TABLE logging.event (
id int8 NOT NULL,
name varchar(255) NOT NULL,
description TEXT NOT NULL,
PRIMARY KEY (id));

CREATE TABLE logging.event_logging (
id int8 NOT NULL,
event_id int8 NOT NULL,
initiator_id uuid NOT NULL,
subject_id uuid,
timestamp timestamp NOT NULL,
PRIMARY KEY (id));

CREATE TABLE logging.event_logging_post (
id int8 NOT NULL,
post_id int8 NOT NULL,
PRIMARY KEY (id));

ALTER TABLE IF EXISTS logging.event ADD CONSTRAINT uk_event_name UNIQUE (name);
ALTER TABLE IF EXISTS posts.tag ADD CONSTRAINT uk_tag_name UNIQUE (name);
ALTER TABLE IF EXISTS users.authority ADD CONSTRAINT uk_qa_user_name UNIQUE (name);
ALTER TABLE IF EXISTS users.qa_user ADD CONSTRAINT uk_qa_user_email UNIQUE (email);
ALTER TABLE IF EXISTS users.qa_user ADD CONSTRAINT uk_qa_user_username UNIQUE (username);

ALTER TABLE IF EXISTS logging.event_logging ADD CONSTRAINT fk_event_logging_event_id FOREIGN KEY (event_id) REFERENCES logging.event;
ALTER TABLE IF EXISTS logging.event_logging ADD CONSTRAINT fk_event_logging_subject_id FOREIGN KEY (subject_id) REFERENCES users.qa_user;
ALTER TABLE IF EXISTS logging.event_logging ADD CONSTRAINT fk_event_logging_initiator_id FOREIGN KEY (initiator_id) REFERENCES users.qa_user;
ALTER TABLE IF EXISTS logging.event_logging_post ADD CONSTRAINT fk_event_logging_post_post_id FOREIGN KEY (post_id) REFERENCES posts.post;
ALTER TABLE IF EXISTS logging.event_logging_post ADD CONSTRAINT fk_event_logging_post_id FOREIGN KEY (id) REFERENCES logging.event_logging;
ALTER TABLE IF EXISTS posts.answer ADD CONSTRAINT fk_answer_question_id FOREIGN KEY (question_id) REFERENCES posts.question;
ALTER TABLE IF EXISTS posts.answer ADD CONSTRAINT fk_answer_id FOREIGN KEY (id) REFERENCES posts.post;
ALTER TABLE IF EXISTS posts.comment ADD CONSTRAINT fk_comment_author_id FOREIGN KEY (author_id) REFERENCES users.qa_user;
ALTER TABLE IF EXISTS posts.comment ADD CONSTRAINT fk_comment_post_id FOREIGN KEY (post_id) REFERENCES posts.post;
ALTER TABLE IF EXISTS posts.post ADD CONSTRAINT fk_post_author_id FOREIGN KEY (author_id) REFERENCES users.qa_user;
ALTER TABLE IF EXISTS posts.question ADD CONSTRAINT fk_post_accepted_answer_id FOREIGN KEY (accepted_answer_id) REFERENCES posts.answer;
ALTER TABLE IF EXISTS posts.question ADD CONSTRAINT fk_question_id FOREIGN KEY (id) REFERENCES posts.post;
ALTER TABLE IF EXISTS posts.question_tags ADD CONSTRAINT fk_question_tags_tags_id FOREIGN KEY (tags_id) REFERENCES posts.tag;
ALTER TABLE IF EXISTS posts.question_tags ADD CONSTRAINT fk_question_tags_questions_id FOREIGN KEY (questions_id) REFERENCES posts.question;
ALTER TABLE IF EXISTS users.qa_user_authorities ADD CONSTRAINT fk_qa_user_authorities_authorities_id FOREIGN KEY (authorities_id) REFERENCES users.authority;
ALTER TABLE IF EXISTS users.qa_user_authorities ADD CONSTRAINT fk_qa_user_authorities_user_id FOREIGN KEY (user_id) REFERENCES users.qa_user;
ALTER TABLE IF EXISTS users.reputation ADD CONSTRAINT fk_reputation_id FOREIGN KEY (id) REFERENCES logging.event;

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 101 INCREMENT 1;

-- Get all the points from each user
CREATE OR REPLACE VIEW users.user_reputation
AS SELECT author_id user_id, SUM(reputation) reputation
	FROM (
		SELECT author_id, SUM(points) reputation
	    FROM logging.event_logging el
		INNER JOIN logging.event_logging_post elp ON elp.id = el.id
		INNER JOIN posts.post p ON elp.post_id = p.id
		INNER JOIN users.reputation r ON r.id = el.event_id
		GROUP BY p.author_id
		UNION
		SELECT el.subject_id AS author_id, SUM(points) reputation
		FROM  logging.event_logging el
		INNER JOIN users.reputation r ON r.id = el.event_id
		WHERE el.subject_id IS NOT NULL
		GROUP BY subject_id
	) reputation
	GROUP BY author_id
	ORDER BY reputation DESC
;
