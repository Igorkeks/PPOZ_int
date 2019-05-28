package configuration;

import io.qameta.allure.Attachment;

public class AllureConfigure {

    @Attachment(value = "Ответ в json формате", type = "application/json")
    public static String saveResponseBody(String body) {
        return body;
    }
}
