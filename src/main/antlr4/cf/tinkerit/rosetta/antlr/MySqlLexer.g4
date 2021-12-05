/*
MySQL (Positive Technologies) grammar
The MIT License (MIT).
Copyright (c) 2015-2017, Ivan Kochurkin (kvanttt@gmail.com), Positive Technologies.
Copyright (c) 2017, Ivan Khudyashev (IHudyashov@ptsecurity.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

lexer grammar MySqlLexer;

channels { MYSQLCOMMENT, ERRORCHANNEL }

// SKIP

SPACE:                               [ \t\r\n]+    -> channel(HIDDEN);
SPEC_MYSQL_COMMENT:                  '/*!' .+? '*/' -> channel(MYSQLCOMMENT);
COMMENT_INPUT:                       '/*' .*? '*/' -> channel(HIDDEN);
LINE_COMMENT:                        (
                                       ('-- ' | '#') ~[\r\n]* ('\r'? '\n' | EOF) 
                                       | '--' ('\r'? '\n' | EOF) 
                                     ) -> channel(HIDDEN);


// Keywords
// Common Keywords

ADD:                                 [Aa][Dd][Dd];
ALL:                                 [Aa][Ll][Ll];
ALTER:                               [Aa][Ll][Tt][Ee][Rr];
ANALYZE:                             [Aa][Nn][Aa][Ll][Yy][Zz][Ee];
AND:                                 [Aa][Nn][Dd];
AS:                                  [Aa][Ss];
ASC:                                 [Aa][Ss][Cc];
BEFORE:                              [Bb][Ee][Ff][Oo][Rr][Ee];
BETWEEN:                             [Bb][Ee][Tt][Ww][Ee][Ee][Nn];
BOTH:                                [Bb][Oo][Tt][Hh];
BY:                                  [Bb][Yy];
CALL:                                [Cc][Aa][Ll][Ll];
CASCADE:                             [Cc][Aa][Ss][Cc][Aa][Dd][Ee];
CASE:                                [Cc][Aa][Ss][Ee];
CAST:                                [Cc][Aa][Ss][Tt];
CHANGE:                              [Cc][Hh][Aa][Nn][Gg][Ee];
CHARACTER:                           [Cc][Hh][Aa][Rr][Aa][Cc][Tt][Ee][Rr];
CHECK:                               [Cc][Hh][Ee][Cc][Kk];
COLLATE:                             [Cc][Oo][Ll][Ll][Aa][Tt][Ee];
COLUMN:                              [Cc][Oo][Ll][Uu][Mm][Nn];
CONDITION:                           [Cc][Oo][Nn][Dd][Ii][Tt][Ii][Oo][Nn];
CONSTRAINT:                          [Cc][Oo][Nn][Ss][Tt][Rr][Aa][Ii][Nn][Tt];
CONTINUE:                            [Cc][Oo][Nn][Tt][Ii][Nn][Uu][Ee];
CONVERT:                             [Cc][Oo][Nn][Vv][Ee][Rr][Tt];
CREATE:                              [Cc][Rr][Ee][Aa][Tt][Ee];
CROSS:                               [Cc][Rr][Oo][Ss][Ss];
CURRENT_USER:                        [Cc][Uu][Rr][Rr][Ee][Nn][Tt][_][Uu][Ss][Ee][Rr];
CURSOR:                              [Cc][Uu][Rr][Ss][Oo][Rr];
DATABASE:                            [Dd][Aa][Tt][Aa][Bb][Aa][Ss][Ee];
DATABASES:                           [Dd][Aa][Tt][Aa][Bb][Aa][Ss][Ee][Ss];
DECLARE:                             [Dd][Ee][Cc][Ll][Aa][Rr][Ee];
DEFAULT:                             [Dd][Ee][Ff][Aa][Uu][Ll][Tt];
DELAYED:                             [Dd][Ee][Ll][Aa][Yy][Ee][Dd];
DELETE:                              [Dd][Ee][Ll][Ee][Tt][Ee];
DESC:                                [Dd][Ee][Ss][Cc];
DESCRIBE:                            [Dd][Ee][Ss][Cc][Rr][Ii][Bb][Ee];
DETERMINISTIC:                       [Dd][Ee][Tt][Ee][Rr][Mm][Ii][Nn][Ii][Ss][Tt][Ii][Cc];
DISTINCT:                            [Dd][Ii][Ss][Tt][Ii][Nn][Cc][Tt];
DISTINCTROW:                         [Dd][Ii][Ss][Tt][Ii][Nn][Cc][Tt][Rr][Oo][Ww];
DROP:                                [Dd][Rr][Oo][Pp];
EACH:                                [Ee][Aa][Cc][Hh];
ELSE:                                [Ee][Ll][Ss][Ee];
ELSEIF:                              [Ee][Ll][Ss][Ee][Ii][Ff];
ENCLOSED:                            [Ee][Nn][Cc][Ll][Oo][Ss][Ee][Dd];
ESCAPED:                             [Ee][Ss][Cc][Aa][Pp][Ee][Dd];
EXISTS:                              [Ee][Xx][Ii][Ss][Tt][Ss];
EXIT:                                [Ee][Xx][Ii][Tt];
EXPLAIN:                             [Ee][Xx][Pp][Ll][Aa][Ii][Nn];
FALSE:                               [Ff][Aa][Ll][Ss][Ee];
FETCH:                               [Ff][Ee][Tt][Cc][Hh];
FOR:                                 [Ff][Oo][Rr];
FORCE:                               [Ff][Oo][Rr][Cc][Ee];
FOREIGN:                             [Ff][Oo][Rr][Ee][Ii][Gg][Nn];
FROM:                                [Ff][Rr][Oo][Mm];
FULLTEXT:                            [Ff][Uu][Ll][Ll][Tt][Ee][Xx][Tt];
GRANT:                               [Gg][Rr][Aa][Nn][Tt];
GROUP:                               [Gg][Rr][Oo][Uu][Pp];
HAVING:                              [Hh][Aa][Vv][Ii][Nn][Gg];
HIGH_PRIORITY:                       [Hh][Ii][Gg][Hh][_][Pp][Rr][Ii][Oo][Rr][Ii][Tt][Yy];
IF:                                  [Ii][Ff];
IGNORE:                              [Ii][Gg][Nn][Oo][Rr][Ee];
IN:                                  [Ii][Nn];
INDEX:                               [Ii][Nn][Dd][Ee][Xx];
INFILE:                              [Ii][Nn][Ff][Ii][Ll][Ee];
INNER:                               [Ii][Nn][Nn][Ee][Rr];
INOUT:                               [Ii][Nn][Oo][Uu][Tt];
INSERT:                              [Ii][Nn][Ss][Ee][Rr][Tt];
INTERVAL:                            [Ii][Nn][Tt][Ee][Rr][Vv][Aa][Ll];
INTO:                                [Ii][Nn][Tt][Oo];
IS:                                  [Ii][Ss];
ITERATE:                             [Ii][Tt][Ee][Rr][Aa][Tt][Ee];
JOIN:                                [Jj][Oo][Ii][Nn];
KEY:                                 [Kk][Ee][Yy];
KEYS:                                [Kk][Ee][Yy][Ss];
KILL:                                [Kk][Ii][Ll][Ll];
LEADING:                             [Ll][Ee][Aa][Dd][Ii][Nn][Gg];
LEAVE:                               [Ll][Ee][Aa][Vv][Ee];
LEFT:                                [Ll][Ee][Ff][Tt];
LIKE:                                [Ll][Ii][Kk][Ee];
LIMIT:                               [Ll][Ii][Mm][Ii][Tt];
LINEAR:                              [Ll][Ii][Nn][Ee][Aa][Rr];
LINES:                               [Ll][Ii][Nn][Ee][Ss];
LOAD:                                [Ll][Oo][Aa][Dd];
LOCK:                                [Ll][Oo][Cc][Kk];
LOOP:                                [Ll][Oo][Oo][Pp];
LOW_PRIORITY:                        [Ll][Oo][Ww][_][Pp][Rr][Ii][Oo][Rr][Ii][Tt][Yy];
MASTER_BIND:                         [Mm][Aa][Ss][Tt][Ee][Rr][_][Bb][Ii][Nn][Dd];
MASTER_SSL_VERIFY_SERVER_CERT:       [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Vv][Ee][Rr][Ii][Ff][Yy][_][Ss][Ee][Rr][Vv][Ee][Rr][_][Cc][Ee][Rr][Tt];
MATCH:                               [Mm][Aa][Tt][Cc][Hh];
MAXVALUE:                            [Mm][Aa][Xx][Vv][Aa][Ll][Uu][Ee];
MODIFIES:                            [Mm][Oo][Dd][Ii][Ff][Ii][Ee][Ss];
NATURAL:                             [Nn][Aa][Tt][Uu][Rr][Aa][Ll];
NOT:                                 [Nn][Oo][Tt];
NO_WRITE_TO_BINLOG:                  [Nn][Oo][_][Ww][Rr][Ii][Tt][Ee][_][Tt][Oo][_][Bb][Ii][Nn][Ll][Oo][Gg];
NULL_LITERAL:                        [Nn][Uu][Ll][Ll];
ON:                                  [Oo][Nn];
OPTIMIZE:                            [Oo][Pp][Tt][Ii][Mm][Ii][Zz][Ee];
OPTION:                              [Oo][Pp][Tt][Ii][Oo][Nn];
OPTIONALLY:                          [Oo][Pp][Tt][Ii][Oo][Nn][Aa][Ll][Ll][Yy];
OR:                                  [Oo][Rr];
ORDER:                               [Oo][Rr][Dd][Ee][Rr];
OUT:                                 [Oo][Uu][Tt];
OUTER:                               [Oo][Uu][Tt][Ee][Rr];
OUTFILE:                             [Oo][Uu][Tt][Ff][Ii][Ll][Ee];
PARTITION:                           [Pp][Aa][Rr][Tt][Ii][Tt][Ii][Oo][Nn];
PRIMARY:                             [Pp][Rr][Ii][Mm][Aa][Rr][Yy];
PROCEDURE:                           [Pp][Rr][Oo][Cc][Ee][Dd][Uu][Rr][Ee];
PURGE:                               [Pp][Uu][Rr][Gg][Ee];
RANGE:                               [Rr][Aa][Nn][Gg][Ee];
READ:                                [Rr][Ee][Aa][Dd];
READS:                               [Rr][Ee][Aa][Dd][Ss];
REFERENCES:                          [Rr][Ee][Ff][Ee][Rr][Ee][Nn][Cc][Ee][Ss];
REGEXP:                              [Rr][Ee][Gg][Ee][Xx][Pp];
RELEASE:                             [Rr][Ee][Ll][Ee][Aa][Ss][Ee];
RENAME:                              [Rr][Ee][Nn][Aa][Mm][Ee];
REPEAT:                              [Rr][Ee][Pp][Ee][Aa][Tt];
REPLACE:                             [Rr][Ee][Pp][Ll][Aa][Cc][Ee];
REQUIRE:                             [Rr][Ee][Qq][Uu][Ii][Rr][Ee];
RESTRICT:                            [Rr][Ee][Ss][Tt][Rr][Ii][Cc][Tt];
RETURN:                              [Rr][Ee][Tt][Uu][Rr][Nn];
REVOKE:                              [Rr][Ee][Vv][Oo][Kk][Ee];
RIGHT:                               [Rr][Ii][Gg][Hh][Tt];
RLIKE:                               [Rr][Ll][Ii][Kk][Ee];
SCHEMA:                              [Ss][Cc][Hh][Ee][Mm][Aa];
SCHEMAS:                             [Ss][Cc][Hh][Ee][Mm][Aa][Ss];
SELECT:                              [Ss][Ee][Ll][Ee][Cc][Tt];
SET:                                 [Ss][Ee][Tt];
SEPARATOR:                           [Ss][Ee][Pp][Aa][Rr][Aa][Tt][Oo][Rr];
SHOW:                                [Ss][Hh][Oo][Ww];
SPATIAL:                             [Ss][Pp][Aa][Tt][Ii][Aa][Ll];
SQL:                                 [Ss][Qq][Ll];
SQLEXCEPTION:                        [Ss][Qq][Ll][Ee][Xx][Cc][Ee][Pp][Tt][Ii][Oo][Nn];
SQLSTATE:                            [Ss][Qq][Ll][Ss][Tt][Aa][Tt][Ee];
SQLWARNING:                          [Ss][Qq][Ll][Ww][Aa][Rr][Nn][Ii][Nn][Gg];
SQL_BIG_RESULT:                      [Ss][Qq][Ll][_][Bb][Ii][Gg][_][Rr][Ee][Ss][Uu][Ll][Tt];
SQL_CALC_FOUND_ROWS:                 [Ss][Qq][Ll][_][Cc][Aa][Ll][Cc][_][Ff][Oo][Uu][Nn][Dd][_][Rr][Oo][Ww][Ss];
SQL_SMALL_RESULT:                    [Ss][Qq][Ll][_][Ss][Mm][Aa][Ll][Ll][_][Rr][Ee][Ss][Uu][Ll][Tt];
SSL:                                 [Ss][Ss][Ll];
STARTING:                            [Ss][Tt][Aa][Rr][Tt][Ii][Nn][Gg];
STRAIGHT_JOIN:                       [Ss][Tt][Rr][Aa][Ii][Gg][Hh][Tt][_][Jj][Oo][Ii][Nn];
TABLE:                               [Tt][Aa][Bb][Ll][Ee];
TERMINATED:                          [Tt][Ee][Rr][Mm][Ii][Nn][Aa][Tt][Ee][Dd];
THEN:                                [Tt][Hh][Ee][Nn];
TO:                                  [Tt][Oo];
TRAILING:                            [Tt][Rr][Aa][Ii][Ll][Ii][Nn][Gg];
TRIGGER:                             [Tt][Rr][Ii][Gg][Gg][Ee][Rr];
TRUE:                                [Tt][Rr][Uu][Ee];
UNDO:                                [Uu][Nn][Dd][Oo];
UNION:                               [Uu][Nn][Ii][Oo][Nn];
UNIQUE:                              [Uu][Nn][Ii][Qq][Uu][Ee];
UNLOCK:                              [Uu][Nn][Ll][Oo][Cc][Kk];
UNSIGNED:                            [Uu][Nn][Ss][Ii][Gg][Nn][Ee][Dd];
UPDATE:                              [Uu][Pp][Dd][Aa][Tt][Ee];
USAGE:                               [Uu][Ss][Aa][Gg][Ee];
USE:                                 [Uu][Ss][Ee];
USING:                               [Uu][Ss][Ii][Nn][Gg];
VALUES:                              [Vv][Aa][Ll][Uu][Ee][Ss];
WHEN:                                [Ww][Hh][Ee][Nn];
WHERE:                               [Ww][Hh][Ee][Rr][Ee];
WHILE:                               [Ww][Hh][Ii][Ll][Ee];
WITH:                                [Ww][Ii][Tt][Hh];
WRITE:                               [Ww][Rr][Ii][Tt][Ee];
XOR:                                 [Xx][Oo][Rr];
ZEROFILL:                            [Zz][Ee][Rr][Oo][Ff][Ii][Ll][Ll];


