/**
 * FileName:receive
 * Author：HuangLin
 * Date: 2020/7/7 9:42
 * Description
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package Simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import utils.ConnectionUtils;

import java.io.IOException;

public class receive {
    public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        //定义消息队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msgStr = new String(delivery.getBody());

            System.out.println("------[Receive] msg :"+msgStr);
        }
    }
}
