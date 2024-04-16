# 메모장 관리 API 서버 만들기

## 비즈니스 로직과 API 설계
1. 유저
   - 사용자 생성
     - URI : /users
     - HTTP 메서드는 POST
2. 메모
   - 특정 사용자의 모든 메모 조회 
     - URI : /users/{userId}/memos
     - HTTP 메서드는 GET
   - 특정 사용자의 특정 메모 조회
     - URI : /users/{userId}/memos/{memoId}
     - HTTP 메서드는 GET
   - 특정 사용자의 메모 작성
     - URI : /users/{userId}/memos
     - HTTP 메서드는 POST
   - 특정 사용자의 특정 메모 수정
     - URI : /users/{userId}/memos/{memoId}
     - HTTP 메서드는 PATCH
   - 특정 사용자의 특정 메모 삭제
     - URI : /users/{userId}/memos/{memoId}
     - HTTP 메서드는 DELETE
