# simple-todo

#### 구성
<pre>
spring boot  
JPA , Querydsl  
h2
html, jQuery  
</pre>

#### 간단한 TODO 서비스  
<pre>
- 기본적인 CRUD 완료
- 다른 TODO를 참조 할 수 있도록 적용
- 페이징, 검색, 정렬 완료
- 백업파일 다운로드 완료
</pre>


### **설치/빌드**

 1. aws openJDK11 설치 (수동설치 방법)
    <pre><code>wget https://corretto.aws/downloads/latest/amazon-corretto-11-x64-linux-jdk.tar.gz
    
    tar zxvf amazon-corretto-11-x64-linux-jdk.tar.gz
    
    mv amazon-corretto-11.0.6.10.1-linux-x64 /usr/local/
    
    파일 생성  
    vi /etc/profile.d/jdk11.sh
    
    환경변수 설정  
    export JAVA_HOME=/usr/local/amazon-corretto-11.0.6.10.1-linux-x64
    export PATH=$PATH:$JAVA_HOME/bin
    </code></pre>
    * 참고 : https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/generic-linux-install.html

 2. maven설치  
    <pre><code>sudo yum install maven
    </code></pre>
    * 참고 : http://maven.apache.org/install.html

 3. 깃설치   
    <pre><code>sudo yum install git
    </code></pre>
    
 4. 소스 받기  (소스를 저장할 위치로 이동)
    <pre><code>git clone https://github.com/jiha-liz/simple-todo.git
    </code></pre>

 5. 소스 빌드 
    <pre><code>cd simple-todo
    해당 위치에  pom.xml이 있는지 획인
    
    sudo mvn package  
    
    java -jar target/todo-0.0.1-SNAPSHOT.jar
    </code></pre>

 6. 서비스 확인
    <pre>
    http://localhost
    </pre>
