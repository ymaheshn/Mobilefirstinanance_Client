package network;

import base.MFFResponse;
import base.MFFResponseNew;
import dashboard.models.DashboardCount;
import dashboard.models.GraphCount;
import dashboard.models.ProfileCount;
import loans.model.*;
import login.model.EntityResponse;
import login.model.LoginRequest;
import login.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MFFApiService {
    @POST("/LoginService/login")
    Call<MFFResponse<LoginResponse>> login(@Body LoginRequest request);

    @GET("/UserService/users/root")
    Call<MFFResponseNew<EntityResponse>> getEntityDetails(@Query("access_token") String accessToken);


    @GET("/PortfolioService/searchPortfolio")
    Call<CollectionPortfolioResponse> getCollections(@Query("access_token") String accessToken,
                                                     @Query("pageNumber") int pageNumber,
                                                     @Query("numberOfRecords") int numberOfRecords);

    @GET("/PortfolioService/searchPortfolio")
    Call<CollectionPortfolioResponse> searchCollectionUsingName(@Query("access_token") String accessToken,
                                                                @Query("Name") String name,
                                                                @Query("pageNumber") int pageNumber,
                                                                @Query("numberOfRecords") int numberOfRecords);


    @GET("/PortfolioService/geteventsBycontractUUID")
    Call<CollectionPortfolioDetailsResponse> getPortfolioCollectionDetails(@Query("access_token") String accessToken,
                                                                           @Query("contractUUID") String contractUUID,
                                                                           @Query("eventType") String eventType);

    @Headers({
            "Content-Type: application/json",
    })
    @POST("/PortfolioService/savePortfolioRepayment")
    Call<SavedPortfolioResponse> savePortfolio(@Query("access_token") String accessToken, @Body String array);

    @GET("/ProfileService/profile/count")
    Call<MFFResponseNew<ProfileCount>> getTotalClient(@Query("access_token") String accessToken);

    @GET("/WorkflowService/workflows/count")
    Call<MFFResponseNew<DashboardCount>> getOnBoardDetailsCount(@Query("access_token") String accessToken);

    @GET("/PortfolioService/contracts/delinquency/count/{date}")
    Call<DashBoardGraphResponse> getGraphDetails(@Path("date") String date, @Query("access_token") String accessToken);

    @GET("/PortfolioService/bulkReceiveAndDeals")
    Call<LoansPortfolioResponse> getLoans(@Query("access_token") String accessToken,
                                          @Query("pageNumber") int pageNumber,
                                          @Query("numberOfRecords") int numberOfRecords,
                                          @Query("eventType") String eventType);

    @GET("/PortfolioService/bulkReceiveAndDeals")
    Call<LoansPortfolioResponse> searchLoansUsingName(@Query("access_token") String accessToken,
                                                      @Query("Name") String name,
                                                      @Query("pageNumber") int pageNumber,
                                                      @Query("numberOfRecords") int numberOfRecords,
                                                      @Query("eventType") String eventType);
    @GET("/PortfolioService/bulkReceiveAndDeals")
    Call<LoansPortfolioResponse> searchLoansUsingHierarchy(@Query("access_token") String accessToken,
                                                      @Query("Hierarchy") String name,
                                                      @Query("pageNumber") int pageNumber,
                                                      @Query("numberOfRecords") int numberOfRecords,
                                                      @Query("eventType") String eventType);

    @GET("/PortfolioService/bulkReceiveAndDeals")
    Call<LoansPortfolioResponse> searchLoansUsingNationalId(@Query("access_token") String accessToken,
                                                           @Query("nationalId") String name,
                                                           @Query("pageNumber") int pageNumber,
                                                           @Query("numberOfRecords") int numberOfRecords,
                                                           @Query("eventType") String eventType);

    @GET("/PortfolioService/bulkReceiveAndDeals")
    Call<LoansPortfolioResponse> searchLoansUsingIdentifier(@Query("access_token") String accessToken,
                                                           @Query("identifier") String name,
                                                           @Query("pageNumber") int pageNumber,
                                                           @Query("numberOfRecords") int numberOfRecords,
                                                           @Query("eventType") String eventType);

    @GET("/ProfileService/files/{profileID}/business")
    Call<BusinessDocumentsModel> getBusinessDocuments(@Path("profileID") String name,@Query("access_token") String accessToken);


}