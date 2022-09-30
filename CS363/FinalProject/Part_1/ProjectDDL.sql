use group31;

create table `mentioned` (
	`tid` bigint(20), 
    `screen_name` varchar(32)
);

create table `tagged` (
	`tid` bigint(32), 
    `hashtagname` varchar(44),
    primary key(`hashtagname`)
);

create table `tweets` (
	`tid` bigint(32) NOT NULL,
    `textbody` varchar(256),
    `retweet_count` int(11) DEFAULT NULL,
    `retweeted` int(11) DEFAULT NULL,
    `posted` datetime DEFAULT NULL,
    `posting_user` varchar(32) DEFAULT NULL,
    PRIMARY KEY (`tid`)
);

create table `urlused` (
	`tid` bigint(20) not null, 
    `url` varchar(512) not null,
    primary key(`url`)
);

create table `user` (
	`screen_name` varchar(32) not null, 
    `name` varchar(32) default null,
    `sub_category` varchar(16) default null, 
    `category` varchar(32) default null, 
    `ofstate` varchar(16) default null, 
    `numFollowers` int(11), 
    `numFollowing` int(11),
    primary key(`screen_name`)
);