// DATA TYPE Keywords

TINYINT:                             [Tt][Ii][Nn][Yy][Ii][Nn][Tt];
SMALLINT:                            [Ss][Mm][Aa][Ll][Ll][Ii][Nn][Tt];
MEDIUMINT:                           [Mm][Ee][Dd][Ii][Uu][Mm][Ii][Nn][Tt];
INT:                                 [Ii][Nn][Tt];
INTEGER:                             [Ii][Nn][Tt][Ee][Gg][Ee][Rr];
BIGINT:                              [Bb][Ii][Gg][Ii][Nn][Tt];
REAL:                                [Rr][Ee][Aa][Ll];
DOUBLE:                              [Dd][Oo][Uu][Bb][Ll][Ee];
FLOAT:                               [Ff][Ll][Oo][Aa][Tt];
DECIMAL:                             [Dd][Ee][Cc][Ii][Mm][Aa][Ll];
NUMERIC:                             [Nn][Uu][Mm][Ee][Rr][Ii][Cc];
DATE:                                [Dd][Aa][Tt][Ee];
TIME:                                [Tt][Ii][Mm][Ee];
TIMESTAMP:                           [Tt][Ii][Mm][Ee][Ss][Tt][Aa][Mm][Pp];
DATETIME:                            [Dd][Aa][Tt][Ee][Tt][Ii][Mm][Ee];
YEAR:                                [Yy][Ee][Aa][Rr];
CHAR:                                [Cc][Hh][Aa][Rr];
VARCHAR:                             [Vv][Aa][Rr][Cc][Hh][Aa][Rr];
BINARY:                              [Bb][Ii][Nn][Aa][Rr][Yy];
VARBINARY:                           [Vv][Aa][Rr][Bb][Ii][Nn][Aa][Rr][Yy];
TINYBLOB:                            [Tt][Ii][Nn][Yy][Bb][Ll][Oo][Bb];
BLOB:                                [Bb][Ll][Oo][Bb];
MEDIUMBLOB:                          [Mm][Ee][Dd][Ii][Uu][Mm][Bb][Ll][Oo][Bb];
LONGBLOB:                            [Ll][Oo][Nn][Gg][Bb][Ll][Oo][Bb];
TINYTEXT:                            [Tt][Ii][Nn][Yy][Tt][Ee][Xx][Tt];
TEXT:                                [Tt][Ee][Xx][Tt];
MEDIUMTEXT:                          [Mm][Ee][Dd][Ii][Uu][Mm][Tt][Ee][Xx][Tt];
LONGTEXT:                            [Ll][Oo][Nn][Gg][Tt][Ee][Xx][Tt];
ENUM:                                [Ee][Nn][Uu][Mm];


// Interval type Keywords

YEAR_MONTH:                          [Yy][Ee][Aa][Rr][_][Mm][Oo][Nn][Tt][Hh];
DAY_HOUR:                            [Dd][Aa][Yy][_][Hh][Oo][Uu][Rr];
DAY_MINUTE:                          [Dd][Aa][Yy][_][Mm][Ii][Nn][Uu][Tt][Ee];
DAY_SECOND:                          [Dd][Aa][Yy][_][Ss][Ee][Cc][Oo][Nn][Dd];
HOUR_MINUTE:                         [Hh][Oo][Uu][Rr][_][Mm][Ii][Nn][Uu][Tt][Ee];
HOUR_SECOND:                         [Hh][Oo][Uu][Rr][_][Ss][Ee][Cc][Oo][Nn][Dd];
MINUTE_SECOND:                       [Mm][Ii][Nn][Uu][Tt][Ee][_][Ss][Ee][Cc][Oo][Nn][Dd];
SECOND_MICROSECOND:                  [Ss][Ee][Cc][Oo][Nn][Dd][_][Mm][Ii][Cc][Rr][Oo][Ss][Ee][Cc][Oo][Nn][Dd];
MINUTE_MICROSECOND:                  [Mm][Ii][Nn][Uu][Tt][Ee][_][Mm][Ii][Cc][Rr][Oo][Ss][Ee][Cc][Oo][Nn][Dd];
HOUR_MICROSECOND:                    [Hh][Oo][Uu][Rr][_][Mm][Ii][Cc][Rr][Oo][Ss][Ee][Cc][Oo][Nn][Dd];
DAY_MICROSECOND:                     [Dd][Aa][Yy][_][Mm][Ii][Cc][Rr][Oo][Ss][Ee][Cc][Oo][Nn][Dd];


// Group function Keywords

AVG:                                 [Aa][Vv][Gg];
BIT_AND:                             [Bb][Ii][Tt][_][Aa][Nn][Dd];
BIT_OR:                              [Bb][Ii][Tt][_][Oo][Rr];
BIT_XOR:                             [Bb][Ii][Tt][_][Xx][Oo][Rr];
COUNT:                               [Cc][Oo][Uu][Nn][Tt];
GROUP_CONCAT:                        [Gg][Rr][Oo][Uu][Pp][_][Cc][Oo][Nn][Cc][Aa][Tt];
MAX:                                 [Mm][Aa][Xx];
MIN:                                 [Mm][Ii][Nn];
STD:                                 [Ss][Tt][Dd];
STDDEV:                              [Ss][Tt][Dd][Dd][Ee][Vv];
STDDEV_POP:                          [Ss][Tt][Dd][Dd][Ee][Vv][_][Pp][Oo][Pp];
STDDEV_SAMP:                         [Ss][Tt][Dd][Dd][Ee][Vv][_][Ss][Aa][Mm][Pp];
SUM:                                 [Ss][Uu][Mm];
VAR_POP:                             [Vv][Aa][Rr][_][Pp][Oo][Pp];
VAR_SAMP:                            [Vv][Aa][Rr][_][Ss][Aa][Mm][Pp];
VARIANCE:                            [Vv][Aa][Rr][Ii][Aa][Nn][Cc][Ee];


