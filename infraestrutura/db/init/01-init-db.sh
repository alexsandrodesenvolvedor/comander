#!/bin/bash
set -euo pipefail

: "${POSTGRES_USER?Need POSTGRES_USER}"
: "${POSTGRES_DB?Need POSTGRES_DB}"
: "${DB_ROLE:=infra_role}"
: "${DB_ROLE_PASSWORD:=change_me_role_password}"

# This script runs during the image init phase (only when the DB volume is empty).
# It creates a role (if not exists) and sets the target database owner to that role.

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
DO
\$do\$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '${DB_ROLE}') THEN
      EXECUTE format('CREATE ROLE %I WITH LOGIN PASSWORD %L', '${DB_ROLE}', '${DB_ROLE_PASSWORD}');
   END IF;
END
\$do\$;

-- Ensure the database exists (it should be created by POSTGRES_DB env) and set owner
ALTER DATABASE "${POSTGRES_DB}" OWNER TO "${DB_ROLE}";
EOSQL
