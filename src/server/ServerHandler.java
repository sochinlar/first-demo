package server; /**
 * Author   NieYinjun
 * Date     2018/9/22 21:09
 * TAG
 */
import client.UserData;
import server.UserParam;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----------------channelActive-----------------------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("---------------------channelRead---------------------");
        //接受客户端对象
        UserParam user = (UserParam)msg;
        System.out.println("客户端发来的消息 : " + user.getId() + ", " + user.getName() + ", " + user.getRequestMessage());
        //给客户端返回对象
        UserData response = new UserData();
        response.setId(user.getId());
        response.setName("response" + user.getId());
        response.setResponseMessage("响应内容" + user.getId());
        ctx.writeAndFlush(response);
        //处理完毕，关闭服务端
        //ctx.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----------------channelReadComplete-----------------------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("-----------------exceptionCaught-----------------------");
        ctx.close();
    }
}