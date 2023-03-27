# Start docker container with mariadb at port 3307
docker run --name mariadbtest -e MYSQL_ROOT_PASSWORD=password -p 3307:3306 mariadb:10.5.18

# TODO Deploy SQL script