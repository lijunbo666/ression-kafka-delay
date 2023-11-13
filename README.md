#ression-kafka-delay
redssion和kafka延迟消费工具自定义的相关工具实现。

1,kafka延迟消费工具 
包路径：com/example/delay/controller/TestController.java

   1)post接口请求:http://localhost:8089/test/send 并设置延迟消费参数

   2)TestController中delayOrder方法为监听消费方法

   相关实现文章：https://blog.csdn.net/godwei_ding/article/details/118658888

2.注意事项
  1)为了防止redssion中延迟队列和阻塞队列执行过程中丢失消息，请更新redssion版本到 redisson->3.15.0的最新版本，解决了此问题，通过分析，最终原因如下
  
  2)blpop等待超时或者take都有风险，如果不升级版本建议用poll()，然后起个定时任务去poll

  3)官方的解决方法分析说明：

   那个加一秒那个类 做了个临界条件的规避防止出现了延时时间和阻塞超时时间刚好撞到了一起，等待超时断开连接的那一刻有可能服务端的延迟消息到达了造成数据丢失，这个解决也是很巧秒

