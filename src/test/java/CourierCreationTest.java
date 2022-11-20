import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.*;


public class CourierCreationTest {

    CourierClient courierClient;
    Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }


    @Test
    @DisplayName(value = "Create new courier")
    @Description(value = "Creating courier with correct login and password")
    public void successCreatingCourierTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(201);
        createResponse.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier already existing")
    @Description("Checking Response when trying to create identical couriers")
    public void creatingIdenticalCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Create courier without login")
    @Description("Creating courier without login")
    public void creatingCourierWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Creating a courier without password")
    public void creatingCourierWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}