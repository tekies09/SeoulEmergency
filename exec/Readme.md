## 빌드 및 배포
1. 
Hadoop
Spring boot  
Vue2  
JAVA : openjdk version "1.8.0_192"  
InteliJ IDEA 2021.3.1  
VSCode 1.64.2  
Mongo:5.0.6   
jenkins:lts-jdk8


2. <br>nginx 파일  
```
upstream backend {
        server localhost:8080;
        server localhost:8081;
}

server {
        listen 80 default_server;
        listen [::]:80 default_server;
        location / {
        return 301 https://$host$request_uri;
                }
        }

server {
        #listen 80 default_server;
        #listen [::]:80 default_server;
        listen 443 ssl default_server;
        listen [::]:443 ssl default_server;

        ssl_certificate /home/ubuntu/cert/live/j6a403.p.ssafy.io/fullchain.pem;
        ssl_certificate_key /home/ubuntu/cert/live/j6a403.p.ssafy.io/privkey.pem;
        ssl_session_timeout 1440m;
        ssl_session_tickets off;
        ssl_protocols TLSv1.2 TLSv1.3;
        ssl_prefer_server_ciphers off;
        ssl_ciphers "ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-CHACHA20-POLY1305:ECDHE-RSA-CHACHA20-POLY1305:DHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES256-GCM-SHA384";

        root /home/ubuntu/SeoulEmergencyFrontend/dist;

        # Add index.php to the list if you are using PHP
        index index.html index.htm index.nginx-debian.html;

        server_name _;

        location / {
                # First attempt to serve request as file, then
                # as directory, then fall back to displaying a 404.
                try_files $uri $uri/ /index.html;
        }
        location /api {
                proxy_pass http://backend;
                proxy_http_version 1.1;
                proxy_set_header Connection "";
                include /etc/nginx/proxy_params;
        }


```
3. 특이사항  
 SSL 발급받기
 ```
sudo apt-get update -y & sudo apt-get install letsencrypt -y
sudo letsencrypt certonly --standalone -d [도메인 네임]
 ```

4. DB 접속 정보  
mysql
사용 db : shelter
계정 : threestar / ssafya403threestar

<br>

## 외부 서비스 정보
naver api 이용 
