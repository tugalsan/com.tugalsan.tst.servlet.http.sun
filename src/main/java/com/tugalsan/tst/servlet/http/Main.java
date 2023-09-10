package com.tugalsan.tst.servlet.http;

import com.sun.net.httpserver.HttpExchange;
import com.tugalsan.api.callable.client.TGS_CallableType1;
import com.tugalsan.api.file.client.TGS_FileTypes;
import com.tugalsan.api.servlet.http.server.TS_SHttpConfigNetwork;
import com.tugalsan.api.servlet.http.server.TS_SHttpConfigSSL;
import com.tugalsan.api.servlet.http.server.TS_SHttpHandlerText;
import com.tugalsan.api.servlet.http.server.TS_SHttpServer;
import com.tugalsan.api.tuple.client.TGS_Tuple2;
import java.nio.file.Path;

public class Main {

    //HOW TO EXECUTE
    public static void main(String[] args) {
        TGS_CallableType1<TGS_Tuple2<TGS_FileTypes, String>, HttpExchange> customHandler = he -> {
            return TGS_Tuple2.of(TGS_FileTypes.txt_utf8, "ali gel ğüĞÜöçÖÇşiŞİıI");
        };
        var network = TS_SHttpConfigNetwork.of("localhost", 8081);
        var ssl = TS_SHttpConfigSSL.of(Path.of("D:\\xampp_data\\SSL\\tomcat.p12"), "MyPass");
        var folder = Path.of("D:\\file");
        TS_SHttpServer.startHttpsServlet(network, ssl, folder, TS_SHttpHandlerText.of("/", customHandler));
    }
}
