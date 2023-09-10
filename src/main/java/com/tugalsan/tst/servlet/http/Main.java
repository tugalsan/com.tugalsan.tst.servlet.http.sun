package com.tugalsan.tst.servlet.http;

import com.sun.net.httpserver.SimpleFileServer;
import com.tugalsan.api.file.client.TGS_FileTypes;
import com.tugalsan.api.servlet.http.server.TS_SHttpHandlerText;
import com.tugalsan.api.servlet.http.server.TS_SHttpServer;
import com.tugalsan.api.tuple.client.TGS_Tuple2;
import java.nio.file.Path;

public class Main {

    //HOW TO EXECUTE
    //C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.servlet.http>target\bin\webapp.bat
    public static void main(String[] args) {
        //TS_SHttpsServerSimple.main(args);
        TS_SHttpServer.startHttpsServlet("localhost", 8081,
                Path.of("D:\\xampp_data\\SSL\\tomcat.p12"), "MyPass",
                Path.of("D:\\file"),
                TS_SHttpHandlerText.of("/", he -> {
                    return TGS_Tuple2.of(TGS_FileTypes.txt_utf8, "ali gel ğüĞÜöçÖÇşiŞİıI");
                })
        );
    }
}
