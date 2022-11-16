import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends GetSpec {

    private static final String COURIER = "api/v1/courier/";
    private static final String COURIER_LOGIN = "api/v1/courier/login";

    @Step("Запрос на вход с логином:{credentials.login} паролем:{credentials.password}")
    public ValidatableResponse loginCourier(CourierCreds courierCredentials) {
        return given()
                .header("Content-type", "application/json")
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Создание курьра с логином:{courier.login} паролем:{courier.password} именем:{courier.firstName}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Удаление курьера с id:{courierId}")
    public ValidatableResponse deleteCourier(int courierId) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER + courierId)
                .then();
    }
}