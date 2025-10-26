package qa.auto.innotech.util;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import qa.auto.innotech.env.Env;
import qa.auto.innotech.env.config.APIConfig;

import java.util.List;

import static io.restassured.RestAssured.given;

public abstract class RestUtil {

    protected APIConfig config;
    private final String path;

    public RestUtil(String path) {
        this.path = path;
    }

    private RequestSpecification getDefaultSpec(ContentType contentType) {
        RequestSpecification requestSpecification = RestAssured.given()
                .contentType(contentType)
                .filters(getFilters())
                .relaxedHTTPSValidation();

        return ("qa".equals(System.getenv("env")) ?
                requestSpecification :
                requestSpecification.baseUri(config.url()));
    }

    private RequestSpecification getDefaultSpec() {
        return getDefaultSpec(ContentType.JSON);
    }

    public Response doPost(Object body) {
        return given(getDefaultSpec())
                .basePath(path)
                .body(body)
                .post();
    }

    public Response doGet() {
        return given(getDefaultSpec())
                .basePath(path)
                .get();
    }

    public Response doGet(int id) {
        return given(getDefaultSpec())
                .basePath(path)
                .pathParam("id", id)
                .get("/{id}");
    }

    public Response doDelete(int id) {
        return given(getDefaultSpec())
                .basePath(path)
                .pathParam("id", id)
                .delete("/{id}");
    }

    private List<Filter> getFilters() {
        return List.of(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}

