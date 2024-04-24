#!/bin/bash

## Set Variables
APP_NAME="raincatch"
DEV_DIR="/home/ubuntu/$APP_NAME/back"
JAR_PATH=$(find "$DEV_DIR" -name "*SNAPSHOT.jar" -print0 | xargs -0 ls -t | head -n 1)
ENV_PATH="$DEV_DIR/env"

APP_LOG="$DEV_DIR/log/app.log"
ERROR_LOG="$DEV_DIR/log/error.log"

## Get Root Permission
sudo chmod -R u+rwx $DEV_DIR # 현재 실행중인 스크립트에게 지정된 경로부터 하위 디렉토리까지 재귀적으로 root 권한 부여

## Stop Application
CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]
then
  echo "> Application Not Found: 종료할 어플리케이션이 없습니다."
else
  echo "> Stop Application: 실행중인 어플리케이션을 종료합니다. $CURRENT_PID"
  kill -SIGTERM "$CURRENT_PID"
  sleep 5
fi

## Start Application
NOW=$(date +%c)
mkdir -p $DEV_DIR/log
echo "> Start Application: 어플리케이션을 실행합니다. || $JAR_PATH ($NOW)"
. $ENV_PATH/env.sh # 설정해둔 환경변수 파일 실행 -> 스크립트 내에서만 지역적으로 변수 등록
nohup java -jar "$JAR_PATH" > $APP_LOG 2> $ERROR_LOG &
# nohup : 터미널이 끊겨도 계속 실행
# > $APP_LOG : 지정된 경로에 어플리케이션 로그를 등록
# 2> $ERROR_LOG : 지정된 경로에 에러로그를 등록 (명령어 실행에 실패했을 때)