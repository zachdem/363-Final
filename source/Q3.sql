use group5;

select count(distinct(u.state)) as statenum, GROUP_CONCAT(distinct(u.state)) as states, ht.name 
	from hastags ht, tweets t, users u 
    where ht.tid = t.tid 
    and t.postedUser = u.screen_name 
    and u.state != 'na' 
    and u.state != '' 
    and year(t.createdTime) = 2016
    group by ht.name order by statenum desc limit 5;

