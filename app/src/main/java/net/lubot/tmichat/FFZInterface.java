package net.lubot.tmichat;

import net.lubot.tmichat.model.FFZEmotes.FFZSet;
import net.lubot.tmichat.model.FFZUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FFZInterface {
    @GET("_room/{user}")
    Call<FFZUser> getUser(@Path("user") String user);
    @GET("set/{id}")
    Call<FFZSet> getSet(@Path("id") String id);
}
