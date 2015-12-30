#!/bin/bash


if [ "x$1" == "x" ]; then
	echo "Usage: $0 DEVKEY APPKEY"
	exit -1
fi

if [ "x$2" == "x" ]; then
	echo "Usage: $0 DEVKEY APPKEY"
	exit -1
fi

DEVKEY=$1
APPKEY=$2

curl -H "DevKey: $DEVKEY" -H "AppKey: $APPKEY" "http://opendata.tfgm.com/api/Carparks?pageIndex=0&pageSize=20"

