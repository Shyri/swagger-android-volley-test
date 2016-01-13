package es.shyri.swagger.android.volley.petstore.full;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import io.swagger.client.ApiInvoker;
import io.swagger.client.api.PetApi;
import io.swagger.client.model.Category;
import io.swagger.client.model.Pet;

/**
 * Created by Shyri on 11/01/2016.
 */
@RunWith(RobolectricTestRunner.class)
public class PetApiTest {
    PetApi api = null;

    @Before
    public void setup() {
        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        ApiInvoker.initializeInstance(new NoCache(), network, 4, new ExecutorDelivery(Executors.newSingleThreadExecutor()));
        ApiInvoker.getInstance().setApiKey("special-key");
        api = new PetApi();
    }

    @Test
    public void testCreateAndGetPet() throws Exception {
        final Waiter waiter = new Waiter();
        final Pet pet = createRandomPet();
        api.addPet(pet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                waiter.resume();
                waiter.assertTrue(true);
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

        api.getPetById(pet.getId(), new Response.Listener<Pet>() {
            @Override
            public void onResponse(Pet response) {
                Pet fetched = response;
                waiter.assertNotNull(fetched);
                waiter.assertEquals(pet.getId(), fetched.getId());
                waiter.assertNotNull(fetched.getCategory());
                waiter.assertEquals(fetched.getCategory().getName(), pet.getCategory().getName());
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

    private Pet createRandomPet() {
        Pet pet = new Pet();
        pet.setId(System.currentTimeMillis());
        pet.setName("gorilla");

        Category category = new Category();
        category.setName("really-happy");

        pet.setCategory(category);
        pet.setStatus(Pet.StatusEnum.available);
        List<String> photos = Arrays.asList(new String[]{"http://foo.bar.com/1", "http://foo.bar.com/2"});
        pet.setPhotoUrls(photos);

        return pet;
    }
}
