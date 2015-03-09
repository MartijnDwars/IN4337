#!/usr/bin/env bash

# Goto bin directory (so we can omit classpath, and rmiregistry just works)
cd ../out/production/BYZGEN

# Start registry
rmiregistry &

# Run 2 register nodes
java RunProcess 0 6 0 &
java RunProcess 1 6 1 &
java RunProcess 2 6 0 &
java RunProcess 3 6 1 &
java RunProcess 4 6 0 &
java RunProcess 5 6 0 &

# Wait for input from the user
read

# Kill all background processes
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

