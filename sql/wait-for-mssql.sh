#!/bin/bash

SERVER="evdealer-mssql,1433"
MSSQL_SA_PASSWORD='6$3S6zPKpJ+Y2-*J'

# Wait until SQL Server is ready
echo "Waiting for SQL Server to start..."
sleep 15s

# Run all SQL scripts in folder
for file in /opt/sql/*.sql; do
    echo "Running $file..."
    /opt/mssql-tools/bin/sqlcmd -S $SERVER -U sa -P "$MSSQL_SA_PASSWORD" -i "$file"
done

echo "SQL scripts run successfully"