#!/bin/sh
set -e

# OneSMS를 에뮬레이터에서 빌드하고 설치해 실행하는 스크립트
#
# 사용법:
#   scripts/run-emulator.sh [AVD이름]
#
# 경로는 환경변수로 바꿀 수 있어요.
#   ANDROID_HOME  Android SDK 경로 (기본: ~/Library/Android/sdk)
#   JAVA_HOME     JDK 8 경로       (기본: ~/.jdks/zulu8/Contents/Home)
#   AVD_NAME      사용할 AVD 이름  (기본: onesms_test)

PROJECT_DIR=$(cd "$(dirname "$0")/.." && pwd)
cd "$PROJECT_DIR"

: "${ANDROID_HOME:=$HOME/Library/Android/sdk}"
: "${JAVA_HOME:=$HOME/.jdks/zulu8/Contents/Home}"
AVD_NAME="${1:-${AVD_NAME:-onesms_test}}"
PKG=root.onesms
APK=app/build/outputs/apk/debug/app-debug.apk

ADB="$ANDROID_HOME/platform-tools/adb"
EMULATOR="$ANDROID_HOME/emulator/emulator"
export ANDROID_HOME JAVA_HOME

# 필요한 도구 확인
[ -x "$ADB" ] || { echo "adb를 찾을 수 없어요: $ADB"; exit 1; }
[ -x "$EMULATOR" ] || { echo "emulator를 찾을 수 없어요: $EMULATOR"; exit 1; }
[ -d "$JAVA_HOME" ] || { echo "JDK 8을 찾을 수 없어요: $JAVA_HOME"; exit 1; }

# AVD 존재 확인
if ! "$EMULATOR" -list-avds | grep -qx "$AVD_NAME"; then
  echo "AVD '$AVD_NAME'가 없어요. 사용 가능한 AVD 목록:"
  "$EMULATOR" -list-avds
  exit 1
fi

# SDK 경로를 담은 local.properties가 없으면 생성
if [ ! -f local.properties ]; then
  echo "sdk.dir=$ANDROID_HOME" > local.properties
  echo "local.properties를 생성했어요."
fi

echo "==> APK 빌드"
./gradlew :app:assembleDebug

# 이미 실행 중인 에뮬레이터가 없으면 부팅
SERIAL=$("$ADB" devices | awk '/emulator-.*device/{print $1; exit}')
if [ -z "$SERIAL" ]; then
  echo "==> 에뮬레이터 부팅: $AVD_NAME"
  "$EMULATOR" -avd "$AVD_NAME" -no-snapshot -no-boot-anim >/dev/null 2>&1 &
  "$ADB" wait-for-device
  echo "==> 부팅을 기다리는 중..."
  until [ "$("$ADB" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do
    sleep 2
  done
  SERIAL=$("$ADB" devices | awk '/emulator-.*device/{print $1; exit}')
fi
echo "==> 대상 에뮬레이터: $SERIAL"

echo "==> APK 설치"
"$ADB" -s "$SERIAL" install -r "$APK"

echo "==> 권한 부여"
for p in RECEIVE_SMS SEND_SMS READ_PHONE_STATE ACCESS_FINE_LOCATION ACCESS_COARSE_LOCATION; do
  "$ADB" -s "$SERIAL" shell pm grant "$PKG" "android.permission.$p" 2>/dev/null || true
done
"$ADB" -s "$SERIAL" shell appops set "$PKG" SYSTEM_ALERT_WINDOW allow 2>/dev/null || true

echo "==> 앱 실행"
"$ADB" -s "$SERIAL" shell monkey -p "$PKG" -c android.intent.category.LAUNCHER 1 >/dev/null 2>&1

echo ""
echo "완료했어요. 앱에서 문자 키워드, 해제 암호, 연락처를 등록하고 '서비스 실행'을 켠 뒤"
echo "scripts/send-demo-sms.sh 로 시연 문자를 보내보세요."
