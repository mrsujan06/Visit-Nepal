package com.example.arvin.nepaltouristguide.restaurant_detail;

import com.example.arvin.nepaltouristguide.detail_base.BaseDetailPresenter;
import com.example.arvin.nepaltouristguide.detail_base.DetailMvpView;
import com.example.arvin.nepaltouristguide.model.placeDetailResponse.PlaceDetailResponse;
import com.example.arvin.nepaltouristguide.service.Interactor.ApiNepalServiceInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantDetailPresenter extends BaseDetailPresenter<DetailMvpView> {

    private CompositeDisposable bag = new CompositeDisposable();

    @Inject
    public RestaurantDetailPresenter(ApiNepalServiceInteractor mApiNepalServiceInteractor) {
        this.mApiNepalServiceInteractor = mApiNepalServiceInteractor;
    }

    @Override
    public void getPlaceDetail(String placeId, String apiKey) {
        bag.add(mApiNepalServiceInteractor.getDetailData(placeId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placeDetailResponse ->
                        {
                            if (placeDetailResponse != null) {
                                setRestaurantData(placeDetailResponse);
                            }
                        },
                        throwable -> getMvpView().onFetchDataError(throwable.getMessage())
                ));

        getMvpView().onFetchDataProgress();
    }

    private void setRestaurantData(PlaceDetailResponse placeDetailResponse) {
        getMvpView().onFetchDataSuccess(placeDetailResponse);
        updateToolbarName(placeDetailResponse.getResult().getName());
        updatePlaceName(placeDetailResponse.getResult().getName());
        updatePlaceAddress(placeDetailResponse.getResult().getFormattedAddress());
        updatePlacePhoneNumber(placeDetailResponse.getResult().getFormattedPhoneNumber());
        updateOpenCloseStatus(placeDetailResponse.getResult().getOpeningHours().getOpenNow());
        updateOpeningTimes(placeDetailResponse.getResult().getOpeningHours().getWeekdayText());
        updateRating(placeDetailResponse.getResult().getRating());
        updateRatingBar(placeDetailResponse.getResult().getRating());
        updateUserRatingTotal(placeDetailResponse.getResult().getUserRatingsTotal().toString());
        updateRatingBarChart();
        updateUserComments(placeDetailResponse);
        updateLocation(placeDetailResponse.getResult().getGeometry().getLocation().getLat(), placeDetailResponse.getResult().getGeometry().getLocation().getLng());
    }

}