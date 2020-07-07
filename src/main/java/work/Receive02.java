/**
 * FileName:Receive01
 * Author：HuangLin
 * Date: 2020/7/7 10:18
 * Description 工作队列 消费者2获取消息
 * * 能者多劳
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package work;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

public class Receive02 {
    public static final  String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        Connection conn = ConnectionUtils.getConnection();
        final Channel channel = conn.createChannel();
        // 声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //同一时间只会发一条消息给消费者
        channel.basicQos(1);
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2] Receive Msg : " + msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回确认状态
                channel.basicAck(envelope.getDeliveryTag(),false);
                System.out.println("[2] done ");
            }
        };
        //改为手动确认
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
