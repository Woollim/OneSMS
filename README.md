# OneSMS

휴대전화를 잃어버렸을 때 문자 한 통으로 원격 잠그는 안드로이드 앱이에요. 미리 정해 둔 키워드 문자를 받으면 화면을 잠그고 경고음을 울려서, 다른 사람이 기기를 쓰지 못하게 막아요. 동시에 등록해 둔 연락처로 기기의 현재 위치를 문자로 보내 되찾을 단서를 남겨요.

## 데모

앱을 켜고 키워드와 해제 암호를 등록한 다음, 문자를 받아 잠기고 다시 해제하기까지 전체 흐름이에요. 화면을 잠그는 순간 경고음이 울려요.

![OneSMS 데모](resource/demo.gif)

소리를 포함한 원본 영상은 [resource/demo.mp4](resource/demo.mp4)에서 볼 수 있어요.

## 주요 기능

- 지정한 키워드 문자를 받으면 화면을 잠가요.
- 잠금과 동시에 기기 기본 벨소리를 알람으로 울려요.
- 등록한 연락처로 기기의 현재 위치를 문자로 보내요.
- 해제 암호를 입력해야 잠금을 풀 수 있어요.
- 재부팅 후에도 잠금 상태를 유지해요.

## 동작 방식

문자를 받으면 `OneSMSReceiver`가 브로드캐스트를 가로채서 내용이 등록한 키워드와 정확히 일치하는지 확인해요. 일치하면 잠금 화면을 띄우는 `LockScreenService`를 시작하고, 발신자에게 위치를 문자로 회신해요.

잠금 화면은 다른 앱 위에 오버레이로 그려요. 해제 암호가 맞으면 오버레이를 걷어내고 서비스를 종료해요.

## 화면 구성

- 설정 화면에서 서비스를 켜고 끄며, 등록한 키워드와 연락처를 확인해요.
- 편집 화면에서 키워드 문자, 해제 암호, 연락처를 등록해요.
- 잠금 화면에서 분실 안내와 연락처를 보여주고, 암호 입력으로 해제해요.

## 기술 스택

- 언어는 Kotlin을 써요.
- 네트워크 통신은 Retrofit과 OkHttp로 처리해요.
- 위치 조회는 Google Play Services의 FusedLocationProvider를 써요.
- 권한 요청은 TedPermission으로 처리해요.
- 이미지 로딩은 Glide를 써요.

## 빌드하고 실행하기

2018년에 만든 프로젝트라 그 시절 빌드 도구(Gradle 4.1, Android Gradle Plugin 3.0.1, Kotlin 1.1.51)를 그대로 써요. 최신 macOS에서 빌드하려면 아래 환경이 필요해요.

### 요구 사항

- JDK 8 (구형 Android Gradle Plugin이 상위 버전 JDK와 호환되지 않아요)
- Android SDK Platform 26
- Android SDK Build-Tools 26.0.2

### 빌드

프로젝트 최상위에 SDK 경로를 담은 `local.properties`를 만들어요.

```properties
sdk.dir=/Users/사용자이름/Library/Android/sdk
```

디버그 APK는 다음 명령으로 빌드해요. 빌드 결과는 `app/build/outputs/apk/debug/`에 생겨요.

```bash
JAVA_HOME=<JDK 8 경로> ./gradlew :app:assembleDebug
```

## 프로젝트 구조

```
app/src/main/java/root/onesms/
├── Activity/     화면 (설정, 편집, 잠금 등)
├── Service/      잠금 화면을 띄우는 백그라운드 서비스
├── Manager/      문자 수신, 잠금 화면, 위치 회신, 사운드 처리
├── Connect/      Retrofit API 정의
└── Util/         공통 유틸리티
```

## 알려진 제약

- 잠금 시 재생하는 소리는 기기 기본 벨소리예요. `res/raw/siren.mp3`는 지금 코드에서 쓰지 않아요.
- 위치를 회신할 때 좌표를 짧은 링크로 바꾸는 데 Google URL Shortener API를 쓰는데, 이 API는 2019년에 종료됐어요. 좌표 자체는 문자로 전달돼요.
