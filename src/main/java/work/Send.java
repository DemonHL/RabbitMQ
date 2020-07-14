/**
 * FileName:Send
 * Author：HuangLin
 * Date: 2020/7/7 9:58
 * Description 工作队列生产者：一个生产者对应多个消费者
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;

public class Send {
    public static final  String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtils.getConnection();
        Channel channel = conn.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i < 30; i++) {
            String msg="hello work queue"+i;
            /**
             *  第一个参数为  exchange:交换机名称
             *  第二个参数为  routingKey:路由键，
             *  第三个参数为  props:消息属性集，包含14个属性成员，如持久化，优先级，过期时间等。
             *  第四个参数为  body:消息体，需要发送的消息。
             **/
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        // 释放连接
        channel.close();
        conn.close();
    }

}
