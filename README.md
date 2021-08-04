# lsz-mall

模拟电商项目


# 项目部署

1. 构建docker镜像
- 修改配置，构建各自的jar包
- docker build -t mall-manage .
- docker build -f portal-Dockerfile -t mall-portal .

> -f portal-Dockerfile 该参数指定Dockerfile，视具体情况而定，此处构建文件为 `portal-Dockerfile` 

1. 创建Docker网络

docker network create -d bridge mall-network

> 容器连接网络：docker network connect mall-network nginx

1. 运行容器

docker run -d --name mall-manage --network mall-network --privileged=true --init -p 7070:7070 -v ~/log:/tmp/log mall-manage
docker run -d --name mall-portal --network mall-network --privileged=true --init -p 28018:28018 -v ~/log:/tmp/log mall-portal

## 配置MySQL
1. docker run -d --name=mall-mysql --network mall-network -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7.23

### 指定MySQL脚本

TODO

> 也可以配置flyway为enable

## 配置nginx

1. docker run -p 80:80 --name nginx -d nginx # 创建一个容器导出配置文件
2. docker cp nginx:/etc/nginx ~/nginx
3. mv ~/nginx/nginx ~/nginx/conf
4. docker cp nginx:/usr/share/nginx/html ~/nginx/
5. docker stop nginx && docker rm nginx # 删除，准备重新创建容器
6. docker run -p 80:80 --name nginx \
--network mall-network \
--privileged=true \
-v ~/nginx/static_html/:/usr/share/nginx/ \
-v ~/nginx/logs:/var/log/nginx  \
-v ~/nginx/conf:/etc/nginx  \
-d nginx

-v ~/nginx/html:/usr/share/nginx/html \
-v ~/nginx/mall:/usr/share/nginx/mall \

### 配置文件

- vi ~/nginx/conf/default.conf

```conf
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;

    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;        
    }

    location /mall/manage {
        alias  /usr/share/nginx/mall_manage;
        index  index.html index.htm;
        autoindex on;
    }

    location /mall/portal {
        alias  /usr/share/nginx/mall_portal;
        index  index.html index.htm;
        autoindex on;
    }

    location /api/mall/manage/ {
        proxy_pass http://mall-manage:7070/;
        proxy_set_header Host $host:$server_port;
    }

    location /api/mall/portal/ {
        proxy_pass http://mall-portal:28018/;
        proxy_set_header Host $host:$server_port;
    }

}
```


