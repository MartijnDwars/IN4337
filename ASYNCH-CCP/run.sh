#!/usr/bin/env bash

# Goto bin directory (so we can omit classpath, and rmiregistry just works)
cd ../out/production/ASYNCH-CCP


for i in `seq 1 $1`;
do
    # Start registry
    killall -9 rmiregistry
    rmiregistry &
    sleep .5

    # Run 2 registers
    java RunRegister 0 2 &
    pids[0]=$!
    java RunRegister 1 2 &
    pids[1]=$!

    # Run 2 processes
    java RunProcess 0 2 &
    pid1=$!
    java RunProcess 1 2 &
    pid2=$!

    wait $pid1;
    wait $pid2;
done;

# Kill all background processes
trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

