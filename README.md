#### Инструкция по запуску
git clone https://github.com/priorgnewb/news-with-docker.git  
cd news-with-docker/news  
./mvnw clean package -DskipTests  
cd ..  
docker-compose up  

#### Тестировать  
http://localhost:8080/swagger-ui/index.html
