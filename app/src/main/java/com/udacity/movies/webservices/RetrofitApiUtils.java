package com.udacity.movies.webservices;


import com.udacity.movies.shared.Constants;

public class RetrofitApiUtils {

    public static RetrofitService getRetrofitService() {
        return RetrofitClient.getClient(Constants.BASE_SERVER).create(RetrofitService.class);
    }

}
