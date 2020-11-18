SELECT m.mentioned mentionedUser , u2.state mentionedUserState, group_concat(distinct(t.postedUser)) postingUsers
FROM users u, tweets t, mentions m, users u2
WHERE t.postedUser = u.screen_name
AND t.tid = m.tid
AND u2.screen_name = m.mentioned
AND MONTH(t.createdTime) = 1
AND YEAR(t.createdTime) = 2016
AND u.sub_category = 'GOP'
GROUP BY m.mentioned
ORDER BY count(*) desc
LIMIT 5
