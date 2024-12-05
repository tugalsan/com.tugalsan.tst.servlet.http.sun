package com.tugalsan.tst.servlet.http.sun;

import com.tugalsan.api.crypto.client.*;
import com.tugalsan.api.file.client.*;
import com.tugalsan.api.function.client.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.servlet.http.sun.server.*;
import com.tugalsan.api.string.client.*;
import com.tugalsan.api.tuple.client.*;

public class Main {

    final private static TS_Log d = TS_Log.of(false, Main.class);

    //HOW TO EXECUTE
    //WHEN RUNNING IN NETBEANS, ALL DEPENDENCIES SHOULD HAVE TARGET FOLDER!
    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.serialcom
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.serialcom-1.0-SNAPSHOT-jar-with-dependencies.jar    
    
    //ERROR: {TS_SHttpServer}, {of}, {ERROR CAUSE: 'java.lang.NullPointerException: Cannot invoke "com.sun.net.httpserver.HttpServer.createContext(String, com.sun.net.httpserver.HttpHandler)" because "httpServer" is null'
    
    public static void main(String[] args) {
        var settingsLoc = Settings.pathDefault();
        d.cr("main", "settingsLoc", settingsLoc);
        var settings = Settings.of(settingsLoc);
        TGS_Func_OutBool_In1<TS_SHttpHandlerRequest> allow = request -> {
            d.ci("allow", "hello");
            if (!request.isLocalClient()) {
                request.sendError404("allow", "ERROR: Will work only localhost 😠");
                return false;
            }
            return true;
        };
        var byteHandler = TS_SHttpHandlerByte.of("/byte", allow, request -> {
            d.ci("byteHandler", "hello");
            return TGS_Tuple2.of(
                    TGS_FileTypes.jpeg,
                    TGS_CryptUtils.decrypt64_toBytes(TS_SHttpUtils.testJpgBase64())
            );
        }, settings.customHandler_removeHiddenChars);
        var stringHandler = TS_SHttpHandlerString.of("/", allow, request -> {
            d.ci("stringHandler", "hello");
            var urlJpg = request.url.cloneIt();
            urlJpg.path.fileOrServletName = "byte";
            urlJpg.path.paths.clear();
            return TGS_Tuple2.of(TGS_FileTypes.htm_utf8, TGS_StringUtils.cmn().concat(
                    "<html><head><style>",
                    "html, body {",
                    "   height: 100%;",
                    "}",
                    "body {",
                    "   background-image: url(",
                    urlJpg.toString(),
                    ");",
                    "   background-repeat: no-repeat;",
                    "   background-size: contain;",
                    "}",
                    "</style></head><body>",
                    "Hello html",
                    "<body></html>"
            ));
        }, settings.customHandler_removeHiddenChars);
        TS_SHttpServer.of(
                TS_SHttpConfigNetwork.of(settings.ip, settings.sslPort),
                TS_SHttpConfigSSL.of(settings.sslPath, settings.sslPass, settings.redirectToSSL),
                TS_SHttpConfigHandlerFile.of(settings.fileHandlerServletName, allow, settings.fileHandlerRoot, settings.fileHandler_filterUrlsWithHiddenChars),
                byteHandler, stringHandler
        );
    }
}