// Common function Keywords

CURRENT_DATE:                        [Cc][Uu][Rr][Rr][Ee][Nn][Tt][_][Dd][Aa][Tt][Ee];
CURRENT_TIME:                        [Cc][Uu][Rr][Rr][Ee][Nn][Tt][_][Tt][Ii][Mm][Ee];
CURRENT_TIMESTAMP:                   [Cc][Uu][Rr][Rr][Ee][Nn][Tt][_][Tt][Ii][Mm][Ee][Ss][Tt][Aa][Mm][Pp];
LOCALTIME:                           [Ll][Oo][Cc][Aa][Ll][Tt][Ii][Mm][Ee];
CURDATE:                             [Cc][Uu][Rr][Dd][Aa][Tt][Ee];
CURTIME:                             [Cc][Uu][Rr][Tt][Ii][Mm][Ee];
DATE_ADD:                            [Dd][Aa][Tt][Ee][_][Aa][Dd][Dd];
DATE_SUB:                            [Dd][Aa][Tt][Ee][_][Ss][Uu][Bb];
EXTRACT:                             [Ee][Xx][Tt][Rr][Aa][Cc][Tt];
LOCALTIMESTAMP:                      [Ll][Oo][Cc][Aa][Ll][Tt][Ii][Mm][Ee][Ss][Tt][Aa][Mm][Pp];
NOW:                                 [Nn][Oo][Ww];
POSITION:                            [Pp][Oo][Ss][Ii][Tt][Ii][Oo][Nn];
SUBSTR:                              [Ss][Uu][Bb][Ss][Tt][Rr];
SUBSTRING:                           [Ss][Uu][Bb][Ss][Tt][Rr][Ii][Nn][Gg];
SYSDATE:                             [Ss][Yy][Ss][Dd][Aa][Tt][Ee];
TRIM:                                [Tt][Rr][Ii][Mm];
UTC_DATE:                            [Uu][Tt][Cc][_][Dd][Aa][Tt][Ee];
UTC_TIME:                            [Uu][Tt][Cc][_][Tt][Ii][Mm][Ee];
UTC_TIMESTAMP:                       [Uu][Tt][Cc][_][Tt][Ii][Mm][Ee][Ss][Tt][Aa][Mm][Pp];



// Keywords, but can be ID
// Common Keywords, but can be ID

