CREATE TABLE books(
bookId integer NOT NULL PRIMARY KEY IDENTITY (1, 1),
title character varying(100) NOT NULL,
author character varying (50),
category character varying(30),
releaseDate character varying(30),
fileSize integer,
filePath character varying(150),
numParagraph integer,
numLines integer,
numWords integer,
numSentences integer,
numCharacters integer
);