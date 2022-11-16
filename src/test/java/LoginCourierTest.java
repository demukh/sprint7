
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.*;

public class LoginCourierTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierCreds courierCreds;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierCreds = new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Successful courier login")
    @Description("Creating new courier and login with correct credentials and checking the success login courier, statusCode=200")
    public void successLoginCourierTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds).statusCode(200);
        courierId = loginResponse.extract().path("id");
        loginResponse.assertThat().body("id", notNullValue());
        System.out.println(courierId);
    }

    @Test
    @DisplayName("Failed courier login with incorrect courierLogin")
    @Description("Creating new courier, login with incorrect courierLogin and Check the failed login courier, statusCode=404")
    public void failedLoginCourierIncorrectLoginTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds);
        courierId = loginResponse.extract().path("id");
        CourierCreds incorrectLoginCred = new CourierCreds(courier.getLogin() + "test", courier.getPassword());
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectLoginCred).statusCode(404);
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Failed courier login with incorrect courierPassword")
    @Description("Creating new courier, login with incorrect courierPassword and checking the failed login courier, statusCode=404")
    public void failedLoginCourierIncorrectPasswordTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds);
        courierId = loginResponse.extract().path("id");
        CourierCreds incorrectPasswordCred = new CourierCreds(courier.getLogin(), courier.getPassword() + "123");
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectPasswordCred).statusCode(404);
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Failed courier login without courierLogin")
    @Description("Creating new courier, login without courierLogin and check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutLoginTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds);
        courierId = loginResponse.extract().path("id");
        CourierCreds withoutLoginCred = new CourierCreds("", courier.getPassword());
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutLoginCred).statusCode(400);
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Failed courier login without courierPassword")
    @Description("Creating new courier, login without courierPassword and check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutPasswordTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds);
        courierId = loginResponse.extract().path("id");
        CourierCreds withoutPasswordCred = new CourierCreds(courier.getLogin(), "");
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutPasswordCred).statusCode(400);
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Failed courier login without courierCred")
    @Description("Creating new courier, login without courierCred and check the failed login courier, statusCode=400")
    public void failedLoginCourierWithoutCredTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCreds);
        courierId = loginResponse.extract().path("id");
        CourierCreds withoutCred = new CourierCreds("", "");
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutCred).statusCode(400);
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}