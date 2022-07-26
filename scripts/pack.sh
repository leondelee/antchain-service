mvn clean
mvn package
scp -P 13022 ./target/antchain-svr-1.0-SNAPSHOT.jar liangweili@120.26.193.231:/home/liangweili/antchain-svr/bin/
#sftp -P 13022 liangweili@120.26.193.231
ssh liangweili@120.26.193.231 -p 13022 "cd ./antchain-svr/bin/ && docker-compose down && docker-compose up -d"
