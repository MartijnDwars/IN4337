#!/usr/bin/env bash

# Goto bin directory (so we can omit classpath, and rmiregistry just works)
cd ../out/production/SYNCH-CCP

# Run 2 register nodes
for i in `seq 1 10000`;
do
    java Main
done

# Kill all background processes
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

