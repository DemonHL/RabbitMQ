/**
 * FileName:Receive01
 * Author：HuangLin
 * Date: 2020/7/7 10:18
 * Description 工作队列 消费者1获取消息
 * 公平分发（默认轮询分发）一人一个，平均分发
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package FairDispatch;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

public class Receive02 {
    public static final  String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtils.getConnection();
        Channel channel = conn.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2] Receive Msg : " + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done ");
                }
            }
        };
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
