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

# 4. Run Docker Image
echo "Step 4 --> Run Docker Image"
docker rm -f taf
docker run --name taf -it -p 8080:8080 taf

# Pour se connecter à la machine virtuelle :
# docker exec -it taf sh

# Pour se connecter au manager :
# username = root / password = root