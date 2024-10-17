package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {
	
	Faker faker;
	User userPayload;
	
	
    @BeforeClass
	public void setup () {
		
    	faker = new Faker();
    	userPayload= new User();
    	
    	userPayload.setId(faker.idNumber().hashCode());
    	userPayload.setUsername(faker.name().username());
    	userPayload.setFirstName(faker.name().firstName());
    	userPayload.setLastName(faker.name().lastName());
    	userPayload.setEmail(faker.internet().safeEmailAddress());
    	userPayload.setPhone(faker.phoneNumber().cellPhone());
    	userPayload.setPassword(faker.internet().password(5, 10));
    	
    	
	}
    
    @Test
    public void testPostUser() {
    	
    	Response response = UserEndPoints.createUser(userPayload);
    	
    	response.then().log().all();
    	
    	Assert.assertEquals(response.getStatusCode(),200);
    	
    }
    
    @Test
    public void testGetUsername() {

    	Response response =UserEndPoints.readUser(this.userPayload.getUsername());
    	
    	response.then().log().all();
    	
    	Assert.assertEquals(response.getStatusCode(), 200);
    	
    }
    
    @Test
    public void testUpdateUsername() {
    	
    	// updated data
    	userPayload.setFirstName(faker.name().firstName());
    	userPayload.setLastName(faker.name().lastName());
    	userPayload.setEmail(faker.internet().safeEmailAddress());
    	
    	
      
    	Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);  // doubt 
    //	response.then().log().all();
    	response.then().log().body().statusCode(200);   // this is called chi assertion
    	Assert.assertEquals(response.getStatusCode(), 200);
    	
    	// check the data after update
    	response= UserEndPoints.readUser(this.userPayload.getUsername());
    // 	response.then().and().log().all();
    	Assert.assertEquals(response.getStatusCode(), 200);
    
    }
    

}
