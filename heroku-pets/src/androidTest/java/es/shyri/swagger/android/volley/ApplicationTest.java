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

import java.util.concurrent.TimeoutException;

import io.swagger.client.api.ApiInvoker;
import io.swagger.client.api.DefaultApi;
import io.swagger.client.model.Pet;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
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
    public void postPet() throws TimeoutException {
        Pet pet = new Pet();
        pet.setName("My Pet");
        pet.setBirthday(2);

        final Waiter waiter = new Waiter();
        defaultApi.rootPost(pet, new Response.Listener<String>() {
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
}