-- Score earned by each user (THIS IS USED AS VIEW in schema.sql)
SELECT author_id user_id, SUM(reputation) reputation
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

-- Number of UPVOTES and DOWNVOTES for each post
SELECT 
post_id,
SUM(
	CASE
		WHEN event_id = 2 THEN 1
		WHEN event_id = 3 THEN -1
	END
) score
FROM logging.event_logging el
INNER JOIN logging.event_logging_post elp ON elp.id = el.id
WHERE event_id IN (2, 3)
--AND post_id = ?
GROUP BY post_id, event_id
ORDER BY score DESC
;

-- How many upvotes and downvotes each post has
SELECT e."name", post_id, event_id, COUNT(event_id)
FROM logging.event_logging el
INNER JOIN logging.event_logging_post elp ON elp.id = el.id
INNER JOIN logging."event" e ON e.id = el.event_id
WHERE event_id IN (2, 3)
--AND post_id = ?
GROUP BY post_id, event_id, e."name"
--ORDER BY post_id DESC
;

-- How many answers each question has on average per year
SELECT AVG(number_answers) FROM (
	SELECT COUNT(*) number_answers, date_trunc('year', p.created_date) "year" FROM posts.answer a
	INNER JOIN posts.post p ON p.id = a.id
	INNER JOIN posts.question q ON a.question_id = q.id
	GROUP BY a.question_id, "year"
) number_answers_question
;

-- The tags with most questions by year (each question can have more than one tag)
SELECT t."name", COUNT(q.id) n_posts, date_part('year', p.created_date) "year"
FROM posts.question q
INNER JOIN posts.post p ON p.id = q.id 
INNER JOIN posts.question_tags qt ON qt.questions_id = q.id
INNER JOIN posts.tag t ON t.id = qt.tags_id
GROUP BY qt.tags_id, t."name", "year"
HAVING COUNT(*) > 1
ORDER BY n_posts DESC
;

-- The tags with most answers by year
SELECT qt.tags_id, t."name", COUNT(*) number_answers,
date_part('year', p.created_date) "year"
FROM posts.answer a
INNER JOIN posts.post p ON p.id = a.id 
INNER JOIN posts.question_tags qt ON qt.questions_id = a.question_id 
INNER JOIN posts.tag t ON t.id = qt.tags_id  
WHERE date_part('year', p.created_date) BETWEEN 2014 AND 2016
GROUP BY qt.tags_id, t."name", "year"
ORDER BY qt.tags_id, "year"
;

-- Number of upvotes and dowvotes by month counting all years
SELECT
event_id,
SUM(
	CASE
		WHEN event_id = 2 THEN 1
		WHEN event_id = 3 THEN -1
	END
) score, 
to_char(el."timestamp", 'Month') "month"
FROM logging.event_logging el
INNER JOIN logging.event_logging_post elp ON elp.id = el.id
WHERE event_id IN (2, 3)
GROUP BY event_id, to_char(el."timestamp", 'Month'), date_part('month', el."timestamp")
ORDER BY date_part('month', el."timestamp")
;

-- Users that most commented each month counting all years having more than 1 comment
SELECT c.author_id, COUNT(*), to_char(c.created_date, 'Month') "month"
FROM posts."comment" c 
GROUP BY c.author_id,
date_part('month', c.created_date),
to_char(c.created_date, 'Month')
HAVING COUNT(*) > 1
ORDER BY date_part('month', c.created_date)
;

-- Tags with the most votes (Downvotes or Upvotes - not score)
SELECT t."name", COUNT(*) number_votes
FROM logging.event_logging el
INNER JOIN logging.event_logging_post elp ON elp.id = el.id 
INNER JOIN (
	SELECT ps.id, (
		CASE WHEN ans.question_id IS NULL
			THEN ps.id ELSE ans.question_id
		END
	) question_id
	FROM posts.post ps
	LEFT JOIN posts.answer ans ON ans.id = ps.id 
) p ON p.id = elp.post_id
INNER JOIN posts.question_tags qt ON qt.questions_id = p.question_id
INNER JOIN posts.tag t ON t.id = qt.tags_id
WHERE el.event_id IN (2, 3)
GROUP BY qt.tags_id, t."name"
;

-- The users who most voted on each year (excludins
SELECT DISTINCT ON ("year") qu.username, COUNT(*) number_votes, date_part('year', el."timestamp") "year"
FROM logging.event_logging el
INNER JOIN logging.event_logging_post elp ON elp.id = el.id 
INNER JOIN (
	SELECT ps.id, (
		CASE WHEN ans.question_id IS NULL
			THEN ps.id ELSE ans.question_id
		END
	) question_id
	FROM posts.post ps
	LEFT JOIN posts.answer ans ON ans.id = ps.id 
) p ON p.id = elp.post_id
INNER JOIN users.qa_user qu ON qu.id = el.initiator_id
WHERE el.event_id IN (2, 3)
GROUP BY qu.username, date_part('year', el."timestamp"), to_char(el."timestamp", 'year')
HAVING COUNT(*) > 1
ORDER BY "year", number_votes DESC, username
;


