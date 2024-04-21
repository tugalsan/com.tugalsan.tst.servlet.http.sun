package com.tugalsan.tst.servlet.http;

import com.tugalsan.api.file.properties.server.TS_FilePropertiesUtils;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.file.server.TS_PathUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;
import java.util.Properties;

public class Settings {

    final private static TS_Log d = TS_Log.of(false, Settings.class);

    public static Path pathDefault() {
        return TS_PathUtils.getPathCurrent_nio(Settings.class.getName() + ".properties");
    }

    private Settings(Path propsFile) {
        var propsExists = TS_FileUtils.isExistFile(propsFile);
        var props = propsExists ? TS_FilePropertiesUtils.read(propsFile) : new Properties();

        var fileHandler_filterUrlsWithHiddenCharsStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_fileHandler_filterUrlsWithHiddenChars", "true");
        d.ci("construtor", "fileHandler_filterUrlsWithHiddenChars", fileHandler_filterUrlsWithHiddenCharsStr);
        fileHandler_filterUrlsWithHiddenChars = TGS_UnSafe.call((() -> Boolean.valueOf(fileHandler_filterUrlsWithHiddenCharsStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for fileHandler_filterUrlsWithHiddenCharsStr: Cannot convert String to Boolean: " + fileHandler_filterUrlsWithHiddenCharsStr)));

        var customHandler_removeHiddenCharsStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_customHandler_removeHiddenChars", "true");
        d.ci("construtor", "onHandlerCustom_removeHiddenCharsStr", customHandler_removeHiddenCharsStr);
        customHandler_removeHiddenChars = TGS_UnSafe.call((() -> Boolean.valueOf(customHandler_removeHiddenCharsStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for customHandler_removeHiddenChars: Cannot convert String to Boolean: " + customHandler_removeHiddenCharsStr)));

        ip = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_ip", "localhost");
        d.ci("construtor", "ip", ip);

        var redirectToSSLStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_sslRedirect", "true");
        d.ci("construtor", "redirectToSSLStr", redirectToSSLStr);
        redirectToSSL = TGS_UnSafe.call((() -> Boolean.valueOf(redirectToSSLStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for sslRedirectStr: Cannot convert String to Boolean: " + redirectToSSLStr)));

        var sslPortStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_sslPort", "8081");
        d.ci("construtor", "sslPortStr", sslPortStr);
        sslPort = TGS_UnSafe.call((() -> Integer.valueOf(sslPortStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for sslPortStr: Cannot convert String to Integer: " + sslPortStr)));

        var sslPathStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_sslPath", "D:/xampp_data/SSL/tomcat.p12");
        d.ci("construtor", "sslPathStr", sslPathStr);
        sslPath = TGS_UnSafe.call((() -> Path.of(sslPathStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for sslPathStr: Cannot convert String to Path: " + sslPathStr)));

        sslPass = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_sslPass", "MyPass");
        d.ci("construtor", "sslPass", sslPass);

        fileHandlerServletName = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_fileHandlerServletName", "/file/");
        d.ci("construtor", "fileHandlerServletName", fileHandlerServletName);

        var fileHandlerRootStr = TS_FilePropertiesUtils.getValue(props, "com.tugalsan.gvm.cloud.Main_fileHandlerRoot", "D:/file");
        d.ci("construtor", "fileHandlerRoot", fileHandlerRootStr);
        fileHandlerRoot = TGS_UnSafe.call((() -> Path.of(fileHandlerRootStr)), e -> TGS_UnSafe.thrw(new RuntimeException("ERROR for fileHandlerRootStr: Cannot convert String to Path: " + fileHandlerRootStr)));

        if (!propsExists) {
            TS_FilePropertiesUtils.write(props, propsFile);
        }
    }
    final public boolean redirectToSSL, customHandler_removeHiddenChars, fileHandler_filterUrlsWithHiddenChars;
    final public int sslPort;
    final public Path sslPath, fileHandlerRoot;
    final public String ip, sslPass, fileHandlerServletName;

    public static Settings of(Path propsFile) {
        return new Settings(propsFile);
    }

}
