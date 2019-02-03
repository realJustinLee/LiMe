CREATE TABLE `users`
(
  `id`       int(11)                       NOT NULL,
  `username` varchar(50) COLLATE utf8_bin  NOT NULL,
  `password` varchar(50) COLLATE utf8_bin  NOT NULL,
  `gender`   varchar(20) COLLATE utf8_bin  NOT NULL,
  `email`    varchar(100) COLLATE utf8_bin NOT NULL,
  `banned`   tinyint(1)                    NOT NULL DEFAULT '0'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;


SELECT *
FROM users;

INSERT INTO users(username, password, gender, email)
VALUES ('lixin', 'lixin', 'male', 'JustinDellAdam@live.com');

SELECT password
FROM users
WHERE username = 'lixin'
  AND banned = FALSE;

UPDATE users
SET banned = FALSE
WHERE username = 'lixin';