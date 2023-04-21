# This Script is used to build the project and run it in a docker container.

# 1. Build Front-end
echo "Step 1 --> Build Front-end"

cd front

# Compile
npm install
ng build --configuration=production --base-href "."

# export to back folder
cp -r dist/front/* ../back/src/main/resources/static/

# 2. Build Back-end
echo "Step 2 --> Build Back-end"
cd ../back

# Compile back end
mvn clean package -P prod

# put taf.war in main folder
cp target/taf.war ../