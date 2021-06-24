# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* InputStream , BufferedReader  
자바에서 Stream의 기본 값은 byte이며 미리 사이즈를 정해서 `byte[] = getBytes()`로 읽어올 수 도있다.   
  UTF-8에서 한글은 3byte라 한byte씩 읽어오면 깨질 수 있는데 <b>Scanner(InputStreamReader)</b>가 '문자'단위로 읽어들일 수 있게 해준다.   
  이를 '문자열' 단위로 읽으려면 배열로 선언해야하는데 Buffer를 통해 쌓아둔 뒤 한번에 문자열 처럼 처리할 수 있다.
  - BufferedReader의 `readLine()`메소드   
    : 한줄(공백 포함)을 읽어오기 때문에 char 배열 생성 필요 없이 String으로 바로 받을 수 있다.  
* `Byte[] body = Files.readAllBytes(Path path)  `
  Path객체 형식으로 넣어주어야 하기 때문에 경로 String값이 아닌  new File(경로).toPath()로 지정해준다.  
* `log.debug()` 에서 쓰레드 번호, 실행중인 클래스, 값을 다 볼 수 있다.

---
* 알아볼 것

    - keep-alive
    - HTTP header 
    - 요청으로 받은 header값을 어떻게 response에서 사용?하는지 

### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 