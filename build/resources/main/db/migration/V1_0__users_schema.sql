-- CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;
--
-- CREATE TABLE users (
--   id UUID NOT NULL,
--    email VARCHAR(255),
--    name VARCHAR(255),
--    password VARCHAR(255),
--    role VARCHAR(255),
--    avatar VARCHAR(255),
--    CONSTRAINT pk_users PRIMARY KEY (id)
-- );
--
-- CREATE TABLE news (
--   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--    description VARCHAR(255),
--    image VARCHAR(255),
--    title VARCHAR(255),
--    username VARCHAR(255),
--    user_id UUID,
--    CONSTRAINT pk_news PRIMARY KEY (id)
-- );
--
-- ALTER TABLE news ADD CONSTRAINT FK_NEWS_ON_USERID FOREIGN KEY (user_id) REFERENCES users (id);
--
-- CREATE SEQUENCE  IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;
--
-- CREATE TABLE tags (
--   id BIGINT NOT NULL,
--    title VARCHAR(255),
--    news_id BIGINT,
--    CONSTRAINT pk_tags PRIMARY KEY (id)
-- );
--
-- ALTER TABLE tags ADD CONSTRAINT FK_TAGS_ON_NEWS FOREIGN KEY (news_id) REFERENCES news (id);
--
-- CREATE TABLE logs (
--   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--    method VARCHAR(255),
--    status_code INTEGER,
--    created_at TIMESTAMP WITHOUT TIME ZONE,
--    CONSTRAINT pk_logs PRIMARY KEY (id)
-- );