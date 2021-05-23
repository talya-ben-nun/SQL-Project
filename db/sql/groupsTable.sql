CREATE TABLE groups(
groupId integer NOT NULL PRIMARY KEY IDENTITY (1, 1),
groupName character varying(100) NOT NULL
);


CREATE TABLE words(
wordId integer NOT NULL PRIMARY KEY IDENTITY (1, 1),
word character varying(400) NOT NULL,
title character varying(200) NOT NULL,
paragraph integer,
sentence integer,
indexSentence integer
);


CREATE TABLE wordInGroup(
groupId integer,
wordId integer,
FOREIGN KEY (groupId) REFERENCES groups(groupId),
FOREIGN KEY (wordId) REFERENCES words(wordId)
);


CREATE TABLE wordInBook(
bookId integer,
wordId2 integer,
FOREIGN KEY (bookId) REFERENCES books(bookId),
FOREIGN KEY (wordId2) REFERENCES words(wordId)
);