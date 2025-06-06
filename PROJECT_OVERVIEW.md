# Priorify 프로젝트 개요

## 프로젝트 소개
Priorify는 일정 관리 시스템으로, 사용자들이 일정을 효율적으로 관리하고 우선순위를 자동으로 계산해 주는 기능을 제공합니다. 이 프로젝트는 Spring Boot 백엔드와 MongoDB를 사용하여 구현되었으며, 구글 OAuth 로그인 기능을 지원합니다. 프론트엔드는 Next.js를 기반으로 구현되어 직관적인 일정 시각화와 관리 기능을 제공합니다.

## 주요 기능

### 1. 사용자 인증 시스템
- 일반 회원가입 및 로그인
- 구글 OAuth를 통한 소셜 로그인
- JWT 토큰 기반 인증

### 2. 일정 관리 시스템
- 일정 생성, 조회, 수정, 삭제 기능
- 카테고리별 일정 분류
- 일정 상태 관리 (active, completed)
- 구글 일정과의 연동 가능성

### 3. 우선순위 계산 시스템
- 일정에 대한 우선순위 자동 계산
- 벡터 기반 우선순위 시스템 (우선순위 점수, 시간 특성을 벡터로 표현)
- 사용자 선호도와 시간 근접성을 고려한 종합 점수 계산
- 시간 특성의 벡터화 (요일, 시간대, 월, 계절, 일정 주기, 현재와의 근접성)
- Vector Search를 통한 우선순위 높은 일정 효율적 검색
- MongoDB Aggregation Pipeline을 활용한 복잡한 우선순위 쿼리 처리

### 4. 분석 기능
- 카테고리별 일정 통계
- 생산성 메트릭 제공
- 완료율 분석

### 5. 직관적 일정 시각화 (프론트엔드)
- 우선순위에 따라 크기가 다른 노드로 일정 표현
- 연결된 일정들을 Node Connection Graph로 시각화
- 중요한 일정을 한눈에 파악할 수 있는 직관적 UI
- 노드 간 관계를 통해 일정 간 연관성 표현
- 노드 상호작용을 통한 직관적인 일정 관리

## 기술적 과제 및 개선점

### 1. 시간 벡터화 개선
- **문제점**: 현재 계절 표현 방식은 선형적(봄:0.25, 여름:0.5, 가을:0.75, 겨울:1.0)이어서 12월과 3월 같이 실제로는 가까운 월이 벡터 공간에서 멀리 표현됨
- **해결 방향**: 계절을 원형 공간에 매핑하여 표현 (사인/코사인 함수 활용)
- **기대 효과**: 월과 계절의 순환적 특성을 잘 반영한 벡터 표현 가능

### 2. 구글 OAuth 통합
- 현재는 JSON 응답 방식으로 구현됨
- 프론트엔드와의 통합을 위한 콜백 URL 조정 필요

### 3. 일정 우선순위 알고리즘 개선
- 사용자 행동 패턴을 학습하여 더 정확한 우선순위 계산
- 카테고리별 중요도의 개인화 향상
- Vector Search 최적화를 통한 유사 일정 추천 기능 구현

### 4. 프론트엔드 그래프 시각화 최적화
- 대량의 일정 처리 시 그래프 렌더링 성능 개선
- 복잡한 일정 연결 관계의 효과적인 시각화 방법 연구
- 모바일 환경에서의 그래프 상호작용 UX 개선

### 5. 데이터베이스 쿼리 최적화
- MongoDB Aggregation Pipeline을 활용한 복잡한 데이터 처리 패턴 구현
- 인덱싱 전략 개선을 통한 Vector Search 성능 향상
- 대용량 일정 데이터 처리 시 쿼리 효율성 개선

## 데이터 모델

### 사용자(User)
- 기본 정보: 이름, 비밀번호
- 구글 연동 정보: googleId, email, displayName
- 우선순위 설정: highPriorities, lowPriorities (CategoryPriority 목록)

### 일정(Schedule)
- 기본 정보: 제목, 카테고리, 시작/종료 시간
- 상태 정보: 상태(active/completed)
- 연결 정보: 연결된 일정 목록, 외부 이벤트 ID
- 우선순위 벡터: 종합점수, 시간점수, 사용자선호도, 시간특성벡터

### 카테고리 우선순위(CategoryPriority)
- 카테고리명
- 순위 (rank)

## 개발 계획

### 1단계: 기본 기능 구현 (완료)
- 사용자 인증 시스템 구현
- 기본 일정 관리 기능 구현
- 단순 우선순위 계산 로직 구현

### 2단계: 우선순위 시스템 고도화 (진행 중)
- 벡터 기반 우선순위 계산 시스템 구현
- 시간 벡터화 개선
- 사용자 선호도 시스템 개선
- Vector Search 구현 및 최적화
- MongoDB Aggregation Pipeline을 활용한 고급 쿼리 패턴 구현

### 3단계: 통합 및 최적화
- 구글 캘린더 연동 기능 완성
- 사용자 인터페이스 개선
- 성능 최적화
- 데이터베이스 인덱싱 전략 개선

### 4단계: 프론트엔드 고도화
- Node Connection Graph 시각화 완성
- 사용자 상호작용 개선
- 백엔드 API와의 완전한 통합
- 반응형 UI 최적화

## 기술 스택
- 백엔드: Spring Boot
- 데이터베이스: MongoDB (Vector Search, Aggregation Pipeline 활용)
- 인증: JWT, OAuth2
- 프론트엔드: Next.js, React, D3.js/Sigma.js (그래프 시각화 라이브러리)

## 프론트엔드 주요 컴포넌트

### 1. 노드 그래프 시각화 (Node Connection Graph)
- 일정을 노드로 표현하며, 우선순위에 따라 노드 크기 조정
- 연관된 일정은 엣지(edge)로 연결하여 관계 표현
- 사용자가 직관적으로 중요 일정을 파악할 수 있는 시각적 표현

### 2. 일정 관리 인터페이스
- 드래그 앤 드롭으로 일정 생성 및 수정
- 노드 클릭을 통한 상세 정보 확인
- 일정 간 연결 관계 생성 및 수정

### 3. 분석 대시보드
- 우선순위 분포 시각화
- 카테고리별 일정 통계
- 완료율 및 생산성 지표 제공

## MongoDB 활용 전략

### 1. Vector Search 구현
- 우선순위 벡터를 기반으로 유사한 일정 검색
- 사용자 패턴과 유사한 시간대/카테고리의 일정 추천
- 벡터 유사도 계산을 통한 효율적인 우선순위 높은 일정 검색

### 2. Aggregation Pipeline 활용
- 복잡한 조건의 일정 필터링 (시간, 카테고리, 우선순위 등)
- 다양한 시각적 분석을 위한 데이터 집계
- 사용자별 일정 패턴 분석
- 일정 데이터의 효율적인 변환 및 처리 