ACCOUNT:                             [Aa][Cc][Cc][Oo][Uu][Nn][Tt];
ACTION:                              [Aa][Cc][Tt][Ii][Oo][Nn];
AFTER:                               [Aa][Ff][Tt][Ee][Rr];
AGGREGATE:                           [Aa][Gg][Gg][Rr][Ee][Gg][Aa][Tt][Ee];
ALGORITHM:                           [Aa][Ll][Gg][Oo][Rr][Ii][Tt][Hh][Mm];
ANY:                                 [Aa][Nn][Yy];
AT:                                  [Aa][Tt];
AUTHORS:                             [Aa][Uu][Tt][Hh][Oo][Rr][Ss];
AUTOCOMMIT:                          [Aa][Uu][Tt][Oo][Cc][Oo][Mm][Mm][Ii][Tt];
AUTOEXTEND_SIZE:                     [Aa][Uu][Tt][Oo][Ee][Xx][Tt][Ee][Nn][Dd][_][Ss][Ii][Zz][Ee];
AUTO_INCREMENT:                      [Aa][Uu][Tt][Oo][_][Ii][Nn][Cc][Rr][Ee][Mm][Ee][Nn][Tt];
AVG_ROW_LENGTH:                      [Aa][Vv][Gg][_][Rr][Oo][Ww][_][Ll][Ee][Nn][Gg][Tt][Hh];
BEGIN:                               [Bb][Ee][Gg][Ii][Nn];
BINLOG:                              [Bb][Ii][Nn][Ll][Oo][Gg];
BIT:                                 [Bb][Ii][Tt];
BLOCK:                               [Bb][Ll][Oo][Cc][Kk];
BOOL:                                [Bb][Oo][Oo][Ll];
BOOLEAN:                             [Bb][Oo][Oo][Ll][Ee][Aa][Nn];
BTREE:                               [Bb][Tt][Rr][Ee][Ee];
CACHE:                               [Cc][Aa][Cc][Hh][Ee];
CASCADED:                            [Cc][Aa][Ss][Cc][Aa][Dd][Ee][Dd];
CHAIN:                               [Cc][Hh][Aa][Ii][Nn];
CHANGED:                             [Cc][Hh][Aa][Nn][Gg][Ee][Dd];
CHANNEL:                             [Cc][Hh][Aa][Nn][Nn][Ee][Ll];
CHECKSUM:                            [Cc][Hh][Ee][Cc][Kk][Ss][Uu][Mm];
CIPHER:                              [Cc][Ii][Pp][Hh][Ee][Rr];
CLIENT:                              [Cc][Ll][Ii][Ee][Nn][Tt];
CLOSE:                               [Cc][Ll][Oo][Ss][Ee];
COALESCE:                            [Cc][Oo][Aa][Ll][Ee][Ss][Cc][Ee];
CODE:                                [Cc][Oo][Dd][Ee];
COLUMNS:                             [Cc][Oo][Ll][Uu][Mm][Nn][Ss];
COLUMN_FORMAT:                       [Cc][Oo][Ll][Uu][Mm][Nn][_][Ff][Oo][Rr][Mm][Aa][Tt];
COMMENT:                             [Cc][Oo][Mm][Mm][Ee][Nn][Tt];
COMMIT:                              [Cc][Oo][Mm][Mm][Ii][Tt];
COMPACT:                             [Cc][Oo][Mm][Pp][Aa][Cc][Tt];
COMPLETION:                          [Cc][Oo][Mm][Pp][Ll][Ee][Tt][Ii][Oo][Nn];
COMPRESSED:                          [Cc][Oo][Mm][Pp][Rr][Ee][Ss][Ss][Ee][Dd];
COMPRESSION:                         [Cc][Oo][Mm][Pp][Rr][Ee][Ss][Ss][Ii][Oo][Nn];
CONCURRENT:                          [Cc][Oo][Nn][Cc][Uu][Rr][Rr][Ee][Nn][Tt];
CONNECTION:                          [Cc][Oo][Nn][Nn][Ee][Cc][Tt][Ii][Oo][Nn];
CONSISTENT:                          [Cc][Oo][Nn][Ss][Ii][Ss][Tt][Ee][Nn][Tt];
CONTAINS:                            [Cc][Oo][Nn][Tt][Aa][Ii][Nn][Ss];
CONTEXT:                             [Cc][Oo][Nn][Tt][Ee][Xx][Tt];
CONTRIBUTORS:                        [Cc][Oo][Nn][Tt][Rr][Ii][Bb][Uu][Tt][Oo][Rr][Ss];
COPY:                                [Cc][Oo][Pp][Yy];
CPU:                                 [Cc][Pp][Uu];
DATA:                                [Dd][Aa][Tt][Aa];
DATAFILE:                            [Dd][Aa][Tt][Aa][Ff][Ii][Ll][Ee];
DEALLOCATE:                          [Dd][Ee][Aa][Ll][Ll][Oo][Cc][Aa][Tt][Ee];
DEFAULT_AUTH:                        [Dd][Ee][Ff][Aa][Uu][Ll][Tt][_][Aa][Uu][Tt][Hh];
DEFINER:                             [Dd][Ee][Ff][Ii][Nn][Ee][Rr];
DELAY_KEY_WRITE:                     [Dd][Ee][Ll][Aa][Yy][_][Kk][Ee][Yy][_][Ww][Rr][Ii][Tt][Ee];
DES_KEY_FILE:                        [Dd][Ee][Ss][_][Kk][Ee][Yy][_][Ff][Ii][Ll][Ee];
DIRECTORY:                           [Dd][Ii][Rr][Ee][Cc][Tt][Oo][Rr][Yy];
DISABLE:                             [Dd][Ii][Ss][Aa][Bb][Ll][Ee];
DISCARD:                             [Dd][Ii][Ss][Cc][Aa][Rr][Dd];
DISK:                                [Dd][Ii][Ss][Kk];
DO:                                  [Dd][Oo];
DUMPFILE:                            [Dd][Uu][Mm][Pp][Ff][Ii][Ll][Ee];
DUPLICATE:                           [Dd][Uu][Pp][Ll][Ii][Cc][Aa][Tt][Ee];
DYNAMIC:                             [Dd][Yy][Nn][Aa][Mm][Ii][Cc];
ENABLE:                              [Ee][Nn][Aa][Bb][Ll][Ee];
ENCRYPTION:                          [Ee][Nn][Cc][Rr][Yy][Pp][Tt][Ii][Oo][Nn];
END:                                 [Ee][Nn][Dd];
ENDS:                                [Ee][Nn][Dd][Ss];
ENGINE:                              [Ee][Nn][Gg][Ii][Nn][Ee];
ENGINES:                             [Ee][Nn][Gg][Ii][Nn][Ee][Ss];
ERROR:                               [Ee][Rr][Rr][Oo][Rr];
ERRORS:                              [Ee][Rr][Rr][Oo][Rr][Ss];
ESCAPE:                              [Ee][Ss][Cc][Aa][Pp][Ee];
EVEN:                                [Ee][Vv][Ee][Nn];
EVENT:                               [Ee][Vv][Ee][Nn][Tt];
EVENTS:                              [Ee][Vv][Ee][Nn][Tt][Ss];
EVERY:                               [Ee][Vv][Ee][Rr][Yy];
EXCHANGE:                            [Ee][Xx][Cc][Hh][Aa][Nn][Gg][Ee];
EXCLUSIVE:                           [Ee][Xx][Cc][Ll][Uu][Ss][Ii][Vv][Ee];
EXPIRE:                              [Ee][Xx][Pp][Ii][Rr][Ee];
EXPORT:                              [Ee][Xx][Pp][Oo][Rr][Tt];
EXTENDED:                            [Ee][Xx][Tt][Ee][Nn][Dd][Ee][Dd];
EXTENT_SIZE:                         [Ee][Xx][Tt][Ee][Nn][Tt][_][Ss][Ii][Zz][Ee];
FAST:                                [Ff][Aa][Ss][Tt];
FAULTS:                              [Ff][Aa][Uu][Ll][Tt][Ss];
FIELDS:                              [Ff][Ii][Ee][Ll][Dd][Ss];
FILE_BLOCK_SIZE:                     [Ff][Ii][Ll][Ee][_][Bb][Ll][Oo][Cc][Kk][_][Ss][Ii][Zz][Ee];
FILTER:                              [Ff][Ii][Ll][Tt][Ee][Rr];
FIRST:                               [Ff][Ii][Rr][Ss][Tt];
FIXED:                               [Ff][Ii][Xx][Ee][Dd];
FLUSH:                               [Ff][Ll][Uu][Ss][Hh];
FOLLOWS:                             [Ff][Oo][Ll][Ll][Oo][Ww][Ss];
FOUND:                               [Ff][Oo][Uu][Nn][Dd];
FULL:                                [Ff][Uu][Ll][Ll];
FUNCTION:                            [Ff][Uu][Nn][Cc][Tt][Ii][Oo][Nn];
GENERAL:                             [Gg][Ee][Nn][Ee][Rr][Aa][Ll];
GLOBAL:                              [Gg][Ll][Oo][Bb][Aa][Ll];
GRANTS:                              [Gg][Rr][Aa][Nn][Tt][Ss];
GROUP_REPLICATION:                   [Gg][Rr][Oo][Uu][Pp][_][Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ii][Oo][Nn];
HANDLER:                             [Hh][Aa][Nn][Dd][Ll][Ee][Rr];
HASH:                                [Hh][Aa][Ss][Hh];
HELP:                                [Hh][Ee][Ll][Pp];
HOST:                                [Hh][Oo][Ss][Tt];
HOSTS:                               [Hh][Oo][Ss][Tt][Ss];
IDENTIFIED:                          [Ii][Dd][Ee][Nn][Tt][Ii][Ff][Ii][Ee][Dd];
IGNORE_SERVER_IDS:                   [Ii][Gg][Nn][Oo][Rr][Ee][_][Ss][Ee][Rr][Vv][Ee][Rr][_][Ii][Dd][Ss];
IMPORT:                              [Ii][Mm][Pp][Oo][Rr][Tt];
INDEXES:                             [Ii][Nn][Dd][Ee][Xx][Ee][Ss];
INITIAL_SIZE:                        [Ii][Nn][Ii][Tt][Ii][Aa][Ll][_][Ss][Ii][Zz][Ee];
INPLACE:                             [Ii][Nn][Pp][Ll][Aa][Cc][Ee];
INSERT_METHOD:                       [Ii][Nn][Ss][Ee][Rr][Tt][_][Mm][Ee][Tt][Hh][Oo][Dd];
INSTALL:                             [Ii][Nn][Ss][Tt][Aa][Ll][Ll];
INSTANCE:                            [Ii][Nn][Ss][Tt][Aa][Nn][Cc][Ee];
INVOKER:                             [Ii][Nn][Vv][Oo][Kk][Ee][Rr];
IO:                                  [Ii][Oo];
IO_THREAD:                           [Ii][Oo][_][Tt][Hh][Rr][Ee][Aa][Dd];
IPC:                                 [Ii][Pp][Cc];
ISOLATION:                           [Ii][Ss][Oo][Ll][Aa][Tt][Ii][Oo][Nn];
ISSUER:                              [Ii][Ss][Ss][Uu][Ee][Rr];
JSON:                                [Jj][Ss][Oo][Nn];
KEY_BLOCK_SIZE:                      [Kk][Ee][Yy][_][Bb][Ll][Oo][Cc][Kk][_][Ss][Ii][Zz][Ee];
LANGUAGE:                            [Ll][Aa][Nn][Gg][Uu][Aa][Gg][Ee];
LAST:                                [Ll][Aa][Ss][Tt];
LEAVES:                              [Ll][Ee][Aa][Vv][Ee][Ss];
LESS:                                [Ll][Ee][Ss][Ss];
LEVEL:                               [Ll][Ee][Vv][Ee][Ll];
LIST:                                [Ll][Ii][Ss][Tt];
LOCAL:                               [Ll][Oo][Cc][Aa][Ll];
LOGFILE:                             [Ll][Oo][Gg][Ff][Ii][Ll][Ee];
LOGS:                                [Ll][Oo][Gg][Ss];
MASTER:                              [Mm][Aa][Ss][Tt][Ee][Rr];
MASTER_AUTO_POSITION:                [Mm][Aa][Ss][Tt][Ee][Rr][_][Aa][Uu][Tt][Oo][_][Pp][Oo][Ss][Ii][Tt][Ii][Oo][Nn];
MASTER_CONNECT_RETRY:                [Mm][Aa][Ss][Tt][Ee][Rr][_][Cc][Oo][Nn][Nn][Ee][Cc][Tt][_][Rr][Ee][Tt][Rr][Yy];
MASTER_DELAY:                        [Mm][Aa][Ss][Tt][Ee][Rr][_][Dd][Ee][Ll][Aa][Yy];
MASTER_HEARTBEAT_PERIOD:             [Mm][Aa][Ss][Tt][Ee][Rr][_][Hh][Ee][Aa][Rr][Tt][Bb][Ee][Aa][Tt][_][Pp][Ee][Rr][Ii][Oo][Dd];
MASTER_HOST:                         [Mm][Aa][Ss][Tt][Ee][Rr][_][Hh][Oo][Ss][Tt];
MASTER_LOG_FILE:                     [Mm][Aa][Ss][Tt][Ee][Rr][_][Ll][Oo][Gg][_][Ff][Ii][Ll][Ee];
MASTER_LOG_POS:                      [Mm][Aa][Ss][Tt][Ee][Rr][_][Ll][Oo][Gg][_][Pp][Oo][Ss];
MASTER_PASSWORD:                     [Mm][Aa][Ss][Tt][Ee][Rr][_][Pp][Aa][Ss][Ss][Ww][Oo][Rr][Dd];
MASTER_PORT:                         [Mm][Aa][Ss][Tt][Ee][Rr][_][Pp][Oo][Rr][Tt];
MASTER_RETRY_COUNT:                  [Mm][Aa][Ss][Tt][Ee][Rr][_][Rr][Ee][Tt][Rr][Yy][_][Cc][Oo][Uu][Nn][Tt];
MASTER_SSL:                          [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll];
MASTER_SSL_CA:                       [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Aa];
MASTER_SSL_CAPATH:                   [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Aa][Pp][Aa][Tt][Hh];
MASTER_SSL_CERT:                     [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Ee][Rr][Tt];
MASTER_SSL_CIPHER:                   [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Ii][Pp][Hh][Ee][Rr];
MASTER_SSL_CRL:                      [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Rr][Ll];
MASTER_SSL_CRLPATH:                  [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Cc][Rr][Ll][Pp][Aa][Tt][Hh];
MASTER_SSL_KEY:                      [Mm][Aa][Ss][Tt][Ee][Rr][_][Ss][Ss][Ll][_][Kk][Ee][Yy];
MASTER_TLS_VERSION:                  [Mm][Aa][Ss][Tt][Ee][Rr][_][Tt][Ll][Ss][_][Vv][Ee][Rr][Ss][Ii][Oo][Nn];
MASTER_USER:                         [Mm][Aa][Ss][Tt][Ee][Rr][_][Uu][Ss][Ee][Rr];
MAX_CONNECTIONS_PER_HOUR:            [Mm][Aa][Xx][_][Cc][Oo][Nn][Nn][Ee][Cc][Tt][Ii][Oo][Nn][Ss][_][Pp][Ee][Rr][_][Hh][Oo][Uu][Rr];
MAX_QUERIES_PER_HOUR:                [Mm][Aa][Xx][_][Qq][Uu][Ee][Rr][Ii][Ee][Ss][_][Pp][Ee][Rr][_][Hh][Oo][Uu][Rr];
MAX_ROWS:                            [Mm][Aa][Xx][_][Rr][Oo][Ww][Ss];
MAX_SIZE:                            [Mm][Aa][Xx][_][Ss][Ii][Zz][Ee];
MAX_UPDATES_PER_HOUR:                [Mm][Aa][Xx][_][Uu][Pp][Dd][Aa][Tt][Ee][Ss][_][Pp][Ee][Rr][_][Hh][Oo][Uu][Rr];
MAX_USER_CONNECTIONS:                [Mm][Aa][Xx][_][Uu][Ss][Ee][Rr][_][Cc][Oo][Nn][Nn][Ee][Cc][Tt][Ii][Oo][Nn][Ss];
MEDIUM:                              [Mm][Ee][Dd][Ii][Uu][Mm];
MERGE:                               [Mm][Ee][Rr][Gg][Ee];
MID:                                 [Mm][Ii][Dd];
MIGRATE:                             [Mm][Ii][Gg][Rr][Aa][Tt][Ee];
MIN_ROWS:                            [Mm][Ii][Nn][_][Rr][Oo][Ww][Ss];
MODE:                                [Mm][Oo][Dd][Ee];
MODIFY:                              [Mm][Oo][Dd][Ii][Ff][Yy];
MUTEX:                               [Mm][Uu][Tt][Ee][Xx];
MYSQL:                               [Mm][Yy][Ss][Qq][Ll];
NAME:                                [Nn][Aa][Mm][Ee];
NAMES:                               [Nn][Aa][Mm][Ee][Ss];
NCHAR:                               [Nn][Cc][Hh][Aa][Rr];
NEVER:                               [Nn][Ee][Vv][Ee][Rr];
NEXT:                                [Nn][Ee][Xx][Tt];
NO:                                  [Nn][Oo];
NODEGROUP:                           [Nn][Oo][Dd][Ee][Gg][Rr][Oo][Uu][Pp];
NONE:                                [Nn][Oo][Nn][Ee];
OFFLINE:                             [Oo][Ff][Ff][Ll][Ii][Nn][Ee];
OFFSET:                              [Oo][Ff][Ff][Ss][Ee][Tt];
OJ:                                  [Oo][Jj];
OLD_PASSWORD:                        [Oo][Ll][Dd][_][Pp][Aa][Ss][Ss][Ww][Oo][Rr][Dd];
ONE:                                 [Oo][Nn][Ee];
ONLINE:                              [Oo][Nn][Ll][Ii][Nn][Ee];
ONLY:                                [Oo][Nn][Ll][Yy];
OPEN:                                [Oo][Pp][Ee][Nn];
OPTIMIZER_COSTS:                     [Oo][Pp][Tt][Ii][Mm][Ii][Zz][Ee][Rr][_][Cc][Oo][Ss][Tt][Ss];
OPTIONS:                             [Oo][Pp][Tt][Ii][Oo][Nn][Ss];
OWNER:                               [Oo][Ww][Nn][Ee][Rr];
PACK_KEYS:                           [Pp][Aa][Cc][Kk][_][Kk][Ee][Yy][Ss];
PAGE:                                [Pp][Aa][Gg][Ee];
PARSER:                              [Pp][Aa][Rr][Ss][Ee][Rr];
PARTIAL:                             [Pp][Aa][Rr][Tt][Ii][Aa][Ll];
PARTITIONING:                        [Pp][Aa][Rr][Tt][Ii][Tt][Ii][Oo][Nn][Ii][Nn][Gg];
PARTITIONS:                          [Pp][Aa][Rr][Tt][Ii][Tt][Ii][Oo][Nn][Ss];
PASSWORD:                            [Pp][Aa][Ss][Ss][Ww][Oo][Rr][Dd];
PHASE:                               [Pp][Hh][Aa][Ss][Ee];
PLUGIN:                              [Pp][Ll][Uu][Gg][Ii][Nn];
PLUGIN_DIR:                          [Pp][Ll][Uu][Gg][Ii][Nn][_][Dd][Ii][Rr];
PLUGINS:                             [Pp][Ll][Uu][Gg][Ii][Nn][Ss];
PORT:                                [Pp][Oo][Rr][Tt];
PRECEDES:                            [Pp][Rr][Ee][Cc][Ee][Dd][Ee][Ss];
PREPARE:                             [Pp][Rr][Ee][Pp][Aa][Rr][Ee];
PRESERVE:                            [Pp][Rr][Ee][Ss][Ee][Rr][Vv][Ee];
PREV:                                [Pp][Rr][Ee][Vv];
PROCESSLIST:                         [Pp][Rr][Oo][Cc][Ee][Ss][Ss][Ll][Ii][Ss][Tt];
PROFILE:                             [Pp][Rr][Oo][Ff][Ii][Ll][Ee];
PROFILES:                            [Pp][Rr][Oo][Ff][Ii][Ll][Ee][Ss];
PROXY:                               [Pp][Rr][Oo][Xx][Yy];
QUERY:                               [Qq][Uu][Ee][Rr][Yy];
QUICK:                               [Qq][Uu][Ii][Cc][Kk];
REBUILD:                             [Rr][Ee][Bb][Uu][Ii][Ll][Dd];
RECOVER:                             [Rr][Ee][Cc][Oo][Vv][Ee][Rr];
REDO_BUFFER_SIZE:                    [Rr][Ee][Dd][Oo][_][Bb][Uu][Ff][Ff][Ee][Rr][_][Ss][Ii][Zz][Ee];
REDUNDANT:                           [Rr][Ee][Dd][Uu][Nn][Dd][Aa][Nn][Tt];
RELAY:                               [Rr][Ee][Ll][Aa][Yy];
RELAY_LOG_FILE:                      [Rr][Ee][Ll][Aa][Yy][_][Ll][Oo][Gg][_][Ff][Ii][Ll][Ee];
RELAY_LOG_POS:                       [Rr][Ee][Ll][Aa][Yy][_][Ll][Oo][Gg][_][Pp][Oo][Ss];
RELAYLOG:                            [Rr][Ee][Ll][Aa][Yy][Ll][Oo][Gg];
REMOVE:                              [Rr][Ee][Mm][Oo][Vv][Ee];
REORGANIZE:                          [Rr][Ee][Oo][Rr][Gg][Aa][Nn][Ii][Zz][Ee];
REPAIR:                              [Rr][Ee][Pp][Aa][Ii][Rr];
REPLICATE_DO_DB:                     [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Dd][Oo][_][Dd][Bb];
REPLICATE_DO_TABLE:                  [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Dd][Oo][_][Tt][Aa][Bb][Ll][Ee];
REPLICATE_IGNORE_DB:                 [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Ii][Gg][Nn][Oo][Rr][Ee][_][Dd][Bb];
REPLICATE_IGNORE_TABLE:              [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Ii][Gg][Nn][Oo][Rr][Ee][_][Tt][Aa][Bb][Ll][Ee];
REPLICATE_REWRITE_DB:                [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Rr][Ee][Ww][Rr][Ii][Tt][Ee][_][Dd][Bb];
REPLICATE_WILD_DO_TABLE:             [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Ww][Ii][Ll][Dd][_][Dd][Oo][_][Tt][Aa][Bb][Ll][Ee];
REPLICATE_WILD_IGNORE_TABLE:         [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ee][_][Ww][Ii][Ll][Dd][_][Ii][Gg][Nn][Oo][Rr][Ee][_][Tt][Aa][Bb][Ll][Ee];
REPLICATION:                         [Rr][Ee][Pp][Ll][Ii][Cc][Aa][Tt][Ii][Oo][Nn];
RESET:                               [Rr][Ee][Ss][Ee][Tt];
RESUME:                              [Rr][Ee][Ss][Uu][Mm][Ee];
RETURNS:                             [Rr][Ee][Tt][Uu][Rr][Nn][Ss];
ROLLBACK:                            [Rr][Oo][Ll][Ll][Bb][Aa][Cc][Kk];
ROLLUP:                              [Rr][Oo][Ll][Ll][Uu][Pp];
ROTATE:                              [Rr][Oo][Tt][Aa][Tt][Ee];
ROW:                                 [Rr][Oo][Ww];
ROWS:                                [Rr][Oo][Ww][Ss];
ROW_FORMAT:                          [Rr][Oo][Ww][_][Ff][Oo][Rr][Mm][Aa][Tt];
SAVEPOINT:                           [Ss][Aa][Vv][Ee][Pp][Oo][Ii][Nn][Tt];
SCHEDULE:                            [Ss][Cc][Hh][Ee][Dd][Uu][Ll][Ee];
SECURITY:                            [Ss][Ee][Cc][Uu][Rr][Ii][Tt][Yy];
SERVER:                              [Ss][Ee][Rr][Vv][Ee][Rr];
SESSION:                             [Ss][Ee][Ss][Ss][Ii][Oo][Nn];
SHARE:                               [Ss][Hh][Aa][Rr][Ee];
SHARED:                              [Ss][Hh][Aa][Rr][Ee][Dd];
SIGNED:                              [Ss][Ii][Gg][Nn][Ee][Dd];
SIMPLE:                              [Ss][Ii][Mm][Pp][Ll][Ee];
SLAVE:                               [Ss][Ll][Aa][Vv][Ee];
SLOW:                                [Ss][Ll][Oo][Ww];
SNAPSHOT:                            [Ss][Nn][Aa][Pp][Ss][Hh][Oo][Tt];
SOCKET:                              [Ss][Oo][Cc][Kk][Ee][Tt];
SOME:                                [Ss][Oo][Mm][Ee];
SONAME:                              [Ss][Oo][Nn][Aa][Mm][Ee];
SOUNDS:                              [Ss][Oo][Uu][Nn][Dd][Ss];
SOURCE:                              [Ss][Oo][Uu][Rr][Cc][Ee];
SQL_AFTER_GTIDS:                     [Ss][Qq][Ll][_][Aa][Ff][Tt][Ee][Rr][_][Gg][Tt][Ii][Dd][Ss];
SQL_AFTER_MTS_GAPS:                  [Ss][Qq][Ll][_][Aa][Ff][Tt][Ee][Rr][_][Mm][Tt][Ss][_][Gg][Aa][Pp][Ss];
SQL_BEFORE_GTIDS:                    [Ss][Qq][Ll][_][Bb][Ee][Ff][Oo][Rr][Ee][_][Gg][Tt][Ii][Dd][Ss];
SQL_BUFFER_RESULT:                   [Ss][Qq][Ll][_][Bb][Uu][Ff][Ff][Ee][Rr][_][Rr][Ee][Ss][Uu][Ll][Tt];
SQL_CACHE:                           [Ss][Qq][Ll][_][Cc][Aa][Cc][Hh][Ee];
SQL_NO_CACHE:                        [Ss][Qq][Ll][_][Nn][Oo][_][Cc][Aa][Cc][Hh][Ee];
SQL_THREAD:                          [Ss][Qq][Ll][_][Tt][Hh][Rr][Ee][Aa][Dd];
START:                               [Ss][Tt][Aa][Rr][Tt];
STARTS:                              [Ss][Tt][Aa][Rr][Tt][Ss];
STATS_AUTO_RECALC:                   [Ss][Tt][Aa][Tt][Ss][_][Aa][Uu][Tt][Oo][_][Rr][Ee][Cc][Aa][Ll][Cc];
STATS_PERSISTENT:                    [Ss][Tt][Aa][Tt][Ss][_][Pp][Ee][Rr][Ss][Ii][Ss][Tt][Ee][Nn][Tt];
STATS_SAMPLE_PAGES:                  [Ss][Tt][Aa][Tt][Ss][_][Ss][Aa][Mm][Pp][Ll][Ee][_][Pp][Aa][Gg][Ee][Ss];
STATUS:                              [Ss][Tt][Aa][Tt][Uu][Ss];
STOP:                                [Ss][Tt][Oo][Pp];
STORAGE:                             [Ss][Tt][Oo][Rr][Aa][Gg][Ee];
STRING:                              [Ss][Tt][Rr][Ii][Nn][Gg];
SUBJECT:                             [Ss][Uu][Bb][Jj][Ee][Cc][Tt];
SUBPARTITION:                        [Ss][Uu][Bb][Pp][Aa][Rr][Tt][Ii][Tt][Ii][Oo][Nn];
SUBPARTITIONS:                       [Ss][Uu][Bb][Pp][Aa][Rr][Tt][Ii][Tt][Ii][Oo][Nn][Ss];
SUSPEND:                             [Ss][Uu][Ss][Pp][Ee][Nn][Dd];
SWAPS:                               [Ss][Ww][Aa][Pp][Ss];
SWITCHES:                            [Ss][Ww][Ii][Tt][Cc][Hh][Ee][Ss];
TABLESPACE:                          [Tt][Aa][Bb][Ll][Ee][Ss][Pp][Aa][Cc][Ee];
TEMPORARY:                           [Tt][Ee][Mm][Pp][Oo][Rr][Aa][Rr][Yy];
TEMPTABLE:                           [Tt][Ee][Mm][Pp][Tt][Aa][Bb][Ll][Ee];
THAN:                                [Tt][Hh][Aa][Nn];
TRADITIONAL:                         [Tt][Rr][Aa][Dd][Ii][Tt][Ii][Oo][Nn][Aa][Ll];
TRANSACTION:                         [Tt][Rr][Aa][Nn][Ss][Aa][Cc][Tt][Ii][Oo][Nn];
TRIGGERS:                            [Tt][Rr][Ii][Gg][Gg][Ee][Rr][Ss];
TRUNCATE:                            [Tt][Rr][Uu][Nn][Cc][Aa][Tt][Ee];
UNDEFINED:                           [Uu][Nn][Dd][Ee][Ff][Ii][Nn][Ee][Dd];
UNDOFILE:                            [Uu][Nn][Dd][Oo][Ff][Ii][Ll][Ee];
UNDO_BUFFER_SIZE:                    [Uu][Nn][Dd][Oo][_][Bb][Uu][Ff][Ff][Ee][Rr][_][Ss][Ii][Zz][Ee];
UNINSTALL:                           [Uu][Nn][Ii][Nn][Ss][Tt][Aa][Ll][Ll];
UNKNOWN:                             [Uu][Nn][Kk][Nn][Oo][Ww][Nn];
UNTIL:                               [Uu][Nn][Tt][Ii][Ll];
UPGRADE:                             [Uu][Pp][Gg][Rr][Aa][Dd][Ee];
USER:                                [Uu][Ss][Ee][Rr];
USE_FRM:                             [Uu][Ss][Ee][_][Ff][Rr][Mm];
USER_RESOURCES:                      [Uu][Ss][Ee][Rr][_][Rr][Ee][Ss][Oo][Uu][Rr][Cc][Ee][Ss];
VALIDATION:                          [Vv][Aa][Ll][Ii][Dd][Aa][Tt][Ii][Oo][Nn];
VALUE:                               [Vv][Aa][Ll][Uu][Ee];
VARIABLES:                           [Vv][Aa][Rr][Ii][Aa][Bb][Ll][Ee][Ss];
VIEW:                                [Vv][Ii][Ee][Ww];
WAIT:                                [Ww][Aa][Ii][Tt];
WARNINGS:                            [Ww][Aa][Rr][Nn][Ii][Nn][Gg][Ss];
WITHOUT:                             [Ww][Ii][Tt][Hh][Oo][Uu][Tt];
WORK:                                [Ww][Oo][Rr][Kk];
WRAPPER:                             [Ww][Rr][Aa][Pp][Pp][Ee][Rr];
X509:                                'X509';
XA:                                  [Xx][Aa];
XML:                                 [Xx][Mm][Ll];


