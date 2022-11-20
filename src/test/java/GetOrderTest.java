import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class GetOrderTest {

    @Test
    @DisplayName("Succes getting a list of orders")
    @Description("Check getting a list of orders is not empty")
    public void getOrderListTest() {
        OrderStep order = new OrderStep();
        ValidatableResponse orderListResponse = order.getOrdersList().statusCode(200);
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}