# Spring Boot & React Template for User Authentication and Authorization

## 프로젝트 개요

이 프로젝트는 Spring Boot와 React를 사용하여 사용자 인증 및 권한 관리를 포함한 웹 애플리케이션을 빠르게 생성하기 위한 템플릿입니다. 이 템플릿은 SNS(Kakao) 인증 기능을 포함하여 사용자가 간편하게 로그인하고 회원가입할 수 있도록 설계되었습니다.

## 주요 기능

- 사용자 회원가입 및 로그인
- SNS(Kakao) 인증을 통한 로그인 및 회원가입
- JWT를 이용한 인증 및 권한 관리
- 기본적인 CORS 설정

## 기술 스택

| Stack                      | Description                                                                                                         |
|----------------------------|---------------------------------------------------------------------------------------------------------------------|
| **Backend**                | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white) |
| **Frontend**               | ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white) ![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)                                         |
| **Database**               | ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)                                                 |
| **Authentication**         | ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white) ![OAuth2](https://img.shields.io/badge/OAuth2-4285F4?style=for-the-badge&logo=oauth&logoColor=white)                    |
| **Build Tool**             | ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)                                               |
| **Development Environment**| ![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=for-the-badge&logo=eclipse&logoColor=white)                                                 |

## 설치 및 실행 방법

### 백엔드

1. **프로젝트 클론**
    ```bash
    git clone https://github.com/sonhoil/reactBootAuth.git
    cd reactBootAuth
    ```

2. **데이터베이스 설정**
    MariaDB에 데이터베이스를 생성하고, `src/main/resources/application.properties` 파일에서 데이터베이스 연결 설정을 업데이트합니다.
    ```properties
    spring.datasource.url=jdbc:mariadb://localhost:3306/yourdatabase
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **데이터베이스 테이블 생성**
    아래 SQL을 사용하여 MariaDB에 테이블을 생성합니다.
    ```sql
    -- user table 생성
    CREATE TABLE `user` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `password` varchar(255) DEFAULT NULL,
      `role` varchar(255) DEFAULT NULL,
      `username` varchar(255) DEFAULT NULL,
      `provider` varchar(20) DEFAULT NULL,
      `provider_id` varchar(100) DEFAULT NULL,
      `nickname` varchar(255) DEFAULT NULL,
      `email` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

    -- todo table 생성
    CREATE TABLE `todo` (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `completed` bit(1) DEFAULT NULL,
      `created_at` datetime(6) DEFAULT NULL,
      `description` varchar(255) DEFAULT NULL,
      `due_date` datetime(6) DEFAULT NULL,
      `title` varchar(255) DEFAULT NULL,
      `updated_at` datetime(6) DEFAULT NULL,
      `user_id` bigint(20) DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `FK2ft3dfk1d3uw77pas3xqwymm7` (`user_id`),
      CONSTRAINT `FK2ft3dfk1d3uw77pas3xqwymm7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
    ```

4. **Kakao OAuth 설정**
    Kakao Developers에서 애플리케이션을 등록하고, 발급받은 클라이언트 ID를 `application.properties` 파일에 추가합니다.
    ```properties
    spring.security.oauth2.client.registration.kakao.client-id=yourclientid
    spring.security.oauth2.client.registration.kakao.client-secret=yourclientsecret
    spring.security.oauth2.client.registration.kakao.redirect-uri=http://localhost:8080/login/oauth2/code/kakao
    ```

5. **애플리케이션 실행**
    ```bash
    ./mvnw spring-boot:run
    ```

### 프론트엔드

1. **프로젝트 클론**
    ```bash
    git clone https://github.com/sonhoil/reactBootAuth.git
    cd reactBootAuth/frontend
    ```

2. **패키지 설치**
    ```bash
    npm install
    ```

3. **애플리케이션 실행**
    ```bash
    npm start
    ```

## SNS(Kakao) 인증 구현

### 1. Kakao OAuth 설정

Kakao Developers에서 애플리케이션을 등록하고 발급받은 클라이언트 ID와 시크릿 키를 `application.properties` 파일에 설정합니다.

```properties
spring.security.oauth2.client.registration.kakao.client-id=yourclientid
spring.security.oauth2.client.registration.kakao.client-secret=yourclientsecret
spring.security.oauth2.client.registration.kakao.redirect-uri=http://localhost:8080/login/oauth2/code/kakao
spring.security.oauth2.client.provider.kakao.authorization-uri=https://kauth.kakao.com/oauth/authorize
spring.security.oauth2.client.provider.kakao.token-uri=https://kauth.kakao.com/oauth/token
spring.security.oauth2.client.provider.kakao.user-info-uri=https://kapi.kakao.com/v2/user/me
spring.security.oauth2.client.provider.kakao.user-name-attribute=id
