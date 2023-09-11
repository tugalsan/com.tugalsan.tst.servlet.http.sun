package com.tugalsan.tst.servlet.http;

import com.tugalsan.api.file.client.TGS_FileTypes;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.servlet.http.server.TS_SHttpConfigNetwork;
import com.tugalsan.api.servlet.http.server.TS_SHttpConfigSSL;
import com.tugalsan.api.servlet.http.server.TS_SHttpHandlerText;
import com.tugalsan.api.servlet.http.server.TS_SHttpServer;
import com.tugalsan.api.servlet.http.server.TS_SHttpUtils;
import com.tugalsan.api.tuple.client.TGS_Tuple2;
import com.tugalsan.api.url.client.TGS_Url;
import com.tugalsan.api.url.client.parser.TGS_UrlParser;
import com.tugalsan.api.validator.client.TGS_ValidatorType1;
import java.nio.file.Path;

public class Main {

    final private static TS_Log d = TS_Log.of(true, Main.class);

    //HOW TO EXECUTE
    //WHEN RUNNING IN NETBEANS, ALL DEPENDENCIES SHOULD HAVE TARGET FOLDER!
    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.serialcom
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.serialcom-1.0-SNAPSHOT-jar-with-dependencies.jar    
    public static void main(String[] args) {
        TGS_ValidatorType1<TGS_UrlParser> allow = parser -> true;
        var customTextHandler = TS_SHttpHandlerText.of("/", allow, httpExchange -> {
            var uri = TS_SHttpUtils.getURI(httpExchange).orElse(null);
            if (uri == null) {
                d.ce("main", "ERROR url base null");
                TS_SHttpUtils.sendError404(httpExchange);
                return null;
            }
            var parser = TGS_UrlParser.of(TGS_Url.of(uri.toString()));
            return TGS_Tuple2.of(TGS_FileTypes.htm_utf8, uri.toString() + "<br>" + parser.toString());
        });
        var network = TS_SHttpConfigNetwork.of("localhost", 8081);
        var ssl = TS_SHttpConfigSSL.of(Path.of("D:\\xampp_data\\SSL\\tomcat.p12"), "MyPass", true);
        var folder = Path.of("D:\\file");
        TS_SHttpServer.startHttpsServlet(network, ssl, allow, folder, customTextHandler);
    }
}
