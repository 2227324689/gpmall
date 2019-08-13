## 简介
> 由于该项目是一个集百人共同参与的伟大项目，在开发过程中由于没有提供统一的开发环境，导致每一个大神都需要频繁的修改本地环境的ip地址，
包含 mysql zk kafka 等一些列中间件,为了减少大家的频繁改动，这边使用host地址来减少对项目代码的改动

## Host 配置说明 
这边推荐使用 [SwitchHosts 进行管理](https://oldj.github.io/SwitchHosts/) ，具体使用方法请自行百度

如果是centos 环境，请编辑以下文件
```
vi /etc/hosts
```

添加如下内容 
```
127.0.0.1 mysql.gpmall.com
127.0.0.1 zk.gpmall.com
127.0.0.1 kafka.gpmall.com
127.0.0.1 redis.gpmall.com
127.0.0.1 mysql.gpmall.com
```

其中 ,127.0.0.1 修改为对于应用的Ip地址即可

如果是Windows环境，请编辑以下文件
```
C:\Windows\System32\drivers\etc\hosts
```
将上述示例内容添加到hosts文件中即可
