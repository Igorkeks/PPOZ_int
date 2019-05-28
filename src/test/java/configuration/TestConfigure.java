package configuration;

import classes.ApplicationProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static classes.GlobalVariables.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.FailureConfig.failureConfig;

public class TestConfigure {

    public static RequestSpecification spec;
    static ApplicationProperties ap = ApplicationProperties.getInstance();

    public static final String external_system = "api/v1/dictionaries/external_system";
    public static final String address = "/api/v1/dictionaries/address";
    public static final String outage_kind = "/api/v1/dictionaries/outage_kind";

    public static final String defect = "/api/v1/dictionaries/deffect";
    public static final String source = "/api/v1/dictionaries/source";
    public static final String defect_category = "/api/v1/dictionaries/deffect_category";
    public static final String consultation_category = "/api/v1/dictionaries/consultation_category";
    public static final String bticlass = "/api/v1/dictionaries/bticlass";
    public static final String outage_object = "/api/v1/dictionaries/outage_object";
    public static final String legal_id = "/api/v1/dictionaries/legal/{s}";
    public static final String legal = "/api/v1/dictionaries/legal";
    public static final String grade = "/api/v1/dictionaries/grade";
    public static final String subdivision_id = "/api/v1/dictionaries/subdivision/{s}";
    public static final String subdivision = "/api/v1/dictionaries/subdivision";
    public static final String emergency = "/api/v1/dictionaries/emergency";
    public static final String refusal_executor_reason = "/api/v1/dictionaries/refusal_executor_reason";
    public static final String rejection_reason = "/api/v1/dictionaries/rejection_reason";
    public static final String outage_reason = "/api/v1/dictionaries/outage_reason";
    public static final String cancel_reason = "/api/v1/dictionaries/cancel_reason";
    public static final String person_check_uid = "/api/v1/dictionaries/person/check_uid";
    public static final String execution_time = "/api/v1/dictionaries/execution_time";
    public static final String status = "/api/v1/dictionaries/status";
    public static final String delivery_status = "/api/v1/dictionaries/delivery_status";
    public static final String pay_status = "/api/v1/dictionaries/pay_status";
    public static final String legal_person_id = "/api/v1/dictionaries/legal_person/{s}";
    public static final String legal_person = "/api/v1/dictionaries/legal_person";
    public static final String document_type = "/api/v1/dictionaries/document_type";
    public static final String entity_access_type = "/api/v1/dictionaries/entity_access_type";
    public static final String declarant_type = "/api/v1/dictionaries/declarant_type";
    public static final String consultation_type = "/api/v1/dictionaries/consultation_type";
    public static final String outage_type = "/api/v1/dictionaries/outage_type";
    public static final String payable_type = "/api/v1/dictionaries/payable_type";
    public static final String subdivision_type = "/api/v1/dictionaries/subdivision_type";
    public static final String person_id = "/api/v1/dictionaries/person/{s}";
    public static final String person = "/api/v1/dictionaries/person";


    @BeforeSuite
    public static void globalInit() {
        String sed = ap.getProperty("SidExpiryDate");
        if (sed ==null) {
            getNewSID();
            return;
        }
        if (sed.equals("")) {
            getNewSID();
            return;
        }
        if (sed.length() > 0) {
            LocalDateTime SidExpiryDate = LocalDateTime.parse(
                    sed, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
            if (SidExpiryDate.isBefore(LocalDateTime.now())) getNewSID();
        }
    }

    @BeforeClass
    public static void initSpec() {
        ResponseValidationFailureListener saveBodyOnFailure = (reqSpec, respSpec, resp)
                ->  AllureConfigure.saveResponseBody(resp.getBody().prettyPrint());

        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(ap.getProperty(HOST))
                .setConfig(RestAssured.config().failureConfig(failureConfig().with().failureListeners(saveBodyOnFailure)))
                .addParam("limit", "10")
                .addCookie(SID_ID, ap.getProperty(SID_ID))
//                .addFilter(new ResponseLoggingFilter())
//                .addFilter(new RequestLoggingFilter())
                .build();
    }

    private static void getNewSID() {
        RestAssured.baseURI = ap.getProperty(HOST);

        JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, ap.getProperty(USERNAME));
        requestBody.put(PASSWORD, ap.getProperty(PASSWORD));

        Cookie connectSid = given().body(requestBody.toString()).
                contentType(ContentType.JSON).
                when().
                post(AUTH).
                then().
                log().ifError().
                statusCode(200).
                extract().
                detailedCookie(SID_ID);

        ap.setProperty(SID_ID, connectSid.getValue())
                .setProperty("SidExpiryDate", connectSid.getExpiryDate().toString())
                .save();
    }

}