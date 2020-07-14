/**
 * FileName:Send
 * Author：HuangLin
 * Date: 2020/7/14 10:08
 * Description
 * 订阅模式：一个生产者，多个消费者，
 * 每个消费者都有自己的队列
 * 生产者没有将消息直接发送到队列，而是发送到了交换机exchange
 * 每个队列都要绑定到交换机
 * 生产者发送的消息，经过交换机，到达队列，实现一个消息被多个消费者获得的目的
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;

public class Send {

    public static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明一个交换机（分发模式，只要和交换机绑定的队列，都能收到消息）
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        // 发送消息
        String msg = "Hello exchange ,Routing Mode !";
        /**
         *  第一个参数为  exchange:交换机名称
         *  第二个参数为  routingKey:路由键，
         *  第三个参数为  props:消息属性集，包含14个属性成员，如持久化，优先级，过期时间等。
         *  第四个参数为  body:消息体，需要发送的消息。
         **/
        channel.basicPublish(EXCHANGE_NAME,"info",null,msg.getBytes());
        System.out.println("------[send] msg :"+ msg);

        //释放资源
        channel.close();
        connection.close();
    }

}
