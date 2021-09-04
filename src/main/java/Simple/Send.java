/**
 * FileName:生产者
 * Author：HuangLin
 * Date: 2020/7/7 9:22
 * Description 发送者
 * History
 * <author>   <time>    <version>  <desc>
 * 作者姓名   修改时间      版本号      描述
 */
package Simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

import java.io.IOException;
import java.net.InetAddress;

public class Send {
    public static  final String QUEUE_NAME = "test_simple_queue";
    public static void main(String[] args) throws IOException {
        Connection conn = ConnectionUtils.getConnection();
        InetAddress ia = conn.getAddress();
        String ip = ia.getHostAddress();
        System.out.println("ip地址为"+ip);
        //创建一个通道
        Channel channel = conn.createChannel();
        /**
         声明一个队列
         durable:持久化
         mnesia:数据库【有专门的表去保存我们的队列声明】
         exclusive：排外
         i:当前定义的队列是connection中的chanenl是共享的，其他的connection是访问不到的；
         ii:当connection.close时，queue就被删除。
         autoDelete：自动删除：当最后一个consumer断开之后，autodelete被触发；
         **/
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "Hello Simple Queue !";

        /*
         * 向server发布一条消息
         * 参数1：exchange名字，若为空则使用默认的exchange
         * 参数2：routing key
         * 参数3：其他的属性
         * 参数4：消息体
         * RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
         * 任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
         */
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("------send msg :"+ msg);
        channel.close();
        conn.close();

    }
}
