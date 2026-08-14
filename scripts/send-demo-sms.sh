#!/bin/sh
set -e

# 실행 중인 에뮬레이터에 시연용 문자를 보내는 스크립트
#
# 사용법:
#   scripts/send-demo-sms.sh [키워드] [발신번호]
#
# 키워드는 앱에 등록한 '문자 키워드'와 정확히 일치해야 잠금이 동작해요.
#   키워드    기본값 LOCK
#   발신번호  기본값 01099998888

: "${ANDROID_HOME:=$HOME/Library/Android/sdk}"
ADB="$ANDROID_HOME/platform-tools/adb"

KEYWORD="${1:-LOCK}"
SENDER="${2:-01099998888}"

[ -x "$ADB" ] || { echo "adb를 찾을 수 없어요: $ADB"; exit 1; }

SERIAL=$("$ADB" devices | awk '/emulator-.*device/{print $1; exit}')
[ -n "$SERIAL" ] || { echo "실행 중인 에뮬레이터가 없어요. 먼저 scripts/run-emulator.sh 를 실행하세요."; exit 1; }

echo "==> $SERIAL 로 문자 발송 (발신 $SENDER / 내용 \"$KEYWORD\")"
"$ADB" -s "$SERIAL" emu sms send "$SENDER" "$KEYWORD"
echo "완료했어요. 키워드가 앱 설정과 일치하면 잠금 화면이 떠요."
