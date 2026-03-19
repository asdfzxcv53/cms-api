# 📘 Contents CRUD API

## 📌 1. 프로젝트 소개
JWT 인증 기반의 콘텐츠 관리 API 서버입니다.  
사용자는 콘텐츠를 생성, 조회, 수정, 삭제할 수 있으며, 작성자 또는 관리자 권한에 따라 접근이 제어됩니다.

또한, 콘텐츠 목록 조회 시 페이징 및 정렬 기능을 제공하여 효율적인 데이터 조회를 지원합니다.

---

## 🚀 2. 실행 방법


### ▶️ 실행 방법

./gradlew bootRun

또는 IDE에서 Application 클래스를 실행합니다.

---

### 🌐 접속 주소

- 서버: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI 문서(JSON): http://localhost:8080/v3/api-docs

---

### 🚀 프로젝트 실행 방법

- 서버 실행 후 Swagger UI 로 접속
- 회원가입과 로그인 API를 수행
- 로그인 응답으로 받은 jwt 를 오른쪽 위 Authorize 에 토큰 입력
- 그 후 인증이 필요한 API 테스트 진행

---

## 🛠️ 3. 기술 스택

- Language: Java 25
- Build Tool : Gradle 9.0
- Framework: Spring Boot 4.x
- Security: Spring Security, JWT
- ORM: JPA (EntityManager, JPQL)
- Database: H2
- API 문서화: Swagger (springdoc-openapi)

---

## 📂 4. 프로젝트 구조

com.malgn\
├── content        # 콘텐츠 도메인 (Controller, Service, Repository)\
├── configure      # 설정 (Swagger, Security 등)\
├── exception      # 전역 예외 처리\
├── members        # 사용자 관련 기능

---

## ⚙️ 5. 구현 기능

### 🔐 인증 / 사용자
- 회원가입
- 로그인 (JWT 발급)
- 인증 기반 API 접근 제어

---

### 📝 콘텐츠 기능
- 콘텐츠 생성
- 콘텐츠 수정
- 콘텐츠 삭제
- 콘텐츠 상세 조회
- 콘텐츠 목록 조회 (페이징 처리)

---

### 📊 페이징 및 정렬
- offset 기반 페이징 처리
- 정렬 기준 선택 가능
  - id (기본)
  - viewCount (조회수)
  - createdDate (생성일)

---

## 🔒 6. 권한 처리

콘텐츠 수정 및 삭제는 다음 조건을 만족해야 합니다.

- 작성자 본인
- 또는 ADMIN 권한 사용자

작성자 == 로그인 사용자 OR ROLE_ADMIN

권한이 없는 경우 403 Forbidden 응답을 반환합니다.

---

## ❗ 7. 예외 처리

@RestControllerAdvice를 사용하여 전역 예외를 처리하고,  
모든 에러 응답을 동일한 형식으로 반환합니다.

### ErrorResponse 형식

{
"code": "ERROR_CODE",
"message": "에러 메시지"
}

---

## 📡 8. REST API 문서

Swagger UI를 통해 API 명세를 확인할 수 있습니다.

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

## 9. 주요 API

POST   /content         : 콘텐츠 생성  
PUT    /content/{id}    : 콘텐츠 수정  
DELETE /content/{id}    : 콘텐츠 삭제  
GET    /content         : 콘텐츠 목록 조회 (페이징)  
GET    /content/{id}    : 콘텐츠 상세 조회

---


## 📈 10. 추가 구현 사항

- Swagger(OpenAPI) 기반 API 문서화
- JWT 인증 시스템 구현
- GlobalExceptionHandler를 통한 예외 처리 통일
- 정렬 기준 동적 처리 (JPQL)
- 정렬 기준에 따른 인덱싱

---

## 🤖 11. 사용한 AI 도구

- ChatGPT: API 설계 검증, 예외 처리 구조, README 작성 보조

---