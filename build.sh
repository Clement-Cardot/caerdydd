# 1. Build Back-end
cd back
mvn clean package

# 2. Build Front-end
cd ../front/
ng build

# 3. Build Docker Image
cd ../
docker build -t hello .

# 4. Run Docker Image
docker run -p 8080:8080 hello