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
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "Hello Simple Queue !";

        //exchange routingKey props  body
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("------send msg :"+ msg);
        channel.close();
        conn.close();

    }
}
