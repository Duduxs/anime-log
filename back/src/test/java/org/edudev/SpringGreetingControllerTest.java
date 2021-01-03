package org.edudev;

import static org.hamcrest.CoreMatchers.is;

import org.edudev.services.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

@QuarkusTest
public class SpringGreetingControllerTest {

	private String token;
	
	@BeforeEach
	public void gerarToken() throws Exception {
		token = TokenUtils.generateTokenString("/JWTFuncionarioClaims.json", null);
	}
	
	 private RequestSpecification givenAutenticado() {
	        return RestAssured.given()
	                .contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
	    }
	
    @Test
    public void testHelloEndpoint() {
    	givenAutenticado()
          .when().get("/clients/count")
          .then()
             .statusCode(200)
             .body(is("0"));
    }

}