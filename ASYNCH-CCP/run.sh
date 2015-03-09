#!/usr/bin/env bash

# Goto bin directory (so we can omit classpath, and rmiregistry just works)
cd ../out/production/ASYNCH-CCP

# Start registry
rmiregistry &

# Run 2 register nodes
java RunRegister 0 2 &
java RunRegister 1 2 &

# Run 2 processes
java RunProcess 0 2 &
java RunProcess 1 2 &

# Wait for input from the user
read

# Kill all background processes
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

