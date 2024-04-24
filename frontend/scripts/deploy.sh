#!/bin/bash

if nginx -t
then systemctl reload nginx
else
  echo "Nginx 구성 파일에 오류가 있습니다."
  exit 1
fi