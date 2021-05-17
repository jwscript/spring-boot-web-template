# 빌드 툴 (메이븐, 그래들)
요즘의 빌드 툴은 의존 관계를 관리해준다.<br>
spring-boot-starter-web 라이브러리를 당기면 거기에 사용되는 라이브러리도 당겨준다.

# 스프링 컨테이너의 요청 처리 
웹 브라우저로부터 스프링 부트로 어떤 요청이 들어오면 <br>
내장 톰캣 서버를 거쳐 들어온 요청을 스프링 컨테이너가 처리한다. <br>
이때, 스프링 컨테이너는 HTTP 메서드와 요청 url을 보고 아래와 같이 두가지 처리 방식으로 나뉜다.

1) 처리해줄 컨트롤러가 있는 경우 <br>
   (1) @ResponseBody 어노테이션이 붙어있지 않은 경우 <br>
   컨트롤러가 처리 후 뷰 리졸버(View Resolver)가 동작하여 컨트롤러의 메서드가 반환한 값에 대하여 처리를 해준다. <br>
   이후, /templates/[문자열] 에 해당하는 파일을 찾아준다. <br>
   <br>   
   (2) @ResponseBody 어노테이션이 붙어있는 경우 <br>
   컨트롤러가 처리 후 HttpMessageConverter가 동작하여 반환 값에 대한 처리를 해준다. <br>
    - 반환 값이 단순 문자이면 StringConverter가 처리한다. ex) StringHttpMessageConverter
    - 반환 값이 객체이면 JsonConverter가 처리한다. ex) MappingJackson2HttpMessageConverter
    - 이 밖에 반환값의 타입에 따른 여러 HttpMessageConverter가 기본적으로 등록되어 있다.
    

2) 처리해줄 컨트롤러가 없는 경우 <br>
   /static/ 경로에서 파일을 찾음.

# 데이터를 정적파일로 응답할지, API로 응답할지를 정하는 @ResponseBody <br>
HTML로 내리느냐? = @ResponseBody 사용 X <br>
API로 내리느냐? = @ResponseBody 사용 <br>

# 테스트를 만들 때
given : 뭔가를 주어졌을 때 <br>
when : 실행하면 (이걸 검증한다는 것 !!) <br>
then : 이렇게 나와야 함. <br>

# 스프링 빈을 만드는 두가지 방법
1. @Component 또는 @Component를 가지고 있는 (@Controller, @Service, @Repository) 등을 클래스 위에 붙이거나 <br>
2. java코드로 직접 추가하는 방법 <br>
   @Configuration 이 붙은 클래스에서 @Bean 을 만들거나
일반적으로 @Service, @Repository는 @Configuration이 붙은 클래스에서 @Bean으로 많이 만든다.
하지만, @Controller는 외부의 요청에 대해 처리하다보니 @Configuration 클래스 내부에서 @Bean으로 만들진 않음. <br>
※ 참고로 @Autowired는 스프링 빈 객체가 될 클래스에 있는 경우에만 동작함. !!!!
   
# ORM
Object + Relational + Mapping <br>
객체와 관계형 데이터베이스를 매핑하는 기술
--- 
# 단축키
- Alt + Insert : 빠른 파일 / 메서드 생성
- Ctrl + Shift + F10 : 현재 코드 실행
- Ctrl + Alt + v : 변수 추출
- Ctrl + Alt + m : 메서드 추출
- Ctrl + Shift + t : 테스트 케이스 클래스 생성 
- Ctrl + Shift + n : 파일 찾기 (파일명으로)
- Ctrl + Shift + f : 파일 찾기 (내용으로)
- Ctrl + F12 : 현재 클래스의 메서드/필드 열람