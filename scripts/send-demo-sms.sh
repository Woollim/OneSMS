#!/bin/sh
set -e

# 실행 중인 에뮬레이터에 시연용 문자를 보내는 스크립트
#
# 사용법:
#   scripts/send-demo-sms.sh [키워드] [발신번호]
#   scripts/send-demo-sms.sh --location [경도,위도] [키워드] [발신번호]
#
# 키워드는 앱에 등록한 '문자 키워드'와 정확히 일치해야 잠금이 동작해요.
#   키워드    기본값 LOCK
#   발신번호  기본값 01099998888
#
# --location 을 붙이면 문자 발송 전에 에뮬레이터에 위치를 주입해요.
# 위치가 있어야 앱이 발신자에게 실제 좌표 링크를 회신해요.
#   경도,위도를 생략하면 서울시청 좌표(126.9780,37.5665)를 써요.

: "${ANDROID_HOME:=$HOME/Library/Android/sdk}"
ADB="$ANDROID_HOME/platform-tools/adb"

# 서울시청 기본 좌표 (경도, 위도 순서)
LON=126.9780
LAT=37.5665
INJECT_LOCATION=0
POSITIONAL=""

while [ $# -gt 0 ]; do
  case "$1" in
    --location|-l)
      INJECT_LOCATION=1
      # 다음 인자가 "경도,위도" 형태면 좌표로 사용해요.
      case "$2" in
        *,*) LON="${2%%,*}"; LAT="${2##*,}"; shift ;;
      esac
      shift ;;
    *)
      POSITIONAL="$POSITIONAL $1"; shift ;;
  esac
done
# shellcheck disable=SC2086
set -- $POSITIONAL

KEYWORD="${1:-LOCK}"
SENDER="${2:-01099998888}"

[ -x "$ADB" ] || { echo "adb를 찾을 수 없어요: $ADB"; exit 1; }

SERIAL=$("$ADB" devices | awk '/emulator-.*device/{print $1; exit}')
[ -n "$SERIAL" ] || { echo "실행 중인 에뮬레이터가 없어요. 먼저 scripts/run-emulator.sh 를 실행하세요."; exit 1; }

if [ "$INJECT_LOCATION" = "1" ]; then
  echo "==> $SERIAL 에 위치 주입 (경도 $LON, 위도 $LAT)"
  # 지도 앱으로 GPS를 한 번 깨운 뒤 좌표를 반복 주입해야 lastLocation이 갱신돼요.
  "$ADB" -s "$SERIAL" shell monkey -p com.google.android.apps.maps -c android.intent.category.LAUNCHER 1 >/dev/null 2>&1 || true
  sleep 5
  i=0
  while [ $i -lt 6 ]; do
    "$ADB" -s "$SERIAL" emu geo fix "$LON" "$LAT" >/dev/null 2>&1 || true
    sleep 1
    i=$((i + 1))
  done
fi

echo "==> $SERIAL 로 문자 발송 (발신 $SENDER / 내용 \"$KEYWORD\")"
"$ADB" -s "$SERIAL" emu sms send "$SENDER" "$KEYWORD"
echo "완료했어요. 키워드가 앱 설정과 일치하면 잠금 화면이 떠요."
