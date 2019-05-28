package dictionaries;

import configuration.TestConfigure;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class Dictionaries extends TestConfigure {

    @Test(description = "Справочник внешних систем")
    public void getExternalSystem() {
        given()
                .spec(spec)
                .when()
                .get(external_system)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/externalSystem"))
                .log().ifError();
    }

    @Test(description = "Адресный справочник")
    public void getAddress() {
        given()
                .spec(spec)
                .when()
                .get(address)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/address"))
                .log()
                .all();
    }

    @Test(description = "Справочник видов отключений")
    public void getOutageKind() {
        given()
                .spec(spec)
                .when()
                .get(outage_kind)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/outageKind"))
                .log()
                .all();
    }

    @Test(description = "Справочник дефектов")
    public void getDefects() {
        given()
                .spec(spec)
                .when()
                .get(defect)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/defect"))
                .log()
                .all();
    }

    @Test(description = "Справочник источников заявок")
    public void getSource() {
        given()
                .spec(spec)
                .when()
                .get(source)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/source"))
                .log()
                .all();
    }

    @Test(description = "Справочник категорий дефектов")
    public void getDefectCategory() {
        given()
                .spec(spec)
                .when()
                .get(defect_category)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/defect_category"))
                .log()
                .all();
    }

    @Test(description = "Справочник категорий консультаций")
    public void getConsultationCategory() {
        given()
                .spec(spec)
                .when()
                .get(defect_category)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/defect_category"))
                .log()
                .all();
    }

    @Test(description = "Справочник классов строений БТИ")
    public void getBticlass() {
        given()
                .spec(spec)
                .when()
                .get(bticlass)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/bticlass"))
                .log()
                .all();
    }

    @Test(description = "Справочник объектов отключений")
    public void getOutageObject() {
        given()
                .spec(spec)
                .when()
                .get(outage_object)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/outage_object"))
                .log()
                .all();
    }

    @Test(description = "Справочник организаций")
    public void getLegal() {
        given()
                .spec(spec)
                .when()
                .get(legal)
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/legal"))
                .log()
                .all();
    }

}
