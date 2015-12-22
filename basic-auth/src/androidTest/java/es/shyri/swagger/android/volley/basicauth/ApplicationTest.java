package es.shyri.swagger.android.volley.basicauth;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiInvoker;
import io.swagger.client.api.DefaultApi;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends ApplicationTestCase<Application> {
    DefaultApi defaultApi;
    ApiInvoker apiInvoker;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    public void setUp() {
        ApiInvoker.initializeInstance(getInstrumentation().getContext());
        apiInvoker = ApiInvoker.getInstance();
        defaultApi = new DefaultApi();
        apiInvoker.setUsername("user");
        apiInvoker.setPassword("pass");
    }


    @Test
    public void getTest() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootGet(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                waiter.assertTrue(true);
                waiter.resume();
                Log.d("GA", "Rs : " + response);
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