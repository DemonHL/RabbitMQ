/**
 * FileName:Receive01
 * Author：HuangLin
 * Date: 2020/7/14 18:18
 * Description 接受者
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package topics;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

public class Receive01 {
    public static final  String QUEUE_NAME = "test_topic_queue";
    public static final  String EXCHANGE_NAME ="test_exchange_topic";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机上
        //# 代表匹配一个或者多个;     * 匹配一个
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"goods.#");
        channel.basicQos(1);

        channel.basicConsume(QUEUE_NAME,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println(msg);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });


    }


}
