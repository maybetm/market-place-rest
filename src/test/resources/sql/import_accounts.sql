insert into accounts (date_time_reg, email, login, password, role_id)
values (timezone('utc', now()), 'mail-admin-test', 'login-admin-test', 'password-admin-test', 2);