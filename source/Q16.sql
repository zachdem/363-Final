use group5;

select u.name as user_name, u.category, t.textbody as texts, t.retweet_count as retweetCt, url.address 
	from tweets t, users u, hasurls url 
    where t.postedUser = u.screen_name 
    and t.tid = url.tid 
    and month(t.createdTime) = 2
    and year(t.createdTime) = 2016
    group by t.tid
    order by t.retweet_count desc
    limit 5;