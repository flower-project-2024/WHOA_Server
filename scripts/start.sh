#!/bin/bash 

ROOT_PATH="/home/ubuntu/spring-github-action"
JAR="$ROOT_PATH/application.jar"
JAR_SOURCE="$ROOT_PATH/build/libs/spring-github-action-1.0.0.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

# 복사할 JAR 파일 확인
if [ -f "$JAR_SOURCE" ]; then
  NOW=$(date +%c)

  # JAR 파일 복사
  echo "[$NOW] $JAR_SOURCE 복사" >> $START_LOG
  cp "$JAR_SOURCE" "$JAR"

  # .gitignore 처리된 파일들 복사
  cp "$ROOT_PATH/build/libs/application-*.yml" "$ROOT_PATH/"

  # JAR 파일 실행
  echo "[$NOW] > $JAR 실행" >> $START_LOG
  nohup java -jar "$JAR" > "$APP_LOG" 2> "$ERROR_LOG" &

  # 실행 중인 서버의 PID 확인
  SERVICE_PID=$(pgrep -f "$JAR")
  echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG
else
  echo "JAR 파일을 찾을 수 없습니다." >> "$START_LOG"
fi
