微服务的分布式事务使用的方式 AT模式的使用方式

 1.解压后使用，进入bin目录执行此命令作用
 每个微服务的file.conf会把事务管理链接到这个应用上
 后台运行 seata-server.sh  对应端口和host 输出文件到seata.log
 nohup sh seata-server.sh -p 8091 -h 127.0.0.1 -m file &> seata.log &
 
 2.每个微服务的数据库由 io.seata.rm.datasource.DataSourceProxy进行代理datasource
 
 3.修改每个微服务的pom文件，引入依赖
     <dependency>
             <groupId>com.alibaba.cloud</groupId>
             <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
             <version>2.1.0.RELEASE</version>
     </dependency>
     
4.对应微服务ServiceApplication进行import之前的数据代理类
5.把clientDemoConf目录的两个文件，拷贝到微服务resources的目录下file.conf 、registry.conf
6.修改bootstrap.yml的文件增加对应的seata事务分组，这个分组名称要跟file.conf的一致
  # seata分组
    alibaba:
      seata:
        tx-service-group: minbox-seata
7.需要使用分布式的方法加上seata注解    @GlobalTransactional进行管理全局的事务
8.seat的模式需要在数据库增加undo_log的表


seata还有MT模式后续会引入相关的的代码


 
 