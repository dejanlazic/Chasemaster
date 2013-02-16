-- USAGE:
--		mysql -u root -padmin
--		source db_test.sql

-- populate tables

-- INSERT INTO roles (name) VALUES ('HOME_VISITOR');
-- INSERT INTO roles (name) VALUES ('SUPERVISOR');

-- INSERT INTO users (username, password, password_changed, role_id)
-- (SELECT 'Visitor1', 'visitor1', 0, id FROM roles WHERE name = 'HOME_VISITOR');
-- INSERT INTO users (username, password, role_id)
-- (SELECT 'SuperVisor1', 'supervisor1',id FROM roles WHERE name = 'SUPERVISOR');

INSERT INTO players (username, password, first_name, last_name, registered_on) VALUES ('john', 'doe', 'John', 'Doe', '2013-02-01');
INSERT INTO players (username, password, first_name, last_name, registered_on) VALUES ('jim', 'smyth', 'Jim', 'Smyth', '2013-02-02');

COMMIT;

SELECT * FROM players;