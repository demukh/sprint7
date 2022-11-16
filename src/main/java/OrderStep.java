import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;

public class OrderStep extends GetSpec {
    private static final String ORDER = "api/v1/orders/";

    @Step("Создание нового заказа с использованием {order}")
    public Response createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER);
    }

    @Step("Запрос списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER)
                .then()
                .log().all();
    }
}