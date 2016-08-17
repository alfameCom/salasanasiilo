ALTER TABLE alfame.passwords ADD COLUMN notes_2 CLOB(2147483647);

UPDATE alfame.passwords SET notes_2 = notes;

ALTER TABLE alfame.passwords DROP COLUMN notes;

RENAME COLUMN alfame.passwords.notes_2 TO notes;