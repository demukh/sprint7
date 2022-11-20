import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class GetOrderTest {

    @Test
    @DisplayName("Success getting a list of orders")
    @Description("Check list of orders should not be empty")
    public void getOrderListTest() {
        OrderStep order = new OrderStep();
        ValidatableResponse orderListResponse = order.getOrdersList().statusCode(200);
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}