package com.example.hackday.common.rest;

import android.content.Context;
import android.util.Log;

import com.example.hackday.R;
import com.example.hackday.common.asynctask.IPostAsyncCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetExcRatesRespParser {

    public static final String RespApiQuoteProp = "quote";
    public static final String RespApiUsdProp = "USD";
    public static final String RespApiPriceProp = "price";
    public static final String RespApiErrorCodeProp = "error_code";
    public static final String RespApiErrorMsgProp = "error_message";
    public static final String RespApiStatusProp = "status";
    public static final String RespApiDataProp = "data";
    public static final String RespApiIdProp = "id";
    public static final String RespApiLastUpdatedProp = "last_updated";
    public static final String RespApiSymbolProp = "symbol";

    public static final String RespInternalCurrencyProp = "currency";
    public static final String RespInternalUsRateProp = "usrate";
    public static final String RespInternalDateProp = "date";

    final private Context context;

    public GetExcRatesRespParser(Context ctx){
        context = ctx;
    }

    public void Parse(JSONObject response, IPostAsyncCall<String> callBack){
        try {
            Log.d("GetExcRatesRespParser", String.format("Response is: %s", response.toString()));

            final JSONObject statusObj = response.getJSONObject(RespApiStatusProp);

            final int errorCode = statusObj.getInt(RespApiErrorCodeProp);
            final String errorMsg = statusObj.getString(RespApiErrorMsgProp);

            if(errorCode > 0)
                throw new Exception(String.format(context.getString(R.string.exc_rates_resp_with_error), errorCode, errorMsg));

            JSONArray currExchRates = new JSONArray();

            final JSONArray array= response.getJSONArray(RespApiDataProp);
            for(int i=0;i<array.length();i++)
            {
                JSONObject object= array.getJSONObject(i);
                final int id = object.getInt(RespApiIdProp);
                final String lastUpdated = object.getString(RespApiLastUpdatedProp);
                final String symbol = object.getString(RespApiSymbolProp);

                if(1 == id)
                    currExchRates.put(getExchRateString(lastUpdated, symbol, object));
                else if(1027 == id)
                    currExchRates.put(getExchRateString(lastUpdated, symbol, object));

                if(currExchRates.length() >= 2)
                    break;
            }

            if(null != callBack)
                callBack.onComplete(currExchRates.toString());
        }
        catch(Exception e)
        {
            final String err = String.format(context.getString(R.string.exc_rates_resp_parse_with_error), e);
            Log.e("GetExcRatesRespParser", err);

            if(null != callBack)
                callBack.onError(err);
        }
    }

    private static JSONObject getExchRateString(String lastUpdated, String symbol, JSONObject object) throws JSONException {
        final JSONObject excRateResult = new JSONObject();
        excRateResult.put(RespInternalCurrencyProp, symbol);
        excRateResult.put(RespInternalUsRateProp, getUsdExcRateString(object));
        excRateResult.put(RespInternalDateProp, lastUpdated);
        return excRateResult;
    }

    private static String getUsdExcRateString(JSONObject jsonExchRateItem) throws JSONException {
        final JSONObject quote = jsonExchRateItem.getJSONObject(RespApiQuoteProp);
        final JSONObject usd = quote.getJSONObject(RespApiUsdProp);
        return usd.getString(RespApiPriceProp);
    }
}
