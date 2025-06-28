# Holiday Manager

## 🧩 프로젝트 개요

- 목적: 외부 API를 활용해 최근 5년(2020 ~ 2025)의 **전 세계 공휴일 데이터를 저장, 조회, 관리**하는 서버 구현
- 기능: 데이터 적재, 필터 기반 검색, 재동기화(Upsert), 삭제, 배치 자동화
  <br>

## 🧩 기술 스택
| 기술        | 설명                            |
|-------------|-------------------------------|
| Java        | 21                            |
| Spring Boot | 3.4                           |
| DB          | H2 (In-memory)                |
| ORM         | JPA (Hibernate) + Querydsl 5  |
| Test        | JUnit 5                       |
| 문서화      | OpenAPI 3 (Swagger UI)        |
| 기타        | Dockerfile, GitHub Actions CI |
<br>

## 🧩 실행 방법
```bash
./gradlew bootRun
```
<br>

## 🧩 기능 요구사항
### 1. 데이터 적재
- `GET https://date.nager.at/api/v3/AvailableCountries` 에서 국가 목록 조회
- `GET https://date.nager.at/api/v3/PublicHolidays/{year}/{countryCode}` 에서 공휴일 수집
- 최초 실행시 시 5년 × N개 국가를 **일괄 적재**

### 2. 검색
- 연도별·국가별 필터 기반 공휴일 조회
- from ~ to 기간, 공휴일 타입 등 추가 필터 자유 확장
- 결과는 **페이징** 형태로 응답

### 3. 재동기화(Refresh)
- 특정 연도·국가 데이터를 재호출하여 **Upsert**(덮어쓰기) 가능

### 4. 삭제
- 특정 연도·국가의 공휴일 레코드 **전체 삭제**

### 5. (선택) 배치 자동화
- 매년 1월 2일 01:00 KST에 전년도·금년도 데이터를 자동 동기화
  <br>

## 🧩 작업 목록
| Epic  | Task 코드 | 설명                      |
|-------|-----------|---------------------------|
| HM-0  | -         | 전체 프로젝트 에픽         |
|       | HM-1      | 요구사항 문서화            |
|       | HM-2      | 멀티모듈 구조 설계         |
|       | HM-3      | 외부 API 연동            |
|       | HM-4      | API 서버 구현            |
|       | HM-5      | DB 테이블 설계 및 연동     |
|       | HM-6      | 전체 공휴일 데이터 일괄 적재 |
|       | HM-7      | 배치 자동화 기능 (선택)     |
|       | HM-8      | Swagger 기반 API 문서화   |
<br>

## 🧩 체크리스트

- 빌드 & 실행 방법
  - `./gradlew :api:bootRun`
- ./gradlew clean test 성공 스크린샷 (테스트 작성 시)
  - ![img.png](gradlew-clean-build.png)
- Swagger UI 또는 OpenAPI JSON 노출 확인 방법
  - http://localhost:8080/swagger-ui/index.html
- 설계한 REST API 명세 요약** (엔드포인트·파라미터·응답 예시)
  - `GET /api/v1/holidays`
    - 설명: 공휴일 목록 조회
    - 파라미터:
      - year (optional): 연도 필터
      - countryCode (optional): 국가 코드 필터
      - from (optional): 시작 날짜 필터 (YYYY-MM-DD)
      - to (optional): 종료 날짜 필터 (YYYY-MM-DD)
      - page (required): 페이지 번호
      - size (required): 페이지 크기
    - 응답 예시:
      ```json
      {
        "page": 1,
        "size": 10,
        "totalElements": 1,
        "contents": [
          {
            "date": "2023-01-01",
            "localName": "New Year's Day",
            "countryCode": "US"
          }
        ]
      }
      ```
  - `PUT /api/v1/holidays/{year}/{countryCode}`
    - 설명: 특정 연도·국가의 공휴일 데이터 재동기화
    - 파라미터:
      - year (required): 연도 
      - countryCode (required): 국가 코드

  - `DELETE /api/v1/holidays/{year}/{countryCode}`
    - 설명: 특정 연도·국가의 공휴일 데이터 삭제
    - 파라미터:
      - year (required): 연도
      - countryCode (required): 국가 코드
