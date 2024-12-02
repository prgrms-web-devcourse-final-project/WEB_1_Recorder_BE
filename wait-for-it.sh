#!/bin/bash
host="$1"
shift
until mysqladmin ping -h"$host" --silent; do
  echo "Waiting for MySQL..."
  sleep 2
done
exec "$@"
