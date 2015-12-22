package es.shyri.swagger.android.volley.echo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.jodah.concurrentunit.Waiter;

import org.junit.Test;

import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiInvoker;
import io.swagger.client.api.DefaultApi;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    ApiInvoker apiInvoker;
    DefaultApi defaultApi;

    public ApplicationTest() {
        super(Application.class);
    }

    @Test
    public void getTest() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootGet(new Response.Listener<String>() {
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
    public void postText() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.rootPost("name", "2005", new Response.Listener<String>() {
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
    public void testPathIdGet() throws TimeoutException {
        final Waiter waiter = new Waiter();
        defaultApi.testPathIdGet("1", new Response.Listener<String>() {
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