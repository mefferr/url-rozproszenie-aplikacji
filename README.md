# url-rozproszenie-aplikacji


## Budowanie aplikacji:
```shell
./gradlew build
```

## Budowanie obrazu Dockerowego aplikacji:
```shell
./gradlew dockerBuildImage
```

## Uruchomienie Docker Compose z Cassandrą, Kafką (Redpanda) i konsolą do Kafki:
```shell
docker compose up -d
```

## Uruchomienie Docker Compose z Cassandrą, Kafką (Redpanda), konsolą do Kafki (Redpanda) oraz aplikacją:
```shell
docker compose --profile app up -d
```

## ZADANIE 3:

Uruchomienie konsoli CQLSH w kontenerze Cassandry:
```shell
docker exec -it <id_kontenera_cassandry> cqlsh
```

## ZADANIE 4:

### API do dodawania URL/wpisów:
```shell
curl --location 'localhost:8090/url' \
--header 'Content-Type: application/json' \
--data '{
    "url": "http://niezakazane.pl/test"
}'
```

### API do usuwania wpisów po dacie utworzenia:
```shell
curl --location --request DELETE 'localhost:8090/url-cleanup/by-created-timestamp?timestamp=2024-09-08T22:00:00Z'
```

### API do usuwania wpisów po dacie ostatniego użycia:
```shell
curl --location --request DELETE 'localhost:8090/url-cleanup/by-last-accessed-timestamp?timestamp=2024-09-09T00:00:00Z'
```

Aby zobaczyć wpisy należy połączyć się z Cassandrą na `localhost:9042` i wejść do keyspace `url`.

## ZADANIE 5:

### API do dodawania URL zawierającego słowo zakazane:
```shell
curl --location 'localhost:8090/url' \
--header 'Content-Type: application/json' \
--data '{
    "url": "http://testgoogleabc.pl/123facebookzakazane"
}'
```

Podgląd na Kafkę (Redpanda): [http://localhost:8999/topics](http://localhost:8999/topics)

---

**Do wykonywania requestów HTTP (komend `curl`) można użyć narzędzi takich jak Postman.
Do podglądania wpisów w Cassandrze najłatwiej użyć jakiegoś klienta do baz danych, np. IntelliJ ma wbudowanego.**
