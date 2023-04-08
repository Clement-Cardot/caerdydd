# This Script is used to build the project and run it in a docker container.

# 1. Build Back-end
echo "Step 1 --> Build Back-end"
cd back
mvn clean package

# 2. Build Front-end
echo "Step 2 --> Build Front-end"
cd ../front/
npm install
ng build

# 3. Build Docker Image
echo "Step 3 --> Build Docker Image"
cd ../
docker build -t taf .

# 4. Run Docker Image (Database)
echo "Step 4 --> Run Docker Image (Database)"
docker rm -f taf-db
docker run --name taf-db -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ProjetGL mariadb:10.5.18
sleep 15
echo "Run SQL Script"
docker exec -i taf-db mysql -uroot -proot ProjetGL < ./doc/tablesBdd.sql

# 4. Run Docker Image (WEB)
echo "Step 4 --> Run Docker Image (WEB)"
docker rm -f taf
docker run --name taf -it -p 8080:8080 taf

# Pour se connecter à la machine virtuelle :
# docker exec -it taf sh

# Pour se connecter au manager :
# username = root / password = root