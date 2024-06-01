<div align="center">
  <p>
    <img src="https://harvey-image.oss-cn-hangzhou.aliyuncs.com/telegram.png" alt="logo" width="200" height="auto"/>
  </p>
  <h3>Harvey's Awesome Online Video Platform</h3>
</div>

# Preparing MySQL environment

Start Mysql container.

```shell
docker volume create cilicili-mysql-conf
docker volume create cilicili-mysql-data
docker volume create cilicili-mysql-logs

sudo mkdir -p /opt/mysql

ln -s /var/lib/docker/volumes/cilicili-mysql-conf/_data /opt/mysql/conf
ln -s /var/lib/docker/volumes/cilicili-mysql-data/_data /opt/mysql/data
ln -s /var/lib/docker/volumes/cilicili-mysql-logs/_data /opt/mysql/logs

docker container run \
    --name cilicili-mysql \
    --privileged \
    -p 3306:3306 \
    -v cilicili-mysql-conf:/etc/mysql/conf.d \
    -v cilicili-mysql-data:/var/lib/mysql \
    -v cilicili-mysql-logs:/var/log/mysql \
    -e MYSQL_ROOT_PASSWORD=111 \
    -d mysql:8.1.0
```

Import cilicili/cilicili-resource/cilicili-db.sql into the database.

```shell
mysql -h127.0.0.1 -uroot -p111 cilicili < cilicili-db.sql
```

# Preparing Redis environment

Download the Redis configuration file.

```shell
curl -LJO http://download.redis.io/redis-stable/redis.conf
mv ./redis.conf /opt/redis/conf
```

Modify the Redis configuration file (file: /opt/redis/conf/redis.conf).

```
# run as a daemon in background
daemonize no

# disable protected mode
protected-mode no

# disable bind to allow remote connection
# bind 127.0.0.1 -::1

# SET password
requirepass 111

# persistent storage
appendonly yes
```

Start Redis container.

```shell
docker volume create cilicili-redis-conf
docker volume create cilicili-redis-data
docker volume create cilicili-redis-logs

sudo mkdir -p /opt/redis

ln -s /var/lib/docker/volumes/cilicili-redis-conf/_data /opt/redis/conf
ln -s /var/lib/docker/volumes/cilicili-redis-data/_data /opt/redis/data
ln -s /var/lib/docker/volumes/cilicili-redis-logs/_data /opt/redis/logs

docker container run \
    --name cilicili-redis \
    --privileged \
    -p 6379:6379 \
    -p 16379:16379 \
    -v cilicili-redis-conf:/etc/redis \
    -v cilicili-redis-data:/data \
    -v cilicili-redis-logs:/var/log/redis.log \
    -d redis:7.2 redis-server /etc/redis/redis.conf
```
