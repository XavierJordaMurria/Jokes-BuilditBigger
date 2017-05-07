/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.jorda.jokesapp.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.example.Joker;
import javax.inject.Named;



/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.jokesapp.jorda.com",
                ownerName = "backend.jokesapp.jorda.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);
        return response;
    }

    //you have to make sure to rebuild backend when changing anything to be visible to call it
    @ApiMethod(name = "getJokeFromLibrary")
    public MyBean getJokeFromLibrary() {
        String joke = Joker.getJoke();
        MyBean response = new MyBean();
        response.setData(joke);

        return response;
    }

}