// Date format Keywords

EUR:                                 [Ee][Uu][Rr];
USA:                                 [Uu][Ss][Aa];
JIS:                                 [Jj][Ii][Ss];
ISO:                                 [Ii][Ss][Oo];
INTERNAL:                            [Ii][Nn][Tt][Ee][Rr][Nn][Aa][Ll];


// Interval type Keywords

QUARTER:                             [Qq][Uu][Aa][Rr][Tt][Ee][Rr];
MONTH:                               [Mm][Oo][Nn][Tt][Hh];
DAY:                                 [Dd][Aa][Yy];
HOUR:                                [Hh][Oo][Uu][Rr];
MINUTE:                              [Mm][Ii][Nn][Uu][Tt][Ee];
WEEK:                                [Ww][Ee][Ee][Kk];
SECOND:                              [Ss][Ee][Cc][Oo][Nn][Dd];
MICROSECOND:                         [Mm][Ii][Cc][Rr][Oo][Ss][Ee][Cc][Oo][Nn][Dd];


// PRIVILEGES

TABLES:                              [Tt][Aa][Bb][Ll][Ee][Ss];
ROUTINE:                             [Rr][Oo][Uu][Tt][Ii][Nn][Ee];
EXECUTE:                             [Ee][Xx][Ee][Cc][Uu][Tt][Ee];
FILE:                                [Ff][Ii][Ll][Ee];
PROCESS:                             [Pp][Rr][Oo][Cc][Ee][Ss][Ss];
RELOAD:                              [Rr][Ee][Ll][Oo][Aa][Dd];
SHUTDOWN:                            [Ss][Hh][Uu][Tt][Dd][Oo][Ww][Nn];
SUPER:                               [Ss][Uu][Pp][Ee][Rr];
PRIVILEGES:                          [Pp][Rr][Ii][Vv][Ii][Ll][Ee][Gg][Ee][Ss];


