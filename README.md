# mmall
商城项目
1、启动Nginx：
  关闭防火墙
  mkdir ps -ef|grep nginx
  ./nginx--启动
  sbin/nginx -s reload --重启
  grep --color=auto nginx
  结果是：
    root       4328   1786  0 22:02 ?        00:00:00 nginx: master process ./nginx
    nobody     4329   4328  0 22:02 ?        00:00:00 nginx: worker process
    root       4372   2893  0 22:02 pts/2    00:00:00 grep --color=auto nginx
  浏览器测试：
    http://192.168.25.128/
2、启动zookeeper
   dpkg-reconfigure dash：关闭默认shell命令，选择No.
   cd /home/lzx/zookeeper-3.4.6
   sh bin/zkServer.sh start：启动服务
   sh bin/zkServer.sh status:查看状态
3、
  1、Nginx通过端口配置不同的虚拟主机、通过配置域名或者端口来实现（在同一台Nginx主机上放置多个应用）
     server {//配置多个server节点
        listen       81;//配置监听端口即可实现
        server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        location / {
            root   html81;//这是Nginx安装的相对的路径、也可使用绝对路径、访问次端口即可访问这个文件中的资源
            index  index.html index.htm;
        }
    }
    server {//配置多个server节点：http://192.168.25.128:80
        listen       80;
        server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        location / {
            root   html81;//这是Nginx安装的相对的路径、也可使用绝对路径
            index  index.html index.htm;
        }
    }
   2、通过域名区别不同的主机（通过同一台Nginx、域名一致、端口不一致、配置Nginx的conf.xml文件来实现）
      配置host文件：C:\Windows\System32\drivers\etc\host
      192.168.25.128  www.e3mall.com
      通过www.e3mall.com即可访问、或者www.e3mall.com:81
      server {//配置多个server节点
        listen       80;//配置监听端口即可实现
        server_name  www.e3mall.com;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        location / {
            root   html81;//这是Nginx安装的相对的路径、也可使用绝对路径、访问次端口即可访问这个文件中的资源
            index  index.html index.htm;
        }
    }
    
    server {//配置多个server节点
        listen       80;//配置监听端口即可实现
        server_name  www.baidu.com;//配置域名即可实现访问同一个端口访问不用的应用

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        location / {
            root   html81;//这是Nginx安装的相对的路径、也可使用绝对路径、访问次端口即可访问这个文件中的资源
            index  index.html index.htm;
        }
    }
   4、Nginx反向代理
    正向代理：多台客户端通过一个代理访问相同的服务器
    反向代理：一个客户端通过代理访问多台服务器（只有一个公网IP，但是有多台服务器（多个域名）都绑定到这个公网IP上，所以此时需要配置Nginx来实现对于
    不同的域名来实现访问不同的服务器、其实就是跟上面在一个Nginx上通过配置域名来实现访问不同的资源文件一样的道理，都是通过Nginx来实现中转）
    
    upstream mmall{
      http://192.168.25.128:8080;//跳转到自己的Tomcat
    }
    server {//配置多个server节点
        listen       80;//配置监听端口即可实现
        server_name  www.e3mall.com;//配置域名即可实现访问同一个端口访问不用的应用

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        
        location / {
            proxy_pass http://mmall;//这个与上面配置多个虚拟主机不一样、mmall对应的值在上面upstream声明
            index  index.html index.htm;
        }
    }
    5、负载均衡：当上面访问的Tomcat的服务器实现集群的时候就需要配置负载均衡
      其实就是在上面反向代理的基础上的server配置多个IP即可，将多个Tomcat对应的访问地址配置到同一个Server节点
      
    upstream mmall{
      http://192.168.25.128:8080;//由于启动的两个Tomcat都是在同一个主机上，所以只是通过端口来识别
      http://192.168.25.128:8081;//基本策略是轮询
      //http://192.168.25.128:8081 weight=2;可以配置权重
    }
    server {//配置多个server节点
        listen       80;//配置监听端口即可实现
        server_name  www.e3mall.com;//配置域名即可实现访问同一个端口访问不用的应用

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

        
        location / {
            proxy_pass http://mmall;//这个与上面配置多个虚拟主机不一样、mmall对应的值在上面upstream声明
            index  index.html index.htm;
        }
    }
    6、实现Nginx的高可用
  
