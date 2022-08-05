package pro.goforit.websokettest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Double>J
 * @email: zjj20001031@foxmail.com
 * @editTime: 2022/8/5 9:26
 * @desc:
 **/
@Component
@ServerEndpoint("/websocket/{userId}")
@Slf4j
public class WebSocketServiceImpl {
    private Session session;

    private static ConcurrentHashMap<String,WebSocketServiceImpl> sessionMap = new ConcurrentHashMap<>(128);

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId){
        log.info("用户:[{}]已进入房间",userId);
        sessionMap.put(userId,this);
    }


    @OnClose
    public void onClose(){

    }


    public void sendMsg2All(String msg){
        sessionMap.values().forEach(
                item->{
                    try {
                        item.session.getAsyncRemote().sendText(msg);
                    } catch (Exception e) {
                        log.error(e.getMessage(),e);
                    }
                }
        );
    }

}
