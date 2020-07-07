/**
 * FileName:Receive2
 * Author：HuangLin
 * Date: 2020/7/7 9:43
 * Description 消费者、
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package Simple;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

public class Receive2 {
    public static  final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 定义一个消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("--------------------new API ----------------------");
                System.out.println("------[Receive] msg :"+msg);
            }
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
}
}
