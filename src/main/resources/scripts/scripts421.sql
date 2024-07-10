ALTER TABLE student ADD CONSTRAINT age_require CHECK ( age > 16 );
ALTER TABLE student ADD CONSTRAINT name_unique_require UNIQUE (name);
ALTER TABLE student ALTER COLUMN name SET NOT NULL;
ALTER TABLE faculty ADD CONSTRAINT name_and_name_unique_require UNIQUE (name, color);
ALTER TABLE student ALTER COLUMN age SET DEFAULT 20;