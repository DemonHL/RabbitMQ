/**
 * FileName:Send
 * Author：HuangLin
 * Date: 2020/7/14 18:04
 * Description
 * 主題模式：
 * 与路由模式异曲同工，主要依据通配符对routingKey进行识别匹配，
 * 让消费者接受某些特定的消息，而不接受其他消息
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;

public class Send {

    public static final String EXCHANGE_NAME="test_exchange_topic";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明一个交换机（主题模式）
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //发送消息并指明路由
        String msg="hello Topic.....";
        String routingKey="goods.add";
        //发送
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("send: "+msg);
        channel.close();
        connection.close();

    }

}
