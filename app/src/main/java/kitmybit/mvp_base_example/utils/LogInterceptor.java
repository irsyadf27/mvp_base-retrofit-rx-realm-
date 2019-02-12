package kitmybit.mvp_base_example.utils;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public final class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = "Server Log";
    boolean SHOW_LOGS = true;
    boolean SHOW_HEADER = false;
    LogInterceptor.OnErrror onErrror;

    public LogInterceptor() {
        SHOW_LOGS = true;
    }

    public LogInterceptor(LogInterceptor.OnErrror onErrror) {
        SHOW_LOGS = true;
        this.onErrror = onErrror;
    }

    public LogInterceptor(boolean showLogs) {
        SHOW_LOGS = showLogs;
        SHOW_HEADER = false;
    }
    public LogInterceptor(boolean showLogs, boolean showHeader){
        SHOW_LOGS = showLogs;
        SHOW_HEADER = showHeader;
    }

    public LogInterceptor showing(boolean state) {
        SHOW_LOGS = state;
        return this;
    }
    public LogInterceptor showingHeader(boolean state){
        SHOW_HEADER = state;
        return this;
    }

    public boolean isShowing(){
        return SHOW_LOGS;
    }

    public boolean isHeaderShowing(){
        return SHOW_HEADER;
    }

    private void log(String message){
        Log.e(TAG, message);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isShowing()) {
            return chain.proceed(request);
        }
        //@REQUEST Title
//        RequestBody requestBody = request.body();
//        String requestTitleMessage = "@" + request.method() + ' ' + request.url();
//        Log.e(" "," ");
//        log(requestTitleMessage);

        //@REQUEST Header
        if(SHOW_HEADER) {
            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    log(name + ": " + headers.value(i));
                }
            }
        }

        //@REQUEST Body
//        if (requestBody!=null) {
//            Buffer buffer = new Buffer();
//            requestBody.writeTo(buffer);
//            Charset charset = UTF8;
//            MediaType contentType = requestBody.contentType();
//            if (contentType != null) {
//                charset = contentType.charset(UTF8);
//            }
//            log("BODY: "+buffer.readString(charset));
//        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            //onErrror.onError(e.getMessage());
            log("@HTTP FAILED: " + String.valueOf(e.toString()));
            throw e;
        }
        //FINISH REQUEST
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //@RESPONSE Title
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        log("@RESPONSE   " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + ')');
        //@RESPONSE Header
        if(SHOW_HEADER) {
            Headers headers2 = response.headers();
            for (int i = 0, count = headers2.size(); i < count; i++) {
                log(headers2.name(i) + ": " + headers2.value(i));
            }
        }
        //@RESPONSE Body
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                log("Couldn't decode the response body; charset is likely malformed.");
                log("@END RESPONSE");
                return response;
            }
        }

        if (contentLength != 0) {
            log("BODY: " + buffer.clone().readString(charset));
        }

        log("@END RESPONSE");
        return response;
    }

    public interface OnErrror{
        void onError(String error);
    }
}
