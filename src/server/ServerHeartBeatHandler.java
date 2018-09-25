package server; /**
 * Author   NieYinjun
 * Date     2018/9/22 21:11
 * TAG
 */
import client.RequestInfo;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ServerHeartBeatHandler extends ChannelHandlerAdapter {

    /**
     * key:ip value:auth **
     * 拥有的客户端列表，从数据库中或者配置文件中读取
     */
    private static HashMap<String, String> AUTH_IP_MAP = new HashMap<String, String>();
    //模拟授权的key
    private static final String SUCCESS_KEY = "auth_success_key";

    //

    static {
        AUTH_IP_MAP.put("192.168.2.182", "1234");
        AUTH_IP_MAP.put("169.254.197.45","1234");
    }

    private boolean auth(ChannelHandlerContext ctx, Object msg){
        String [] ret = ((String) msg).split(",");
        String auth = AUTH_IP_MAP.get(ret[0]);
        if(auth != null && auth.equals(ret[1])){
            ctx.writeAndFlush(SUCCESS_KEY);
            return true;
        } else {
            ctx.writeAndFlush("auth failure !").addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      //  System.out.println(msg instanceof UserParam);
        if(msg instanceof String){
            System.out.println("心跳信息:"+msg);
            auth(ctx, msg);
        }
        else if (msg instanceof RequestInfo) {
            System.out.println("-------------------性能参数-------------------------");
            //接受客户端发来的他机器的性能参数
            RequestInfo info = (RequestInfo) msg;
            System.out.println("当前主机ip为: " + info.getIp());
            System.out.println("当前主机cpu情况: ");
            HashMap<String, Object> cpu = info.getCpuPercMap();
            System.out.println("总使用率: " + cpu.get("combined"));
            System.out.println("用户使用率: " + cpu.get("user"));
            System.out.println("系统使用率: " + cpu.get("sys"));
            System.out.println("等待率: " + cpu.get("wait"));
            System.out.println("空闲率: " + cpu.get("idle"));

            System.out.println("当前主机memory情况: ");
            HashMap<String, Object> memory = info.getMemoryMap();
            System.out.println("内存总量: " + memory.get("total"));
            System.out.println("当前内存使用量: " + memory.get("used"));
            System.out.println("当前内存剩余量: " + memory.get("free"));
            System.out.println("--------------------------------------------");
            //通知客户端消息已收到
            ctx.writeAndFlush("info received!");
        }else if(msg instanceof UserParam){
            System.out.println("-------------进行消息处理----------------");
            ctx.fireChannelRead(msg);
        }else {
            System.out.println("-------------位置消息类型----------------");
            ctx.writeAndFlush("connect failure!").addListener(ChannelFutureListener.CLOSE);
        }
    }


}