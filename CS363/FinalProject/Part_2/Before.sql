-- Jake Gudenkauf
-- k = 5 for all queries

use group31;

-- Q3
select count(distinct u.ofstate) as num_states, h.hastagname, group_concat(distinct u.ofstate) as states
from user u, tagged h, tweets t
where h.tid = t.tid and t.posting_user = u.screen_name and year(t.posted) = 2016
group by h.hastagname
order by num_states desc
limit 5;

-- Q7
select count(t.tid) as tweet_count, u.screen_name, u.category
from tagged h, user u, tweets t
where h.tid = t.tid and t.posting_user = u.screen_name and h.hastagname = "GOPDebate" and u.ofstate = "NC" and month(t.posted) = 2 and year(t.posted) = 2016
group by u.screen_name
order by count(t.tid) desc
limit 5; 

-- Q9
select u.screen_name, u.sub_category, u.numFollowers
from user u
where u.sub_category = "Democrat"
order by u.numFollowers desc
limit 5;

-- Q16
select u.screen_name, u.category, t.textbody, t.retweet_count, url.url
from user u, tweets t, urlused url
where t.tid = url.tid and t.posting_user = u.screen_name and month(t.posted) = 2 and year(t.posted) = 2016
order by t.retweet_count desc
limit 5;

-- Q18
select m.screen_name as user_mentioned, u1.ofstate user_mentioned_state, group_concat(distinct u2.screen_name) as posting_user
from user u1, user u2, tweets t, mentioned m
where u2.sub_category = "GOP" and t.posting_user = u2.screen_name and u1.screen_name = m.screen_name and t.tid = m.tid and month(t.posted) = 1 and year(t.posted) = 2016
group by m.screen_name
order by count(m.tid) desc
limit 5;

-- Q23
select h.hastagname, count(distinct h.tid)
from tagged h, user u, tweets t
where t.posting_user = u.screen_name and u.sub_category = "GOP" and h.tid = t.tid and month(t.posted) in (1,2,3) and year(t.posted) = 2016
group by h.hastagname
order by count(h.tid) desc
limit 5;
