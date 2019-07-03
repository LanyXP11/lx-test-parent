# lx-test-parent
springcloud2.* 整合TX-LCN   完成分布式事务的完美融合

## 项目说明
1.项目模仿了一个典型的电商业务场景,用户在买东西下单的操作，下完订单后端会调用
订单服务进行订单数据库的记录，同时也会调用库存服务进行商品的减库操作，这一操作可能是有
订单服务和库存(商品)服务两个服务完成的操作，整个操作要么一起成功要么一起失败，试想
如果用户在下完单 在调用库存服务减库存的操作失败了的话 会出现商品多买的情况
或者在减库存 成功 订单却失败了会造成少买的现象  这些情况都是不允许出现的 由于两个服务运行在不同的服务中 
这个时候 本地事务就不能完全解决这种状况了 本项目就是基于这种情况做的demo测试

## LCN简介
1.TX-LCN由两大模块组成, TxClient、TxManager，TxClient作为模块的依赖框架，提供TX-LCN的标准支持，TxManager作为分布式事务的控制放。事务发起方或者参与反都由TxClient端来控制。

### 核心步骤
创建事务组
是指在事务发起方开始执行业务代码之前先调用TxManager创建事务组对象，然后拿到事务标示GroupId的过程。
加入事务组
添加事务组是指参与方在执行完业务方法以后，将该模块的事务信息通知给TxManager的操作。
通知事务组
是指在发起方执行完业务代码以后，将发起方执行结果状态通知给TxManager,TxManager将根据事务最终状态和事务组的信息来通知相应的参与模块提交或回滚事务，并返回结果给事务发起方。

2.详情链接:http://www.txlcn.org/zh-cn/docs/principle/control.html

## 项目启动说明:
0.创建两个数据库取名为lx_order  lx_stock

1.运行Eureka服务
```java
@SpringBootApplication
   @EnableEurekaServer
   public class EurekaApplication {
       public static void main(String[] args) {
           SpringApplication.run(EurekaApplication.class, args);
           System.err.println("启动成功");
       }
```
2.启动TxManager
```java
@SpringBootApplication
@EnableDiscoveryClient
public class TxManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TxManagerApplication.class, args);
        System.err.println("启动成功");
    }
}
需要修改的地方
redis.host=你的地址
```
3.启动订单服务
```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
@MapperScan(basePackages = "com.lx.service.mapper")
@ComponentScan(basePackages = {"com.lx.common", "com.lx.service"})
public class OrderApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.err.println("启动成功");
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OrderApplication.class);
    }
#注意:数据库更改
```
4.启动库存服务
```java
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2Doc
@EnableFeignClients
@MapperScan(basePackages = "com.lx.service.mapper")
@ComponentScan(basePackages = {"com.lx.common", "com.lx.service"})
public class StockApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
        System.err.println("启动成功");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StockApplication.class);
    }
#注意:数据库更改
```
### 如何使用LCN
在项目中添加依赖 本项目是将lcn的源代码直接整合到项目中所以只需要在接口的实现中添加相关依赖就ok
lx-test-service模块中添加
```java
<!--TX-CLN分布式事务协调服务支持SpringCloud2.* 未发布版-->
<dependency>
    <groupId>com.lx</groupId>
    <artifactId>lx-test-lcn-springcloud</artifactId>
    <version>1.0-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>com.lx</groupId>
    <artifactId>lx-test-lcn-plugins-db</artifactId>
    <version>1.0-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>*</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
接口实现类中直接实现ITxTransaction接口使用 @TxTransaction(isStart = true)
isStart由服务的发起方设置 项目中订单服务是事务发起方库存服务是服务参与方所以在订单服务做设置
同时将TxManagerHttpRequestServiceImpl和TxManagerTxUrlServiceImpl放入到接口实现类中
同时在application.yml中指定
事务管理器的地址
tm: 
   manager: 
        url: http://127.0.0.1:8899/tx/manager/
        
### 地址访问
0.OrderServiceImpl和StockServiceImpl的中的代码:
```java
    @GetMapping("/api/order/orderAndStock")
    @Transactional//开启本地事务
    @TxTransaction(isStart = true)//使用分布式全局事务 事务的发起方是订单服务
    public LxResponse OrderAndStock(@RequestParam("num") String num) {
        if (StringUtils.isBlank(num)) {
            return new LxResponse().setError("NUM不允许为空");
        }
        OrderEntity entity = new OrderEntity();
        entity.setCommodityId(30l);
        entity.setName("恭喜您下单了");
        entity.setOrderCreatetime(new Date());
        entity.setOrderMoney(300d);
        entity.setOrderState(0);
        //往订单表中添加一条记录
        int i = orderMapper.addOrder(entity);
        if (i < 0) {
            throw new MessageException(9010, "下单失败");
        }
        //调用库存服务对商品进行减库存操作
        LxResponse lxResponse = stockFeign.inventoryReduction(30l);
        if (checkResultResponse(lxResponse)) {
            throw new MessageException(lxResponse.getError().getCode(), lxResponse.getError().getMessage());
        }
        //制造异常
        Long result = 1 / Long.valueOf(num);
        lxResponse = new LxResponse();

        lxResponse.setData("下单成功");

        log.info("调用订单服务最终返回值是:[{}]", JSON.toJSONString(lxResponse));
        return lxResponse;
        
            @TxTransaction//使用分布式全局事务
            @Transactional//本地事务
            @RequestMapping("/api/stock/inventoryReduction")
            public LxResponse inventoryReduction(@RequestParam("commodityId") Long commodityId) {
                if (commodityId == null) {
                    return new LxResponse().setError("商品ID不允许为空");
                }
                StockEntity stockEntity = stockMapper.selectStock(commodityId);
                if (stockEntity == null) {
                    return new LxResponse().setError("商品ID不存在");
                }
                if (stockEntity.getStock() <= 0) {
                    return new LxResponse().setError("当前商品已经买完啦!");
                }
                int updateStockResult = stockMapper.updateStock(commodityId);
                if (updateStockResult <= 0) {
                    return new LxResponse().setError("修改库存失败!");
                }
                return new LxResponse().setData("修改库存成功!");
            }
        
```
1.访问路径:127.0.0.1:8010/api/order/orderAndStock?num=1



