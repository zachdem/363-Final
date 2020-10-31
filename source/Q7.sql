SELECT count(*) tweet_count, u.screen_name, u.category FROM users u, tweets t, hasTags ht
WHERE u.screen_name = t.postedUser
AND t.tid = ht.tid
AND ht.name = 'GOPDebate'
AND u.state = 'NC'
AND MONTH(t.createdTime) = 2
AND YEAR(t.createdTime) = 2016
GROUP BY u.screen_name
ORDER BY tweet_count desc
LIMIT 5