// Charsets

ARMSCII8:                            'ARMSCII8';
ASCII:                               'ASCII';
BIG5:                                'BIG5';
CP1250:                              'CP1250';
CP1251:                              'CP1251';
CP1256:                              'CP1256';
CP1257:                              'CP1257';
CP850:                               'CP850';
CP852:                               'CP852';
CP866:                               'CP866';
CP932:                               'CP932';
DEC8:                                'DEC8';
EUCJPMS:                             'EUCJPMS';
EUCKR:                               'EUCKR';
GB2312:                              'GB2312';
GBK:                                 'GBK';
GEOSTD8:                             'GEOSTD8';
GREEK:                               'GREEK';
HEBREW:                              'HEBREW';
HP8:                                 'HP8';
KEYBCS2:                             'KEYBCS2';
KOI8R:                               'KOI8R';
KOI8U:                               'KOI8U';
LATIN1:                              'LATIN1';
LATIN2:                              'LATIN2';
LATIN5:                              'LATIN5';
LATIN7:                              'LATIN7';
MACCE:                               'MACCE';
MACROMAN:                            'MACROMAN';
SJIS:                                'SJIS';
SWE7:                                'SWE7';
TIS620:                              'TIS620';
UCS2:                                'UCS2';
UJIS:                                'UJIS';
UTF16:                               'UTF16';
UTF16LE:                             'UTF16LE';
UTF32:                               'UTF32';
UTF8:                                [Uu][Tt][Ff][8];
UTF8MB3:                             'UTF8MB3';
UTF8MB4:                             'UTF8MB4';


// DB Engines

ARCHIVE:                             [Aa][Rr][Cc][Hh][Ii][Vv][Ee];
BLACKHOLE:                           [Bb][Ll][Aa][Cc][Kk][Hh][Oo][Ll][Ee];
CSV:                                 [Cc][Ss][Vv];
FEDERATED:                           [Ff][Ee][Dd][Ee][Rr][Aa][Tt][Ee][Dd];
INNODB:                              [Ii][Nn][Nn][Oo][Dd][Bb];
MEMORY:                              [Mm][Ee][Mm][Oo][Rr][Yy];
MRG_MYISAM:                          [Mm][Rr][Gg][_][Mm][Yy][Ii][Ss][Aa][Mm];
MYISAM:                              [Mm][Yy][Ii][Ss][Aa][Mm];
NDB:                                 [Nn][Dd][Bb];
NDBCLUSTER:                          [Nn][Dd][Bb][Cc][Ll][Uu][Ss][Tt][Ee][Rr];
PERFOMANCE_SCHEMA:                   [Pp][Ee][Rr][Ff][Oo][Mm][Aa][Nn][Cc][Ee][_][Ss][Cc][Hh][Ee][Mm][Aa];


// Transaction Levels

REPEATABLE:                          [Rr][Ee][Pp][Ee][Aa][Tt][Aa][Bb][Ll][Ee];
COMMITTED:                           [Cc][Oo][Mm][Mm][Ii][Tt][Tt][Ee][Dd];
UNCOMMITTED:                         [Uu][Nn][Cc][Oo][Mm][Mm][Ii][Tt][Tt][Ee][Dd];
SERIALIZABLE:                        [Ss][Ee][Rr][Ii][Aa][Ll][Ii][Zz][Aa][Bb][Ll][Ee];


// Spatial data types

GEOMETRYCOLLECTION:                  [Gg][Ee][Oo][Mm][Ee][Tt][Rr][Yy][Cc][Oo][Ll][Ll][Ee][Cc][Tt][Ii][Oo][Nn];
LINESTRING:                          [Ll][Ii][Nn][Ee][Ss][Tt][Rr][Ii][Nn][Gg];
MULTILINESTRING:                     [Mm][Uu][Ll][Tt][Ii][Ll][Ii][Nn][Ee][Ss][Tt][Rr][Ii][Nn][Gg];
MULTIPOINT:                          [Mm][Uu][Ll][Tt][Ii][Pp][Oo][Ii][Nn][Tt];
MULTIPOLYGON:                        [Mm][Uu][Ll][Tt][Ii][Pp][Oo][Ll][Yy][Gg][Oo][Nn];
POINT:                               [Pp][Oo][Ii][Nn][Tt];
POLYGON:                             [Pp][Oo][Ll][Yy][Gg][Oo][Nn];


// Common function names

