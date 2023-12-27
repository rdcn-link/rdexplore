# rdexplore

# package
```sh
cd server
mvn clean package

cd ui
npm install
npm run build
```
# test
```sh
nohup java -jar server/target/rdexplore-server*.jar &
cd ui
npm run serve
```
Open url: http://localhost:8080
# deploy
```sh
nohup java -jar rdexplore-server*.jar &

npm install
npm run build
```
