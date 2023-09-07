CREATE OR REPLACE FUNCTION verify_accepted_answer_question_id()
    RETURNS TRIGGER LANGUAGE PLPGSQL AS $$
DECLARE
    question_id INTEGER;
BEGIN
    question_id := a.question_id FROM posts.answer a WHERE a.id = NEW.accepted_answer_id;
    IF NEW.id <> question_id THEN
        RAISE EXCEPTION 'Answer % is not an answer to question %.', NEW.accepted_answer_id, NEW.id
            USING HINT = 'Check that the answer is indeed an answer to this question.';
    END IF;
   RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS accepted_answer_question_id_update ON posts.question;
CREATE TRIGGER accepted_answer_question_id_update
    BEFORE UPDATE ON posts.question
    FOR EACH ROW EXECUTE PROCEDURE verify_accepted_answer_question_id();


CREATE OR REPLACE FUNCTION verify_accepted_answer_question_id_is_null()
    RETURNS TRIGGER LANGUAGE PLPGSQL AS $$
DECLARE
BEGIN
	IF NEW.accepted_answer_id IS NOT NULL THEN
	    RAISE EXCEPTION 'Question cannot have an accepted answer before being asked.'
			USING HINT = 'Create the question, create the answers, then update the accepted_answer_id';
	END IF;
	RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS accepted_answer_question_id_is_null_on_insert ON posts.question;
CREATE TRIGGER accepted_answer_question_id_is_null_on_insert
    BEFORE INSERT ON posts.question
    FOR EACH ROW EXECUTE PROCEDURE verify_accepted_answer_question_id_is_null();
