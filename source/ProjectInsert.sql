-- run forimport.sql
-- make sure that the file is named correctly.
-- for 2020 data, use ',' as the field terminator
-- for 2016 data, use ';' as the field terminator
-- for Linux, the line terminator may be different
-- 81906 rows are the correct number of rows to be imported
-- the REPLACE INTO TABLE is to empty the existing rows in the table before adding new rows.
-- the tweets.csv file must locate inside the specified folder.
-- find out where are the folder MySQL expects the data files to be.
-- to load data from a different folder beyond the default folder, MySQL server configuration file must be changed.
show variables like 'secure_file_priv';
use group5;

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/user.csv' 
REPLACE INTO TABLE users
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(screen_name,name,sub_category,category,state,num_followers,num_following);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/tweets.csv' 
REPLACE INTO TABLE tweets
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tid,textbody,retweet_count,@col4,createdTime,postedUser);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/tagged.csv' 
REPLACE INTO TABLE hashtags
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(@col1,name);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/tagged.csv' 
REPLACE INTO TABLE hasTags
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tid,name);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/urlused.csv' 
REPLACE INTO TABLE urls
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(@col1,address);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/urlused.csv' 
REPLACE INTO TABLE hasUrls
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tid,address);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/2016/mentioned.csv' 
REPLACE INTO TABLE mentions
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES
(tid,mentioned);