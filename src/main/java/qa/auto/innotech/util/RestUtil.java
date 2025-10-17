package qa.auto.innotech.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class RestUtil {

    private final String path;
    public RestUtil(String path) {
        this.path = path;
    }

    private RequestSpecification getDefaultSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build()
                .log()
                .all();
    }

    public Response doPost(Object body) {
        return given(getDefaultSpec())
                .body(body)
                .post(path);
    }

    public Response doGet() {
        return given(getDefaultSpec())
                .get(path);
    }

    public Response doGet(int id) {
        return given(getDefaultSpec())
                .pathParam("id", id)
                .get(path + "/{id}");
    }

    public Response doDelete(int id) {
        return given(getDefaultSpec())
                .pathParam("id", id)
                .delete(path + "/{id}");
    }
}

