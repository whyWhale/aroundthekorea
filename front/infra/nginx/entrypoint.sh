#!/bin/sh

# Nginx 설정 파일에 환경 변수 삽입
envsubst '${SERVER_NAME}' < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf

# Nginx 실행
exec "$@"