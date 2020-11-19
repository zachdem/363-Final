SELECT u.screen_name, u.sub_category, u.num_followers numFollowers FROM users u
WHERE u.sub_category = 'GOP'
ORDER BY u.num_followers desc
LIMIT 5