ABS:                                 'ABS';
ACOS:                                'ACOS';
ADDDATE:                             'ADDDATE';
ADDTIME:                             'ADDTIME';
AES_DECRYPT:                         'AES_DECRYPT';
AES_ENCRYPT:                         'AES_ENCRYPT';
AREA:                                'AREA';
ASBINARY:                            'ASBINARY';
ASIN:                                'ASIN';
ASTEXT:                              'ASTEXT';
ASWKB:                               'ASWKB';
ASWKT:                               'ASWKT';
ASYMMETRIC_DECRYPT:                  'ASYMMETRIC_DECRYPT';
ASYMMETRIC_DERIVE:                   'ASYMMETRIC_DERIVE';
ASYMMETRIC_ENCRYPT:                  'ASYMMETRIC_ENCRYPT';
ASYMMETRIC_SIGN:                     'ASYMMETRIC_SIGN';
ASYMMETRIC_VERIFY:                   'ASYMMETRIC_VERIFY';
ATAN:                                'ATAN';
ATAN2:                               'ATAN2';
BENCHMARK:                           'BENCHMARK';
BIN:                                 'BIN';
BIT_COUNT:                           'BIT_COUNT';
BIT_LENGTH:                          'BIT_LENGTH';
BUFFER:                              'BUFFER';
CEIL:                                'CEIL';
CEILING:                             'CEILING';
CENTROID:                            'CENTROID';
CHARACTER_LENGTH:                    'CHARACTER_LENGTH';
CHARSET:                             'CHARSET';
CHAR_LENGTH:                         'CHAR_LENGTH';
COERCIBILITY:                        'COERCIBILITY';
COLLATION:                           'COLLATION';
COMPRESS:                            'COMPRESS';
CONCAT:                              'CONCAT';
CONCAT_WS:                           'CONCAT_WS';
CONNECTION_ID:                       'CONNECTION_ID';
CONV:                                'CONV';
CONVERT_TZ:                          'CONVERT_TZ';
COS:                                 'COS';
COT:                                 'COT';
CRC32:                               'CRC32';
CREATE_ASYMMETRIC_PRIV_KEY:          'CREATE_ASYMMETRIC_PRIV_KEY';
CREATE_ASYMMETRIC_PUB_KEY:           'CREATE_ASYMMETRIC_PUB_KEY';
CREATE_DH_PARAMETERS:                'CREATE_DH_PARAMETERS';
CREATE_DIGEST:                       'CREATE_DIGEST';
CROSSES:                             'CROSSES';
DATEDIFF:                            'DATEDIFF';
DATE_FORMAT:                         'DATE_FORMAT';
DAYNAME:                             'DAYNAME';
DAYOFMONTH:                          'DAYOFMONTH';
DAYOFWEEK:                           'DAYOFWEEK';
DAYOFYEAR:                           'DAYOFYEAR';
DECODE:                              'DECODE';
DEGREES:                             'DEGREES';
DES_DECRYPT:                         'DES_DECRYPT';
DES_ENCRYPT:                         'DES_ENCRYPT';
DIMENSION:                           'DIMENSION';
DISJOINT:                            'DISJOINT';
ELT:                                 'ELT';
ENCODE:                              'ENCODE';
ENCRYPT:                             'ENCRYPT';
ENDPOINT:                            'ENDPOINT';
ENVELOPE:                            'ENVELOPE';
EQUALS:                              'EQUALS';
EXP:                                 'EXP';
EXPORT_SET:                          'EXPORT_SET';
EXTERIORRING:                        'EXTERIORRING';
EXTRACTVALUE:                        'EXTRACTVALUE';
FIELD:                               'FIELD';
FIND_IN_SET:                         'FIND_IN_SET';
FLOOR:                               'FLOOR';
FORMAT:                              'FORMAT';
FOUND_ROWS:                          'FOUND_ROWS';
FROM_BASE64:                         'FROM_BASE64';
FROM_DAYS:                           'FROM_DAYS';
FROM_UNIXTIME:                       'FROM_UNIXTIME';
GEOMCOLLFROMTEXT:                    'GEOMCOLLFROMTEXT';
GEOMCOLLFROMWKB:                     'GEOMCOLLFROMWKB';
GEOMETRYCOLLECTIONFROMTEXT:          'GEOMETRYCOLLECTIONFROMTEXT';
GEOMETRYCOLLECTIONFROMWKB:           'GEOMETRYCOLLECTIONFROMWKB';
GEOMETRYFROMTEXT:                    'GEOMETRYFROMTEXT';
GEOMETRYFROMWKB:                     'GEOMETRYFROMWKB';
GEOMETRYN:                           'GEOMETRYN';
GEOMETRYTYPE:                        'GEOMETRYTYPE';
GEOMFROMTEXT:                        'GEOMFROMTEXT';
GEOMFROMWKB:                         'GEOMFROMWKB';
GET_FORMAT:                          'GET_FORMAT';
GET_LOCK:                            'GET_LOCK';
GLENGTH:                             'GLENGTH';
GREATEST:                            'GREATEST';
GTID_SUBSET:                         'GTID_SUBSET';
GTID_SUBTRACT:                       'GTID_SUBTRACT';
HEX:                                 'HEX';
IFNULL:                              'IFNULL';
INET6_ATON:                          'INET6_ATON';
INET6_NTOA:                          'INET6_NTOA';
INET_ATON:                           'INET_ATON';
INET_NTOA:                           'INET_NTOA';
INSTR:                               'INSTR';
INTERIORRINGN:                       'INTERIORRINGN';
INTERSECTS:                          'INTERSECTS';
ISCLOSED:                            'ISCLOSED';
ISEMPTY:                             'ISEMPTY';
ISNULL:                              'ISNULL';
ISSIMPLE:                            'ISSIMPLE';
IS_FREE_LOCK:                        'IS_FREE_LOCK';
IS_IPV4:                             'IS_IPV4';
IS_IPV4_COMPAT:                      'IS_IPV4_COMPAT';
IS_IPV4_MAPPED:                      'IS_IPV4_MAPPED';
IS_IPV6:                             'IS_IPV6';
IS_USED_LOCK:                        'IS_USED_LOCK';
LAST_INSERT_ID:                      'LAST_INSERT_ID';
LCASE:                               'LCASE';
LEAST:                               'LEAST';
LENGTH:                              'LENGTH';
LINEFROMTEXT:                        'LINEFROMTEXT';
LINEFROMWKB:                         'LINEFROMWKB';
LINESTRINGFROMTEXT:                  'LINESTRINGFROMTEXT';
LINESTRINGFROMWKB:                   'LINESTRINGFROMWKB';
LN:                                  'LN';
LOAD_FILE:                           'LOAD_FILE';
LOCATE:                              'LOCATE';
LOG:                                 'LOG';
LOG10:                               'LOG10';
LOG2:                                'LOG2';
LOWER:                               'LOWER';
LPAD:                                'LPAD';
LTRIM:                               'LTRIM';
MAKEDATE:                            'MAKEDATE';
MAKETIME:                            'MAKETIME';
MAKE_SET:                            'MAKE_SET';
MASTER_POS_WAIT:                     'MASTER_POS_WAIT';
MBRCONTAINS:                         'MBRCONTAINS';
MBRDISJOINT:                         'MBRDISJOINT';
MBREQUAL:                            'MBREQUAL';
MBRINTERSECTS:                       'MBRINTERSECTS';
MBROVERLAPS:                         'MBROVERLAPS';
MBRTOUCHES:                          'MBRTOUCHES';
MBRWITHIN:                           'MBRWITHIN';
MD5:                                 'MD5';
MLINEFROMTEXT:                       'MLINEFROMTEXT';
MLINEFROMWKB:                        'MLINEFROMWKB';
MONTHNAME:                           'MONTHNAME';
MPOINTFROMTEXT:                      'MPOINTFROMTEXT';
MPOINTFROMWKB:                       'MPOINTFROMWKB';
MPOLYFROMTEXT:                       'MPOLYFROMTEXT';
MPOLYFROMWKB:                        'MPOLYFROMWKB';
MULTILINESTRINGFROMTEXT:             'MULTILINESTRINGFROMTEXT';
MULTILINESTRINGFROMWKB:              'MULTILINESTRINGFROMWKB';
MULTIPOINTFROMTEXT:                  'MULTIPOINTFROMTEXT';
MULTIPOINTFROMWKB:                   'MULTIPOINTFROMWKB';
MULTIPOLYGONFROMTEXT:                'MULTIPOLYGONFROMTEXT';
MULTIPOLYGONFROMWKB:                 'MULTIPOLYGONFROMWKB';
NAME_CONST:                          'NAME_CONST';
NULLIF:                              'NULLIF';
NUMGEOMETRIES:                       'NUMGEOMETRIES';
NUMINTERIORRINGS:                    'NUMINTERIORRINGS';
NUMPOINTS:                           'NUMPOINTS';
OCT:                                 'OCT';
OCTET_LENGTH:                        'OCTET_LENGTH';
ORD:                                 'ORD';
OVERLAPS:                            'OVERLAPS';
PERIOD_ADD:                          'PERIOD_ADD';
PERIOD_DIFF:                         'PERIOD_DIFF';
PI:                                  'PI';
POINTFROMTEXT:                       'POINTFROMTEXT';
POINTFROMWKB:                        'POINTFROMWKB';
POINTN:                              'POINTN';
POLYFROMTEXT:                        'POLYFROMTEXT';
POLYFROMWKB:                         'POLYFROMWKB';
POLYGONFROMTEXT:                     'POLYGONFROMTEXT';
POLYGONFROMWKB:                      'POLYGONFROMWKB';
POW:                                 'POW';
POWER:                               'POWER';
QUOTE:                               'QUOTE';
RADIANS:                             'RADIANS';
RAND:                                'RAND';
RANDOM_BYTES:                        'RANDOM_BYTES';
RELEASE_LOCK:                        'RELEASE_LOCK';
REVERSE:                             'REVERSE';
ROUND:                               'ROUND';
ROW_COUNT:                           'ROW_COUNT';
RPAD:                                'RPAD';
RTRIM:                               'RTRIM';
SEC_TO_TIME:                         'SEC_TO_TIME';
SESSION_USER:                        'SESSION_USER';
SHA:                                 'SHA';
SHA1:                                'SHA1';
SHA2:                                'SHA2';
SIGN:                                'SIGN';
SIN:                                 'SIN';
SLEEP:                               'SLEEP';
SOUNDEX:                             'SOUNDEX';
SQL_THREAD_WAIT_AFTER_GTIDS:         'SQL_THREAD_WAIT_AFTER_GTIDS';
SQRT:                                'SQRT';
SRID:                                'SRID';
STARTPOINT:                          'STARTPOINT';
STRCMP:                              'STRCMP';
STR_TO_DATE:                         'STR_TO_DATE';
ST_AREA:                             'ST_AREA';
ST_ASBINARY:                         'ST_ASBINARY';
ST_ASTEXT:                           'ST_ASTEXT';
ST_ASWKB:                            'ST_ASWKB';
ST_ASWKT:                            'ST_ASWKT';
ST_BUFFER:                           'ST_BUFFER';
ST_CENTROID:                         'ST_CENTROID';
ST_CONTAINS:                         'ST_CONTAINS';
ST_CROSSES:                          'ST_CROSSES';
ST_DIFFERENCE:                       'ST_DIFFERENCE';
ST_DIMENSION:                        'ST_DIMENSION';
ST_DISJOINT:                         'ST_DISJOINT';
ST_DISTANCE:                         'ST_DISTANCE';
ST_ENDPOINT:                         'ST_ENDPOINT';
ST_ENVELOPE:                         'ST_ENVELOPE';
ST_EQUALS:                           'ST_EQUALS';
ST_EXTERIORRING:                     'ST_EXTERIORRING';
ST_GEOMCOLLFROMTEXT:                 'ST_GEOMCOLLFROMTEXT';
ST_GEOMCOLLFROMTXT:                  'ST_GEOMCOLLFROMTXT';
ST_GEOMCOLLFROMWKB:                  'ST_GEOMCOLLFROMWKB';
ST_GEOMETRYCOLLECTIONFROMTEXT:       'ST_GEOMETRYCOLLECTIONFROMTEXT';
ST_GEOMETRYCOLLECTIONFROMWKB:        'ST_GEOMETRYCOLLECTIONFROMWKB';
ST_GEOMETRYFROMTEXT:                 'ST_GEOMETRYFROMTEXT';
ST_GEOMETRYFROMWKB:                  'ST_GEOMETRYFROMWKB';
ST_GEOMETRYN:                        'ST_GEOMETRYN';
ST_GEOMETRYTYPE:                     'ST_GEOMETRYTYPE';
ST_GEOMFROMTEXT:                     'ST_GEOMFROMTEXT';
ST_GEOMFROMWKB:                      'ST_GEOMFROMWKB';
ST_INTERIORRINGN:                    'ST_INTERIORRINGN';
ST_INTERSECTION:                     'ST_INTERSECTION';
ST_INTERSECTS:                       'ST_INTERSECTS';
ST_ISCLOSED:                         'ST_ISCLOSED';
ST_ISEMPTY:                          'ST_ISEMPTY';
ST_ISSIMPLE:                         'ST_ISSIMPLE';
ST_LINEFROMTEXT:                     'ST_LINEFROMTEXT';
ST_LINEFROMWKB:                      'ST_LINEFROMWKB';
ST_LINESTRINGFROMTEXT:               'ST_LINESTRINGFROMTEXT';
ST_LINESTRINGFROMWKB:                'ST_LINESTRINGFROMWKB';
ST_NUMGEOMETRIES:                    'ST_NUMGEOMETRIES';
ST_NUMINTERIORRING:                  'ST_NUMINTERIORRING';
ST_NUMINTERIORRINGS:                 'ST_NUMINTERIORRINGS';
ST_NUMPOINTS:                        'ST_NUMPOINTS';
ST_OVERLAPS:                         'ST_OVERLAPS';
ST_POINTFROMTEXT:                    'ST_POINTFROMTEXT';
ST_POINTFROMWKB:                     'ST_POINTFROMWKB';
ST_POINTN:                           'ST_POINTN';
ST_POLYFROMTEXT:                     'ST_POLYFROMTEXT';
ST_POLYFROMWKB:                      'ST_POLYFROMWKB';
ST_POLYGONFROMTEXT:                  'ST_POLYGONFROMTEXT';
ST_POLYGONFROMWKB:                   'ST_POLYGONFROMWKB';
ST_SRID:                             'ST_SRID';
ST_STARTPOINT:                       'ST_STARTPOINT';
ST_SYMDIFFERENCE:                    'ST_SYMDIFFERENCE';
ST_TOUCHES:                          'ST_TOUCHES';
ST_UNION:                            'ST_UNION';
ST_WITHIN:                           'ST_WITHIN';
ST_X:                                'ST_X';
ST_Y:                                'ST_Y';
SUBDATE:                             'SUBDATE';
SUBSTRING_INDEX:                     'SUBSTRING_INDEX';
SUBTIME:                             'SUBTIME';
SYSTEM_USER:                         'SYSTEM_USER';
TAN:                                 'TAN';
TIMEDIFF:                            'TIMEDIFF';
TIMESTAMPADD:                        'TIMESTAMPADD';
TIMESTAMPDIFF:                       'TIMESTAMPDIFF';
TIME_FORMAT:                         'TIME_FORMAT';
TIME_TO_SEC:                         'TIME_TO_SEC';
TOUCHES:                             'TOUCHES';
TO_BASE64:                           'TO_BASE64';
TO_DAYS:                             'TO_DAYS';
TO_SECONDS:                          'TO_SECONDS';
UCASE:                               'UCASE';
UNCOMPRESS:                          'UNCOMPRESS';
UNCOMPRESSED_LENGTH:                 'UNCOMPRESSED_LENGTH';
UNHEX:                               'UNHEX';
UNIX_TIMESTAMP:                      'UNIX_TIMESTAMP';
UPDATEXML:                           'UPDATEXML';
UPPER:                               'UPPER';
UUID:                                'UUID';
UUID_SHORT:                          'UUID_SHORT';
VALIDATE_PASSWORD_STRENGTH:          'VALIDATE_PASSWORD_STRENGTH';
VERSION:                             'VERSION';
WAIT_UNTIL_SQL_THREAD_AFTER_GTIDS:   'WAIT_UNTIL_SQL_THREAD_AFTER_GTIDS';
WEEKDAY:                             'WEEKDAY';
WEEKOFYEAR:                          'WEEKOFYEAR';
WEIGHT_STRING:                       'WEIGHT_STRING';
WITHIN:                              'WITHIN';
YEARWEEK:                            'YEARWEEK';
Y_FUNCTION:                          'Y';
X_FUNCTION:                          'X';



