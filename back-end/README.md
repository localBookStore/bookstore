# Back-End : Java Spring Boot 2.4

## How to Run Application

### Step 1. Install Java JDK 11
각자의 OS 환경에 맞는 JDK 11파일을 [다운](https://www.oracle.com/java/technologies/javase-downloads.html)받아 설치합니다.<br/>
설치를 완료했다면 windows는 cmd를, macOS는 terminal에서 아래 명령어를 수행하여 설치 여부를 확인합니다.

```bash
$ java --version
java version "11.0.9" 2020-10-20 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.9+7-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.9+7-LTS, mixed mode)
```
위와 같이 Java의 버전이 나온다면, 정상적으로 설치되었습니다.<br/>
(만약 실행이 안된다면, 별도로 Java 환경변수를 설정해야합니다.)

참고로, 설치된 파일은 다음 경로에 default로 저장된다.
```markdown
windows : C:\Program Files\Java
macOS   : /Library/Java/JavaVirtualMachines
```  

### Step 2. Install Spring Boot

##### windows : 
IntelliJ IDEA, STS4 등 IDE 도구 설치 <br/>
or <br/>
[Spring](https://repo.spring.io/release/org/springframework/)에서 제공해주는 CLI 설치

##### macOS :

IntelliJ IDEA, STS4 등 IDE 도구 설치 <br/>
or <br/>
[Spring](https://repo.spring.io/release/org/springframework/)에서 제공해주는 CLI 설치 

### Step 3. Install Gradle
##### windows : 
IntelliJ IDEA 또는 STS4에서 기본으로 제공<br/>
or<br/>
[Gradle](https://gradle.org/releases/)에 접속해서<br/>
실행 파일만 있는 'binary-only'나 각종 문서을 포함하고 있는 'complete'를 다운받아
압축 해제 후 환경 변수에 추가합니다. 

##### macOS :
IntelliJ IDEA 또는 STS4에서 기본으로 제공<br/>
or<br/>
[Gradle](https://gradle.org/releases/)에 접속해서<br/>
실행 파일만 있는 'binary-only'나 각종 문서을 포함하고 있는 'complete'를 다운받아
압축 해제 후 환경 변수에 추가합니다.

Gradle 빌드 툴 설치 작업 후 아래 명령어를 통해 설치 여부를 검증합니다.
```bash
$ gradle -v

------------------------------------------------------------
Gradle 6.X.X
------------------------------------------------------------

Build time:   XXXX-XX-XX HH:MM:SS UTC
Revision:     ...

Kotlin:       X.X.XX
Groovy:       X.X.XX
Ant:          ...
JVM:          11.0.9 (Oracle Corporation 11.0.9+7-LTS)
OS:           ...
```

### Step 4. Build and Run
위에서 소개한 IDE에 해당 프로젝트를 git clone한 후 Build/Run하거나, 아래에 소개할 terminal에 직접 명령어를 작성하여 이용하면 됩니다.

##### Build : 
해당 프로젝트 내 gradlew 파일이 위치한 경로('back-end/')로 이동하여 다음 명령어를 수행합니다.
```bash
$ ./gradlew build
```

##### Run :
위 명령어를 실행하면 'back-end/build/libs/' 경로에 jar 파일이 생성됩니다.<br/>
java 명령어로 해당 jar 파일을 실행하면 http://localhost:8080로 접근할 수 있습니다.
```bash
$ java -jar build/libs/[파일명].jar 또는
$ java -jar build/libs/*.jar
```

### Dependencies

```bash
'spring-boot-starter-web'           #
'spring-boot-starter-test'          # Apply Domain Routing
'spring-boot-devtools'              # Asynchronous Request
'spring-boot-starter-data-jpa'      # Apply Carousel & modal
'spring-boot-starter-validation'    # For a apply Multi Carousel
'mysql-connector-java'              # react-slcik CSS
'org.projectlombok:lombok'          # for react UI font
```

### File Structure (In Progress...)

```markdown
back-end
├── README.md
├── gradlew
├── gradlew.bat
├── .gitignore
├── .gradle
├── gradle
├── build
│   ├── classes
│   ├── generated
│   ├── libs
│   │   └── bookstore-0.0.1-SNAPSHOT.jar
│   ├── reports
│   ├── resources
│   ├── test-result
│   ├── bootJarMainClassName
│   └── bootRunMainClassName
└── src
    └── main
    	└── java
    	    └── com.webservice.bookstore	
                ├── BookstoreApplication.java
                ├── domain.entity
                ├── service
                └── web
                    ├── controller
                    └── dto
```

