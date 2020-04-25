#!/bin/bash

# kill the processes which use the port 8080
sudo lsof -i tcp:8080 | awk '{print $2}' | grep -v PID | xargs sudo kill -9