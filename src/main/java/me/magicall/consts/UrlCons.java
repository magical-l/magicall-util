package me.magicall.consts;

/**
 * @author Liang Wenjian
 */
public interface UrlCons {
    char IP_SEPARATOR_CHARACTER = '.';
    String IP_SEPARATOR = String.valueOf(IP_SEPARATOR_CHARACTER);

    char URL_SEPARATOR_CHARACTER = '/';
    String URL_SEPARATOR = String.valueOf(URL_SEPARATOR_CHARACTER);

    char URI_PARAMETER_SEPARATOR_CHAR = '?';
    String URI_PARAMETER_SEPARATOR = String.valueOf(URI_PARAMETER_SEPARATOR_CHAR);

    char PARAMETER_SEPARATOR_CHAR = '&';
    String PARAMETER_SEPARATOR = String.valueOf(PARAMETER_SEPARATOR_CHAR);

    String LOCALHOST = "localhost";
    String LOCALHOST_IP = "127.0.0.1";

    int HTTP_PORT = 80;

    String PROTOCOL_SEPARATOR = "://";

    String HTTP = "http";
    String HTTP_ = protocolSeparator(HTTP);

    String HTTPS = "https";
    String HTTPS_ = protocolSeparator(HTTPS);

    String FTP = "ftp";
    String FTP_ = protocolSeparator(FTP);

    static String protocolSeparator(final String protocolName) {
        return protocolName + PROTOCOL_SEPARATOR;
    }
}
