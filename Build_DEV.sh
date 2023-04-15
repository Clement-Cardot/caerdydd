# This Script is used to build the project and run it in a docker container.

# 1. Build Front-end
echo "Step 1 --> Build Front-end"

cd front
# Replace ip adress in api services
sed -i "s,localhost:8080,172.24.1.10:8080/taf,g" src/app/core/services/api-auth.service.ts
sed -i 's,localhost:8080,172.24.1.10:8080/taf,g' src/app/core/services/api-team.service.ts
sed -i 's,localhost:8080,172.24.1.10:8080/taf,g' src/app/core/services/api-user.service.ts
sed -i 's,localhost:8080,172.24.1.10:8080/taf,g' src/app/core/services/api-team-member.service.ts

# Compile
npm install
ng build --base-href "."

# export to back folder
cp -r dist/front/* ../back/src/main/resources/static/

# 2. Build Back-end
echo "Step 2 --> Build Back-end"
cd ../back

# Modify application.properties
sed -i 's,spring.datasource.username = root,spring.datasource.username = webuser,g' src/main/resources/application.properties
sed -i 's,spring.datasource.password = root,spring.datasource.password = mNifUKDq10MPD3pP,g' src/main/resources/application.properties

# Compile back end
mvn clean package

# put taf.war in main folder
cp target/taf.war ../