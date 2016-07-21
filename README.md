#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ServletContextListener를 구현하는 ContextLoaderListener 클래스의 객체를 생성하여 context 초기화 작업을 한다.
* @WebServlet 어노테이션이 적용된 DispatcherServlet 클래스의 객체를 생성하고, init 메소드를 통해 RequestMapping 클래스 객체를 생성한다.
* RequestMapping 클래스 객체에서는 사용자 요청에 해당하는 컨트롤러들을 생성해서 매핑한다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* DispatcherServlet 에서 최초 요청을 받는다.
* 요청 uri '/'에 매핑된 HomeController 클래스 객체의 execute 메소드를 호출한다.
* HomeController는 DB에서 전체 질문 목록을 가져온 뒤, index.jsp로 포워딩되도록 세팅된 ModelAndView 클래스 객체를 반환한다.
* DispatcherServlet은 반환받은 ModelAndView 객체에서 View 를 꺼낸 뒤 render() 메소드를 호출한다.
* JspView 클래스 객체에 세팅된대로 index.jsp 로 포워딩된다.
* index.jsp 페이지가 브라우저에 로드되면서, 페이지 내 css, js, woff, ico 등과 같은 정적 파일을 다시 서버에 요청한다.
* 해당 정적 파일들은 ResourceFilter에서 걸러져 바로 해당 리소스로 포워딩 처리가 된다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 
