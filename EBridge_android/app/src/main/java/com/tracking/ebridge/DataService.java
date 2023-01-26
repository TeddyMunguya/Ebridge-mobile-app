package com.tracking.ebridge;

import com.tracking.ebridge.models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @POST("studentregister.php")
    @FormUrlEncoded
    Call<Result> registerStudents(@Field("name") String name, @Field("phone")String mobile,
                                  @Field("mail")String email, @Field("department")String dept,
                                  @Field("username")String username, @Field("password")String password);

    @POST("facultyregister.php")
    @FormUrlEncoded
    Call<Result> registerFaculty(@Field("name") String name, @Field("phone")String mobile,
                                 @Field("mail")String email, @Field("department")String dept,
                                 @Field("username")String username, @Field("password")String password);

    @POST("studentlogin.php")
    @FormUrlEncoded
    Call<Result> studentLogin(@Field("username")String username, @Field("password")String password);

    @POST("facultylogin.php")
    @FormUrlEncoded
    Call<Result> facultyLogin(@Field("username")String username, @Field("password")String password);

    @GET("gettimetable.php")
    Call<Result> getTimeTable();

    @GET("getquestionpapers.php")
    Call<Result> getQuestionPapers();

    @GET("getnotification.php")
    Call<Result> getNotifications();

    @POST("adddoubt.php")
    @FormUrlEncoded
    Call<Result> sendDoubt(@Field("subject")String Subject, @Field("description")String description, @Field("username")String username);

    @GET("getdoubts.php")
    Call<Result> getDoubts();

    @POST("addnotification.php")
    @FormUrlEncoded
    Call<Result> addNotification(@Field("subject")String Subject, @Field("description")String description, @Field("username")String username);

    @POST("addquestionspapers.php")
    @FormUrlEncoded
    Call<Result> addQuestionPaper(@Field("subject")String Subject, @Field("description")String description,
                                  @Field("image")String image);

    @POST("addtimetable.php")
    @FormUrlEncoded
    Call<Result> addTimeTable(@Field("subject")String Subject, @Field("description")String description,
                              @Field("image")String image);
}
