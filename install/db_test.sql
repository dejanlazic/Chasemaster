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

INSERT INTO players (match_id, username, password, colour, registered_on) VALUES (1, 'player01', 'player01', 'WHITE', '2013-05-01');
--INSERT INTO players (match_id, username, password, colour, registered_on) VALUES (1, 'player02', 'player02', 'BLACK', '2013-05-02');
--INSERT INTO players (match_id, username, password, colour, registered_on) VALUES (1, 'player03', 'player03', 'BLACK', '2013-05-03');

COMMIT;

SELECT * FROM players;