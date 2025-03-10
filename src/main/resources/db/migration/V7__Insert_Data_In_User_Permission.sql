INSERT INTO users_permissions (id_user, id_permission)
SELECT id as id_user, 1 as id_permission
FROM users WHERE user_name = 'admin';