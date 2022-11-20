import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private final List<String> color;
    private final Matcher<Object> expected;

    public OrderCreationTest(List<String> color, Matcher<Object> expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters()
    @Step("Create colours list")
    public static Object[][] getColorData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), notNullValue()},
                {List.of("BLACK"), notNullValue()},
                {List.of("GREY"), notNullValue()},
                {null, notNullValue()}
        };
    }

    @Test
    @DisplayName("Success creating order")
    @Description(value = "Check creating order with different list of colors.")
    public void CreatingOrderTest() {
        OrderStep orderStep = new OrderStep();
        Order order = new Order(color);
        Response createOrderResponse = orderStep.createOrder(order);
        createOrderResponse.then().assertThat().statusCode(201)
                .and()
                .body("track", expected);
    }
}