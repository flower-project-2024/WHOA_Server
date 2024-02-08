#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-github-action"
JAR="$ROOT_PATH/application.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR) # 실행 중인 Spring 서버의 PID

if [ -z "$SERVICE_PID" ]; then
  echo "서비스 NotFound" >> $STOP_LOG
else
  echo "서비스 종료 " >> $STOP_LOG
  kill "$SERVICE_PID"
  # 또는, 강제 종료를 하고 싶다면:
  # kill -9 $SERVICE_PID
fi
