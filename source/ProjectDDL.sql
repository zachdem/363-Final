drop database if exists group5;

create database group5;

use group5;

create table users(
	screen_name varchar(15), 
	name varchar(50), 
    num_followers bigint, 
    num_following bigint, 
    category varchar(100), 
    sub_category varchar(100), 
    state varchar(50), 
    primary key(screen_name));

create table tweets(
	tid bigint, 
	textbody text, 
    retweet_count int, 
    createdTime datetime, 
    postedUser varchar(15), 
    primary key(tid), 
    foreign key(postedUser) references users(screen_name) ON DELETE CASCADE ON UPDATE CASCADE);
    
    
create table hasTags(
	tid bigint,
    name varchar(200),
    foreign key(tid) references tweets(tid) ON DELETE CASCADE,
    primary key(name,tid));
    
create table hashtags(
	name varchar(200), 
    foreign key(name) references hasTags(name) ON DELETE CASCADE,
    primary key(name));
    
create table hasUrls(
	tid bigint,
    address varchar(500),
	foreign key(tid) references tweets(tid) ON DELETE CASCADE,
    primary key(address,tid));
    
create table urls(
	address varchar(500), 
    foreign key(address) references hasUrls(address) ON DELETE CASCADE,
    primary key(address));
    
create table mentions(
	tid bigint,
    mentioned varchar(15),
	foreign key(tid) references tweets(tid) ON DELETE CASCADE,
    foreign key(mentioned) references users(screen_name) ON DELETE CASCADE,
    primary key(tid,mentioned));


    
