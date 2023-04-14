# This Script is used to build the project and run it in a docker container.

# 1. Build Front-end
echo "Step 1 --> Build Front-end"
cd front

# Replace ip adress in api services
sed -i "s,http://localhost:8080/api/auth,http://localhost:8080/taf/api/auth,g" src/app/core/services/api-auth.service.ts
sed -i "s,http://localhost:8080/api/teams,http://localhost:8080/taf/api/teams,g" src/app/core/services/api-team.service.ts
sed -i "s,http://localhost:8080/api/users,http://localhost:8080/taf/api/users,g" src/app/core/services/api-user.service.ts
sed -i "s,http://localhost:8080/api/teamMembers,http://localhost:8080/taf/api/teamMembers,g" src/app/core/services/api-team-member.service.ts

# Compile
npm install
ng build --base-href "."

# export to back folder
cp -r dist/front/* ../back/src/main/resources/static/

# 2. Build Back-end
echo "Step 2 --> Build Back-end"
cd ../back

# Compile back end
mvn clean package

# put taf.war in main folder
cp target/taf.war ../