## Запуск для разработки
1. docker-compose up postgres redis
2. Запустить приложение через IntelliJ
3. Или docker-compose up --build чтобы поднять всё сразу. НО!! Нужно при смене кода писать 
./gradlew build
docker-compose up --build