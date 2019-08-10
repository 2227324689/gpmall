# GPMall 电商网站

很多学员苦恼于没接触过分布式项目，希望有一些完整的项目来落地。所以我抽空给大家开发了一个微服务架构的电商网站，有兴趣的同学，可以私聊我一起参与项目的开发。

为了更好的体现在互联网公司项目的完整性，我对模块做了非常细的拆分，并没有使用maven多模块来管理项目，所以大家在部署的时候会一些麻烦

> 这个项目开发完成之后，我会部署到公网上，供大家来体验，并且基于访问量的提升，我们可以不断的优化这个架构。

**注意： 参与的人太多，本来是打算分组，但是分组的配合比较麻烦，比如各个组的代码的分支提交、合并、不好协调。所以暂时没考虑分组。所有参与的人，都是从前台到后台的统一开发。大家自己在开发的时候可以自己拉组**

# 项目用到的技术

项目采用前后端分离开发，前端需要独立部署。前端架构是基于 xmall 提供框架进行的重构。目前核心的技术栈采用的是SpringBoot2.1.5.RELEASE+Dubbo2.7.2,  后续我会再基于spring cloud alibaba进行重构

## 前端使用的技术

* nodejs
* axios
* es6
* vue
* sass
* Element UI
* webpack
* vue router
* mockjs

## 后端使用的技术

后端的主要架构是基于springboot+dubbo+mybatis.

* SpringBoot2.1.6
* Mybatis
* Dubbo2.7.2
* Zookeeper
* Mysql
* Redis
* Elasticsearch
* Kafka
* druid
* Docker
* mybatis generator
* Sentinel

# 项目模块说明

* db_script  本项目的数据库脚本
* gpmall-cashier 收银台，负责支付相关的交互逻辑【暂时未开发，可以不用关心】
* gpmall-commons 公共的组件
* gpmall-front  咕泡商城的前端项目
* gpmall-parent 父控文件，用来统一管理所有jar包
* gpmall-shopping  商品/购物车/首页渲染等交互
* gpmall-user  提供用户相关的交互，如登录、注册、个人中心等
* market-service 促销活动的Dubbo服务【暂时未联调完成，可以不启动】
* pay-service  提供支付处理能力【暂时未联调完成可以不用启动】
* shopping-service  dubbo服务，提供购物车、推荐商品、商品等服务
* user-service Dubbo服务，提供用户相关服务
* order-service Dubbo服务，提供订单服务
* 未完待续....

# 部署说明

模块比较多，部署这块会比较复杂。由于没有远程私服的支持，所以都需要本地先构建才能运行

1. 提前把环境准备好，zookeeper、mysql、redis、kafka等
2. gpmall-parent 是一个父控文件，需要先install到本地仓库
3. gpmall-commons 公共组件，第二步结束之后执行install安装
4. 把所有dubbo服务的api全部install到本地仓库
5. 分别启动gpmall-user/gpmall-shopping.  如果在同一个机器运行，需要指定不同的端口

# 前端项目启动流程

前端项目依赖node，所以需要先安装node环境

1. 去node官网下载nodejs安装文件进行安装，如果在`cmd`中输入 npm -version 能够显示版本，说明安装成功
2. 进入gpmall-front , 执行npm install
3. 第二步成功之后，执行npm  run dev 启动前端项目

# 项目开发进度

## 前台项目整体的规划有

* 首页渲染，轮播、自定义展示板块
* 商品查询、商品展示、商品详情
* 个人中心、用户注册、个人信息修改、收获地址维护
* 购物车、订单查询、下单、支付
* 促销活动

![1565235690613](1565235690613.png)

## 后台规划

后台这块我会搭建一个框架，有兴趣的同学可以参与开发，主要是提供对于前端相关模块的配置

# 如何贡献

非常欢迎您对Gpmall的开发作出贡献！ 你可以选择以下方式向Gpmall贡献：

- [Github - 发布issue进行问题反馈和建议](https://github.com/2227324689/gpmall/issues)
- 通过Pull Request提交修复
- 完善文档
- 提交产品需求









