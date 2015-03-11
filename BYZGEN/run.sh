#!/usr/bin/env bash

# Goto bin directory (so we can omit classpath, and rmiregistry just works)
cd ../out/production/BYZGEN

# Start registry
killall -9 rmiregistry
sleep .5
rmiregistry &

# Run 2 register nodes
java RunProcess 0 8 0 &
java RunProcess 1 8 1 &
java RunProcess 2 8 0 &
java RunProcess 3 8 1 &
java RunProcess 4 8 0 &
java RunProcess 5 8 1 &
java RunProcess 6 8 0 &
java RunAdversary 7 8 0 &

# Wait for input from the user
read

# Kill all background processes
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

