1. 스프링프로젝트 빌드
   
   $ ./gradlew build -x test

2. docker-compose 빌드
   
   $ docker-compose build

3. docker-compose 컨테이너 띄우기

   $ docker-compose up

   mysql database와 스프링 서버의 총 두 개의 컨테이너가 띄워져야 정상입니다.

(swagger api 문서는 
[서버IP]:8080/api-docs로 접속 가능합니다 
[서버IP]:8080/swagger-ui/index.html로 리다이렉트됩니다)

