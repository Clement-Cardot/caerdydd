echo "Run Docker Image (Database)"
docker rm -f taf-db
docker run --name taf-db -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ProjetGL mariadb:10.5.18
sleep 15
echo "Run SQL Creation Table Script"
docker exec -i taf-db mysql -uroot -proot ProjetGL < ./doc/tablesBdd.sql

echo "Run SQL Populate Table Script"
docker exec -i taf-db mysql -uroot -proot ProjetGL < ./doc/populateTables.sql