package es.shyri.swagger.android.volley.petstore.simple;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiInvoker;
import io.swagger.client.Responses;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.NewPet;
import io.swagger.client.model.Pet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {

    private ApiInvoker apiInvoker;
    private DefaultApi defaultApi;

    long petTestId;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp() {
        ApiInvoker.initializeInstance(getInstrumentation().getContext());
        apiInvoker = ApiInvoker.getInstance();
        defaultApi = new DefaultApi();
    }

    @Test
    public void testPets() throws TimeoutException {
        final Waiter waiter = new Waiter();
        NewPet newPet = new NewPet();
        newPet.setName("My cat");
        newPet.setTag("My cat tag");
        defaultApi.addPet(newPet, new Responses.PetResponse() {
            @Override
            public void onResponse(Pet pet) {
                waiter.assertTrue(true);
                waiter.assertEquals("My cat", pet.getName());
                petTestId = pet.getId();
                waiter.resume();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                waiter.assertTrue(false);
                waiter.resume();
            }
        });
        waiter.await();

        defaultApi.findPetById(petTestId, new Responses.PetResponse() {
            @Override
            public void onResponse(Pet pet) {
                waiter.assertTrue(true);
                waiter.assertEquals("My cat", pet.getName());
                waiter.resume();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                waiter.assertTrue(false);
                waiter.resume();
            }
        });
        waiter.await();

        defaultApi.deletePet(petTestId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                waiter.assertTrue(true);
                waiter.resume();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                waiter.assertTrue(false);
                waiter.resume();
            }
        });
    }
}