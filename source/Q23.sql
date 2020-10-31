use group5;

select ht.name, count(ht.tid) as num_uses
	from hastags ht, tweets t, users u 
    where ht.tid = t.tid 
    and t.postedUser = u.screen_name
    and u.sub_category = 'GOP'
    and month(t.createdTime) in ('1','2','3')
    and year(t.createdTime) = 2016
    group by ht.name order by num_uses desc limit 5;
    