// Operators
// Operators. Assigns

VAR_ASSIGN:                          ':=';
PLUS_ASSIGN:                         '+=';
MINUS_ASSIGN:                        '-=';
MULT_ASSIGN:                         '*=';
DIV_ASSIGN:                          '/=';
MOD_ASSIGN:                          '%=';
AND_ASSIGN:                          '&=';
XOR_ASSIGN:                          '^=';
OR_ASSIGN:                           '|=';


// Operators. Arithmetics

STAR:                                '*';
DIVIDE:                              '/';
MODULE:                              '%';
PLUS:                                '+';
MINUSMINUS:                          '--';
MINUS:                               '-';
DIV:                                 [Dd][Ii][Vv];
MOD:                                 [Mm][Oo][Dd];


// Operators. Comparation

EQUAL_SYMBOL:                        '=';
GREATER_SYMBOL:                      '>';
LESS_SYMBOL:                         '<';
EXCLAMATION_SYMBOL:                  '!';


// Operators. Bit

BIT_NOT_OP:                          '~';
BIT_OR_OP:                           '|';
BIT_AND_OP:                          '&';
BIT_XOR_OP:                          '^';


// Constructors symbols

DOT:                                 '.';
LR_BRACKET:                          '(';
RR_BRACKET:                          ')';
COMMA:                               ',';
SEMI:                                ';';
AT_SIGN:                             '@';
ZERO_DECIMAL:                        '0';
ONE_DECIMAL:                         '1';
TWO_DECIMAL:                         '2';
SINGLE_QUOTE_SYMB:                   '\'';
DOUBLE_QUOTE_SYMB:                   '"';
REVERSE_QUOTE_SYMB:                  '`';
COLON_SYMB:                          ':';



// Charsets

CHARSET_REVERSE_QOUTE_STRING:        '`' CHARSET_NAME '`';



// File's sizes


FILESIZE_LITERAL:                    DEC_DIGIT+ ('K'|'M'|'G'|'T');



// Literal Primitives


START_NATIONAL_STRING_LITERAL:       'N' SQUOTA_STRING;
STRING_LITERAL:                      DQUOTA_STRING | SQUOTA_STRING;
DECIMAL_LITERAL:                     DEC_DIGIT+;
HEXADECIMAL_LITERAL:                 'X' '\'' (HEX_DIGIT HEX_DIGIT)+ '\''
                                     | '0X' HEX_DIGIT+;

REAL_LITERAL:                        (DEC_DIGIT+)? '.' DEC_DIGIT+
                                     | DEC_DIGIT+ '.' EXPONENT_NUM_PART
                                     | (DEC_DIGIT+)? '.' (DEC_DIGIT+ EXPONENT_NUM_PART)
                                     | DEC_DIGIT+ EXPONENT_NUM_PART;
NULL_SPEC_LITERAL:                   '\\' 'N';
BIT_STRING:                          BIT_STRING_L;
STRING_CHARSET_NAME:                 '_' CHARSET_NAME;




// Hack for dotID
// Prevent recognize string:         .123somelatin AS ((.123), FLOAT_LITERAL), ((somelatin), ID)
//  it must recoginze:               .123somelatin AS ((.), DOT), (123somelatin, ID)

DOT_ID:                              '.' ID_LITERAL;



// Identifiers

ID:                                  ID_LITERAL;
// DOUBLE_QUOTE_ID:                  '"' ~'"'+ '"';
REVERSE_QUOTE_ID:                    '`' ~'`'+ '`';
STRING_USER_NAME:                    (
                                       SQUOTA_STRING | DQUOTA_STRING
                                       | BQUOTA_STRING | ID_LITERAL
                                     ) '@'
                                     (
                                       SQUOTA_STRING | DQUOTA_STRING
                                       | BQUOTA_STRING | ID_LITERAL
                                     );
LOCAL_ID:                            '@'
                                (
                                  [A-Za-z0-9._$]+
                                  | SQUOTA_STRING
                                  | DQUOTA_STRING
                                  | BQUOTA_STRING
                                );
GLOBAL_ID:                           '@' '@'
                                (
                                  [A-Za-z0-9._$]+
                                  | BQUOTA_STRING
                                );


// Fragments for Literal primitives

fragment CHARSET_NAME:               ARMSCII8 | ASCII | BIG5 | BINARY | CP1250 
                                     | CP1251 | CP1256 | CP1257 | CP850 
                                     | CP852 | CP866 | CP932 | DEC8 | EUCJPMS 
                                     | EUCKR | GB2312 | GBK | GEOSTD8 | GREEK 
                                     | HEBREW | HP8 | KEYBCS2 | KOI8R | KOI8U 
                                     | LATIN1 | LATIN2 | LATIN5 | LATIN7 
                                     | MACCE | MACROMAN | SJIS | SWE7 | TIS620 
                                     | UCS2 | UJIS | UTF16 | UTF16LE | UTF32 
                                     | UTF8 | UTF8MB4;

fragment EXPONENT_NUM_PART:          'E' '-'? DEC_DIGIT+;
fragment ID_LITERAL:                 [A-Za-z_$0-9]*?[A-Za-z_$]+?[A-Za-z_$0-9]*;
fragment DQUOTA_STRING:              '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
fragment SQUOTA_STRING:              '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
fragment BQUOTA_STRING:              '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
fragment HEX_DIGIT:                  [0-9A-F];
fragment DEC_DIGIT:                  [0-9];
fragment BIT_STRING_L:               'B' '\'' [01]+ '\'';



// Last tokens must generate Errors

ERROR_RECONGNIGION:                  .    -> channel(ERRORCHANNEL);
