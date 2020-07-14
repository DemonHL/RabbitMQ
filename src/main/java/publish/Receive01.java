/**
 * FileName:Receive01
 * Author：HuangLin
 * Date: 2020/7/14 10:28
 * Description
 * 订阅模式
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package publish;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

public class Receive01 {


    public static final  String QUEUE_NAME = "test_exchange_queue01";
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建一个通道
        final Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         * 队列绑定到交换机
         * 第一个参数为队列名，
         * 第二个参数为交换机名
         **/
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
         //保证队列一次只分发一个消息
        channel.basicQos(1);
        boolean autoAck=false;
        channel.basicConsume(QUEUE_NAME,autoAck,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String msg=new String(body,"utf-8");
              System.out.println("[1] Receive Msg : "+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done ");
                    /**
                     * 手动确认
                     * 第1个参数：通过发送Tag标识来确认消费的是消息队列中的哪个消息
                     * 第2个参数：是否开启多个消息同时确认，这里我们设置了每次只能消费一个消息，因此不需要开启
                     */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });
    }
}
