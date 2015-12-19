package es.shyri.swagger.android.volley;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeoutException;

import io.swagger.client.api.ApiInvoker;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.api.Responses;
import io.swagger.client.model.Pet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.greaterThan;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    private final String FAKE_PET_ID = "560eea59a62de703006d2b58";
    private final int PET_QUERY_LIMIT = 10;
    ApiInvoker apiInvoker;
    DefaultApi defaultApi;

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
    public void getPets() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootGet(PET_QUERY_LIMIT, new Responses.PetListResponse() {
            @Override
            public void onResponse(List<Pet> petList) {
                waiter.assertTrue(true);
                waiter.assertThat(petList.size(), greaterThan(0));
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
    }


    @Test
    public void postPet() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootPost(getFakePet(), new Response.Listener<String>() {
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
        waiter.await();
    }


    @Test
    public void putPet() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootPut(getFakePet(), new Response.Listener<String>() {
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
        waiter.await();
    }

    @Test
    public void getPetById() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.petIdGet(FAKE_PET_ID, new Response.Listener<String>() {
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
        waiter.await();
    }

    private Pet getFakePet() {
        Pet pet = new Pet();
        pet.setName("My Pet");
        pet.setBirthday(2);

        return pet;
    }
}