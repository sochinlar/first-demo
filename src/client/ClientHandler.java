package client; /**
 * Author   NieYinjun
 * Date     2018/9/22 21:10
 * TAG
 */
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------------channelActive-------------------------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("--------------------channelRead------------------------");
        try {
            UserData user = (UserData)msg;
            System.out.println("服务器返回的消息  : " + user.getId() + ", " + user.getName() + ", " + user.getResponseMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-------------------channelReadComplete-------------------------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("-----------------exceptionCaught------------------------------");
        ctx.close();
    }
}