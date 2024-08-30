# Resume
`Kotlin Wasm`로 개발한 이력서 웹

Demo: https://resume.egchoi.cc

## 기능
- json파일을 기반으로 `composable`Ui를 작성함
- `composable`Ui를 이미지로 변환하여 PDF 파일로 다운로드
- `ocr`를 이용해 이미지로 된 PDF 파일에서 텍스트 레이어를 생성

> ### OCR
> 이미지에 있는 글씨를 인식하는 기술 <br />
> 이 프로젝트에서는 `ocrmypdf`를 이용해 이미지로된 PDF에 텍스트 레이어를 추가한다.

## 작성
- `composeApp/src/commonMain/composeResources/files/form.json` 파일을 작성
- Content 추가는 `Content` 클래스를 상속해서 `form.json`에서 사용할 `type`이름을 `SerialName`, `type`으로 작성한다.
```kotlin
@Serializable
sealed class Content {
    abstract val type: String
    abstract val composable: @Composable () -> Unit
}
```
```kotlin
// ex) PaddingContent.kt
@Serializable
@SerialName("p")
data class PaddingContent(
   override val type: String = "p",
   val value: Int
) : Content() {
   override val composable: @Composable () -> Unit
      get() = {
         Spacer(Modifier.height(value.dp))
      }
}
```

## 실행 (Run)
프론트엔드(wasm)에서 PDF 파일 생성 후 백엔드(ktor)로 PDF 파일 OCR 요청을 보낸다. <br />
OCR이 필요하다면 Ktor를 실행시킨 후 Wasm을 실행 시켜야한다. (OCR이 실패할 경우 기존 PDF 파일을 다운로드 한다.)


### (optinal) 1-1. ocrmypdf 설치

1. ocrmypdf 설치 <br />
https://ocrmypdf.readthedocs.io/en/latest/installation.html

2. 언어팩 설치: 한국어 언어팩(kor)을 설치 해야한다. <br />
https://ocrmypdf.readthedocs.io/en/latest/languages.html


macOS 설치 예시)
```shell
brew install ocrmypdf
```
```shell
# 언어팩 (macOS brew에서는 모든 언어팩이 설치된다.)
brew install tesseract-lang
```

### (optinal) 1-2. ktor 실행
1. `local.properties` 파일에 아래 내용을 추가한다.

```properties
#default 0.0.0.0
OCR_SERVER_HOST=0.0.0.0
#default 8081
OCR_SERVER_PORT=8081
```


2. `gradle`로 `server` | `application`에 있는 `run`을 실행한다. <br />

![스크린샷 2024-08-28 14 23 40](https://github.com/user-attachments/assets/a11f2206-3554-41f4-91d9-1e00aaa88f1b)

또는 gradle 명령어로 실행할 수 있다.
```shell
./gradlew :server:run
```


### 2. Wasm 실행
`gradle`로 `composeApp` | `Tasks` | `kotlin browser`에 있는 `wasmJsBrowserRun`을 실행한다. <br />

![스크린샷 2024-08-28 14 33 23](https://github.com/user-attachments/assets/613054d8-34c1-43bf-b60d-e9f4e4971ab3)

또는 gradle 명령어로 실행할 수 있다.,
```shell
./gradlew :composeApp:wasmJsBrowserRun
```


## Docker
배포는 두 가지 방식이 있다.
1. wasm을 빌드해서 ktor 서버에 static 파일로 올리는 방법
    - OCR을 이용할 경우 가장 편한 방법이다.
2. wasm을 nginx에 올리고 ktor를 별도 서버로 구동시키는 방법
   - CORS를 이용해 백엔드에 프론트엔드만 접근하도록 설정할 수 있다.

### wasm을 빌드해서 ktor 서버에 static 파일로 올리는 방법

1. wasm 빌드
```shell
./gradlew :composeApp:wasmJsBrowserDistribution
```

2. ktor 빌드
```shell
./gradlew :server:installDist
```

3. docker build
```shell
docker build . \
    -t resume-full \
    -f Dockerfile-full
```


### wasm을 nginx에 올리고 ktor를 별도 서버로 구동시키는 방법

1. wasm
```shell
./gradlew :composeApp:wasmJsBrowserDistribution
```
```shell
docker build . \
    -t resume-wasm \
    -f Dockerfile-wasm
```

2. ktor
```shell
./gradlew :server:installDist
```
```shell
docker build . \
    -t resume-ktor \
    -f Dockerfile-ktor
```

3. docker-compose.yml
```yml
services:
  ocr:
    image: resume-ktor
    restart: always
    ports:
      - 3001:8081
    environment:
       ALLOW_HOST: # all: 모든 호스트 허용
                   # same: 같은 호스트만 허용
                   # 또는 허용할 호스트 주소 입력 (ex: localhost:3000)

  resume:
    depends_on:
      - ocr
    image: resume-wasm
    ports:
      - 3000:80
    restart: always
    environment:
      OCR_SERVER: # ktor 서버 주소 입력 (ex: http://localhost:3001)
```
