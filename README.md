# Blog
### Language 
Server clojure 
[Clojure](http://clojure.org)

Web Ssrver 
[Ring](https://github.com/ring-clojure/ring)

Web framwork 
[Luminusweb](http://www.luminusweb.net/)

Template
[Fleet](https://github.com/Flamefork/fleet)     [Clabango](https://github.com/danlarkin/clabango)


ORM [SQLkorma](http://sqlkorma.com/)

MarkDownEdior [https://github.com/miclle/Markdown-Editor](https://github.com/miclle/Markdown-Editor)
### Storeage
[Postgresql](http://postgresql.org)

### Install 
Create database use follow sql in postgresql!
CREATE DATABASE blog;

CREATE TABLE content
(
  title text,
  content text,
  "created-at" timestamp with time zone,
  "updated-at" timestamp with time zone,
  tag text,
  id bigserial NOT NULL
)

CREATE TABLE users
(
  email character varying(30),
  admin boolean,
  last_login time without time zone,
  is_active boolean,
  pass character varying(100),
  id bigserial NOT NULL
)