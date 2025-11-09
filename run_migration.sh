#!/bin/bash

# Migration Script for TKHTH Database
# This script runs the migration SQL file against the PostgreSQL database

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Database connection parameters
DB_HOST="localhost"
DB_PORT="5231"
DB_NAME="tkht"
DB_USER="tkht_user"
DB_PASSWORD="tkht_pass123"

# Migration file
MIGRATION_FILE="migrationV2.sql"

echo -e "${YELLOW}🚀 Starting database migration...${NC}"

# Check if migration file exists
if [ ! -f "$MIGRATION_FILE" ]; then
    echo -e "${RED}❌ Error: Migration file '$MIGRATION_FILE' not found!${NC}"
    exit 1
fi

# Check if PostgreSQL client is installed
if ! command -v psql &> /dev/null; then
    echo -e "${RED}❌ Error: psql command not found. Please install PostgreSQL client.${NC}"
    exit 1
fi

# Set PGPASSWORD environment variable
export PGPASSWORD=$DB_PASSWORD

# Run migration
echo -e "${YELLOW}📝 Running migration from $MIGRATION_FILE...${NC}"

if psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -f $MIGRATION_FILE; then
    echo -e "${GREEN}✅ Migration completed successfully!${NC}"
    echo -e "${GREEN}📊 Database schema has been created.${NC}"
else
    echo -e "${RED}❌ Migration failed! Please check the error messages above.${NC}"
    exit 1
fi

# Unset PGPASSWORD
unset PGPASSWORD

echo -e "${GREEN}✨ Done!${NC}"

