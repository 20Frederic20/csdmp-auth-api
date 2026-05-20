#!/usr/bin/env bash
set -euo pipefail

sudo systemctl start docker

# attente maximale en secondes
max_wait=60
echo "Waiting for Docker to be ready (up to ${max_wait}s)..."

while ! docker info >/dev/null 2>&1; do
    if (( max_wait-- <= 0 )); then
        echo "Docker did not become ready in time" >&2
        exit 1
    fi
    sleep 1
done

echo "Docker is ready, starting compose..."
docker compose up
