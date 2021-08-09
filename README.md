# lsz-mall

电商项目DEMO

> 前端参考：  
> https://github.com/macrozheng/mall-admin-web  
> https://github.com/newbee-ltd/newbee-mall-vue-app  


# 项目部署

1. 构建docker镜像
- 修改配置，构建各自的jar包
- docker build -t mall-manage .
- docker build -f portal-Dockerfile -t mall-portal .

> -f portal-Dockerfile 该参数指定Dockerfile，视具体情况而定，此处构建文件为 `portal-Dockerfile` 

2. 创建Docker网络

docker network create -d bridge mall-network

> 容器连接网络：docker network connect mall-network nginx

3. 运行容器

docker run -d --name mall-manage --network mall-network --privileged=true --init -p 7070:7070 -v ~/mall/log/:/tmp/log/ -v ~/mall/pic/:/tmp/pic/ mall-manage
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
4. docker cp nginx:/usr/share/nginx/html ~/nginx/static_html
5. docker stop nginx && docker rm nginx # 删除，准备重新创建容器
6. docker run -p 80:80 --name nginx \
--network mall-network \
--privileged=true \
-v ~/nginx/static_html/:/usr/share/nginx/ \
-v ~/mall/pic/:/pic/ \
-v ~/nginx/logs:/var/log/nginx  \
-v ~/nginx/conf:/etc/nginx  \
-d nginx

### nginx配置文件

- vi ~/nginx/conf/conf.d/default.conf

```conf
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;

    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;        
    }

    location /pic {
        alias  /pic;
        autoindex on;
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

- vi ~/nginx/conf/nginx.conf

http节点下增加gzip配置

```conf
http {
	# 其他配置省略
    gzip on;
    gzip_static on;

}
```



> 这个配置是为了使前端访问更快，这个对速度影响很大，3MB可以被压缩到几百k


## 配置redis
- mkdir -p ~/mall/redis/
- touch ~/mall/redis/redis.conf

> 如果不手动创建redis.conf文件，docker默认的映射时创建的是目录

- docker run --name mall-redis \
--network mall-network \
--privileged=true \
-v ~/mall/redis/redis.conf:/etc/redis/redis.conf \
-p 6379:6379 \
-d redis /etc/redis/redis.conf

配置文件：~/mall/redis/redis.conf
```conf
port 6379
maxmemory-policy volatile-lru
# 内存占用: 100MB
maxmemory 104857600
```

## 前端gzip打包

1. package.json加入插件`compression-webpack-plugin`

版本不能过高，否则build失败，我这里是1.1.12版本

2. module.exports中productionGzip配置改为true

> 参考：
> https://www.cnblogs.com/donghuang/p/10045688.html
> https://blog.csdn.net/dx18520548758/article/details/84106945