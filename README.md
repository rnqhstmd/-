# 메모장 관리 API 서버 만들기

## 비즈니스 로직과 API 설계
<<<<<<< feature/like

1. 유저
    - 회원가입
        - URI : /users/join
        - HTTP 메서드 : POST
    - 로그인
        - URI : /users/login
        - HTTP 메서드 : POST
    - 사용자 수정
        - URI : /users
        - HTTP 메서드 : PATCH
    - 사용자 삭제
        - URI : /users
        - HTTP 메서드 : DELETE
2. 소속
    - 소속 생성
        - URI : /organizations
        - HTTP 메서드 : POST
    - 소속 가입
        - URI : /organization/join
        - HTTP 메서드 : POST
    - 소속 탈퇴
        - URI : /organization/{id}/exit
        - HTTP 메서드 : DELETE
    - 소속 메모 조회
        - URI : /organizations/{id}/memos
        - HTTP 메서드 : GET
3. 메모
    - 메모 생성
        - URI : /memos
        - HTTP 메서드 : POST
    - 모든 메모 조회
        - URI : /memos
        - HTTP 메서드 : GET
    - 특정 메모 조회
        - URI : /memos/{id}
        - HTTP 메서드 : GET
    - 특정 메모 수정
        - URI : /memos/{id}
        - HTTP 메서드 : PATCH
    - 특정 메모 삭제
        - URI : /memos/{id}
        - HTTP 메서드 : DELETE
    - 좋아요 생성
        - URI : /memos/{id}/likes
        - HTTP 메서드 : POST
    - 좋아요 취소
        - URI : /memos/{id}/likes
        - HTTP 메서드 : DELETE
    - 좋아요 목록 조회
        - URI : /memos/{id}/likes
        - HTTP 메서드 : GET
