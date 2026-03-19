# 📘 Contents CRUD API

## 📌 1. 프로젝트 소개
JWT 인증 기반의 콘텐츠 관리 API 서버입니다.  
사용자는 콘텐츠를 생성, 조회, 수정, 삭제할 수 있으며, 작성자 또는 관리자 권한에 따라 접근이 제어됩니다.

또한, 콘텐츠 목록 조회 시 페이징 및 정렬 기능을 제공하여 효율적인 데이터 조회를 지원합니다.

---

## 🚀 2. 실행 방법

### 📋 요구 사항
- Java 17 이상
- Gradle
- H2 Database (내장 DB 사용)

---

### ▶️ 실행 방법

./gradlew bootRun

또는 IDE에서 Application 클래스를 실행합니다.

---

### 🌐 접속 주소

- 서버: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI 문서(JSON): http://localhost:8080/v3/api-docs

---

## 🛠️ 3. 기술 스택

- Language: Java
- Framework: Spring Boot
- Security: Spring Security, JWT
- ORM: JPA (EntityManager, JPQL)
- Database: H2
- API 문서화: Swagger (springdoc-openapi)

---

## 📂 4. 프로젝트 구조

com.malgn
├── content        # 콘텐츠 도메인 (Controller, Service, Repository)
├── configure      # 설정 (Swagger, Security 등)
├── exception      # 전역 예외 처리
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

### 주요 예외

VALIDATION_ERROR (400) : 요청 값 검증 실패  
BAD_CREDENTIALS (401) : 로그인 실패  
CONTENT_NOT_FOUND (404) : 콘텐츠 없음  
CONTENT_MODIFY_NO_PERMISSION (403) : 권한 없음  
DATABASE_ERROR (409) : DB 제약조건 오류

---

## 📡 8. REST API 문서

Swagger UI를 통해 API 명세를 확인할 수 있습니다.

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

### 주요 API

POST   /content         : 콘텐츠 생성  
PUT    /content/{id}    : 콘텐츠 수정  
DELETE /content/{id}    : 콘텐츠 삭제  
GET    /content         : 콘텐츠 목록 조회 (페이징)  
GET    /content/{id}    : 콘텐츠 상세 조회

---

## 📥 9. 요청 예시

콘텐츠 목록 조회

GET /content?page=1&size=10&sortBy=viewCount&direction=desc

---

콘텐츠 생성

{
"title": "제목",
"description": "내용"
}

---

## 📈 10. 추가 구현 사항

- Swagger(OpenAPI) 기반 API 문서화
- JWT 인증 시스템 구현
- GlobalExceptionHandler를 통한 예외 처리 통일
- offset 기반 페이징 처리
- 정렬 기준 동적 처리 (JPQL)
- 권한 기반 접근 제어 (작성자 / 관리자)

---

## 🤖 11. 사용한 AI 도구 / 참고 자료

- ChatGPT: API 설계, 예외 처리 구조, README 작성 보조
- Spring 공식 문서
- Hibernate / JPA 공식 문서
- Swagger (springdoc-openapi) 공식 문서

---

## 💡 12. 설계 포인트

- Entity는 setter 대신 행위 메서드(update) 사용
- Service 계층에서 비즈니스 로직 및 권한 검증 처리
- Controller는 요청/응답 처리 역할만 담당
- DTO를 통해 Entity 노출 방지

---

## 📌 13. 한 줄 요약

JWT 인증 기반으로 콘텐츠를 관리하며,  
페이징/정렬/권한 제어까지 고려한 REST API 서버입니다.