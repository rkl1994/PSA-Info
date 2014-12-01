package psa.navi;

import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class HelloServer extends NanoHTTPD {
    Handler mHandler;
	public HelloServer() {
        super(8080);
        Log.e("HelloServer", "new");
    }

    @Override 
    public Response serve(IHTTPSession session) {
        Log.e("serve", "started");
    	Method method = session.getMethod();
        String uri = session.getUri();
        Log.e("server",method + " '" + uri + "' ");
        String msg = "yes";
        Log.e("speed",session.getParms().get("speed"));
        Log.e("message", session.getParms().get("message"));
        Log.e("boolean", session.getParms().get("isLast"));
        Log.e("x", session.getParms().get("x"));
        Log.e("y", session.getParms().get("y"));
        Log.e("distanceToNext", session.getParms().get("distanceToNext"));
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = 0;//匹配handler
        bundle.putString("speed", session.getParms().get("speed"));
        bundle.putString("message", session.getParms().get("message"));
        bundle.putString("boolean", session.getParms().get("boolean"));
        bundle.putString("x",session.getParms().get("x"));
        bundle.putString("y", session.getParms().get("y"));
        bundle.putString("distanceToNext", session.getParms().get("distanceToNext")); 
        message.obj = bundle;//传递的参数 我可以用bundle绑定
        if (mHandler != null) {
			mHandler.sendMessage(message);
		}
        return new NanoHTTPD.Response(msg); 
    }

    public void setHandler(Handler handler){
    	mHandler = handler;
    }
}
