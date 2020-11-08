# Authors: Andrew Koenen & Zach DeMaris

use group5;

# Q3
SELECT COUNT(DISTINCT(u.state)) AS statenum, GROUP_CONCAT(DISTINCT(u.state)) AS states, ht.name 
FROM hastags ht, tweets t, users u 
WHERE ht.tid = t.tid 
AND t.postedUser = u.screen_name 
AND u.state != 'na' 
AND u.state != '' 
AND YEAR(t.createdTime) = 2016
GROUP BY ht.name ORDER BY statenum DESC LIMIT 5;
    
# Q7 - TODO

# Q9
SELECT u.screen_name, u.sub_category, u.num_followers FROM users u
WHERE u.sub_category = 'GOP'
ORDER BY u.num_followers desc
LIMIT 5;

# Q16
SELECT u.name AS user_name, u.category, t.textbody AS texts, t.retweet_count AS retweetCt, url.address 
FROM tweets t, users u, hasurls url 
WHERE t.postedUser = u.screen_name 
AND t.tid = url.tid 
AND month = 2
AND year = 2016
GROUP BY t.tid
ORDER BY t.retweet_count DESC
LIMIT 5;
    
# Q18
SELECT m.mentioned, u2.state, GROUP_CONCAT(DISTINCT(t.postedUser)), COUNT(*) 
FROM users u, tweets t, mentions m, users u2
WHERE t.postedUser = u.screen_name
AND t.tid = m.tid
AND u2.screen_name = m.mentioned
AND MONTH(t.createdTime) = 1
AND YEAR(t.createdTime) = 2016
AND u.sub_category = 'GOP'
GROUP BY m.mentioned
ORDER BY COUNT(*) DESC
LIMIT 5;

# Q23
SELECT ht.name, COUNT(ht.tid) AS num_uses
FROM hastags ht, tweets t, users u 
WHERE ht.tid = t.tid 
AND t.postedUser = u.screen_name
AND u.sub_category = 'GOP'
AND month IN ('1','2','3')
AND year = 2016
GROUP BY ht.name ORDER BY num_uses DESC LIMIT 5;
