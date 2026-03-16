#!/bin/bash
set -a
source .env
set +a

find src -name "*.java" | xargs javac -d build/classes -cp build/classes 2>&1
java -cp build/classes app.Main
