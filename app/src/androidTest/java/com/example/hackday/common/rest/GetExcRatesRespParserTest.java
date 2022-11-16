package com.example.hackday.common.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.hackday.common.asynctask.IPostAsyncCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GetExcRatesRespParserTest {

    private JSONObject getResponseJSONObject(String btPriceUsd, String ethPriceUsd) throws JSONException {
        final String ApiResponse = String.format(
                "{\"status\":{\"timestamp\":\"2022-11-16T09:00:31.710Z\",\"error_code\":0,\"error_message\":null,\"elapsed\":19,\"credit_count\":1,\"notice\":null,\"total_count\":9253},\"data\":[{\"id\":1,\"name\":\"Bitcoin\",\"symbol\":\"BTC\",\"slug\":\"bitcoin\",\"num_market_pairs\":9835,\"date_added\":\"2013-04-28T00:00:00.000Z\",\"tags\":[\"mineable\",\"pow\",\"sha-256\",\"store-of-value\",\"state-channel\",\"coinbase-ventures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"binance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"framework-ventures-portfolio\",\"galaxy-digital-portfolio\",\"huobi-capital-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\"],\"max_supply\":21000000,\"circulating_supply\":19208725,\"total_supply\":19208725,\"platform\":null,\"cmc_rank\":1,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\",\"quote\":{\"USD\":{\"price\":%s,\"volume_24h\":34703957734.1906,\"volume_change_24h\":-16.9627,\"percent_change_1h\":-0.51529744,\"percent_change_24h\":-0.82401878,\"percent_change_7d\":-7.53821404,\"percent_change_30d\":-12.94302762,\"percent_change_60d\":-15.66603078,\"percent_change_90d\":-28.48537589,\"market_cap\":322078406970.8667,\"market_cap_dominance\":38.294,\"fully_diluted_market_cap\":352113247828.17,\"tvl\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\"}}},{\"id\":1027,\"name\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethereum\",\"num_market_pairs\":6193,\"date_added\":\"2015-08-07T00:00:00.000Z\",\"tags\":[\"pos\",\"smart-contracts\",\"ethereum-ecosystem\",\"coinbase-ventures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"binance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"framework-ventures-portfolio\",\"hashkey-capital-portfolio\",\"kenetic-capital-portfolio\",\"huobi-capital-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\",\"injective-ecosystem\"],\"max_supply\":null,\"circulating_supply\":122373866.2178,\"total_supply\":122373866.2178,\"platform\":null,\"cmc_rank\":2,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\",\"quote\":{\"USD\":{\"price\":%s,\"volume_24h\":11455390223.937336,\"volume_change_24h\":-11.4217,\"percent_change_1h\":-0.95767897,\"percent_change_24h\":-2.71059078,\"percent_change_7d\":-1.92786097,\"percent_change_30d\":-5.47662304,\"percent_change_60d\":-13.70956647,\"percent_change_90d\":-32.95033717,\"market_cap\":151410787061.97922,\"market_cap_dominance\":18.0022,\"fully_diluted_market_cap\":151410787061.98,\"tvl\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\"}}},{\"id\":825,\"name\":\"Tether\",\"symbol\":\"USDT\",\"slug\":\"tether\",\"num_market_pairs\":41953,\"date_added\":\"2015-02-25T00:00:00.000Z\",\"tags\":[\"payments\",\"stablecoin\",\"asset-backed-stablecoin\",\"avalanche-ecosystem\",\"solana-ecosystem\",\"arbitrum-ecosytem\",\"moonriver-ecosystem\",\"injective-ecosystem\",\"bnb-chain\",\"usd-stablecoin\"],\"max_supply\":null,\"circulating_supply\":66058586035.334526,\"total_supply\":73141766321.23428,\"platform\":{\"id\":1027,\"name\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethereum\",\"token_address\":\"0xdac17f958d2ee523a2206206994597c13d831ec7\"},\"cmc_rank\":3,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\",\"quote\":{\"USD\":{\"price\":0.9992833330162293,\"volume_24h\":46459404118.49288,\"volume_change_24h\":-15.0361,\"percent_change_1h\":-0.00811486,\"percent_change_24h\":0.01923414,\"percent_change_7d\":-0.03938081,\"percent_change_30d\":-0.08074701,\"percent_change_60d\":-0.07328322,\"percent_change_90d\":-0.0786069,\"market_cap\":66011244027.728424,\"market_cap_dominance\":7.8505,\"fully_diluted_market_cap\":73089348032.18,\"tvl\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\"}}},{\"id\":3408,\"name\":\"USD Coin\",\"symbol\":\"USDC\",\"slug\":\"usd-coin\",\"num_market_pairs\":6868,\"date_added\":\"2018-10-08T00:00:00.000Z\",\"tags\":[\"medium-of-exchange\",\"stablecoin\",\"asset-backed-stablecoin\",\"fantom-ecosystem\",\"arbitrum-ecosytem\",\"moonriver-ecosystem\",\"bnb-chain\",\"usd-stablecoin\"],\"max_supply\":null,\"circulating_supply\":44446822487.542816,\"total_supply\":44446822487.542816,\"platform\":{\"id\":1027,\"name\":\"Ethereum\",\"symbol\":\"ETH\",\"slug\":\"ethereum\",\"token_address\":\"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48\"},\"cmc_rank\":4,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\",\"quote\":{\"USD\":{\"price\":1.0001424810385118,\"volume_24h\":3214789339.48868,\"volume_change_24h\":-7.1336,\"percent_change_1h\":0.00977152,\"percent_change_24h\":-0.02779736,\"percent_change_7d\":0.02323908,\"percent_change_30d\":0.00866152,\"percent_change_60d\":0.01302153,\"percent_change_90d\":0.02012842,\"market_cap\":44453155316.96939,\"market_cap_dominance\":5.2867,\"fully_diluted_market_cap\":44453155316.97,\"tvl\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\"}}},{\"id\":1839,\"name\":\"BNB\",\"symbol\":\"BNB\",\"slug\":\"bnb\",\"num_market_pairs\":1137,\"date_added\":\"2017-07-25T00:00:00.000Z\",\"tags\":[\"marketplace\",\"centralized-exchange\",\"payments\",\"smart-contracts\",\"alameda-research-portfolio\",\"multicoin-capital-portfolio\",\"moonriver-ecosystem\",\"bnb-chain\"],\"max_supply\":200000000,\"circulating_supply\":159974021.45156842,\"total_supply\":159979963.59042934,\"platform\":null,\"cmc_rank\":5,\"self_reported_circulating_supply\":null,\"self_reported_market_cap\":null,\"tvl_ratio\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\",\"quote\":{\"USD\":{\"price\":274.14573013156195,\"volume_24h\":1015175596.0776738,\"volume_change_24h\":-24.3738,\"percent_change_1h\":-1.53704599,\"percent_change_24h\":-1.91879753,\"percent_change_7d\":-10.54285045,\"percent_change_30d\":0.99271079,\"percent_change_60d\":-1.0135354,\"percent_change_90d\":-11.01792683,\"market_cap\":43856194912.92238,\"market_cap_dominance\":5.2157,\"fully_diluted_market_cap\":54829146026.31,\"tvl\":null,\"last_updated\":\"2022-11-16T08:58:00.000Z\"}}}]}",
                btPriceUsd, ethPriceUsd);
        return new JSONObject(ApiResponse);
    }

    @Test
    public void parseApiResponse() {
        final String prrecondnBtcPrice = "99999.99999999";
        final String prrecondEthPrice = "88888.88888888";
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        final GetExcRatesRespParser parser = new GetExcRatesRespParser(appContext);

        final String[] parseExceptions = {null,null};
        try {
            parser.Parse(getResponseJSONObject(prrecondnBtcPrice, prrecondEthPrice), new IPostAsyncCall<String>() {
                @Override
                public void onComplete(String result) {

                    try {
                        final JSONArray parserResult = new JSONArray(result);

                        assertEquals(2, parserResult.length());

                        int counter = 0;
                        for(int i=0;i<parserResult.length();i++){
                            JSONObject object= parserResult.getJSONObject(i);

                            if(object.getString(GetExcRatesRespParser.RespInternalCurrencyProp).equalsIgnoreCase("BTC")) {
                                assertEquals(prrecondnBtcPrice, object.getString(GetExcRatesRespParser.RespInternalUsRateProp));
                                counter++;
                            } else if(object.getString(GetExcRatesRespParser.RespInternalCurrencyProp).equalsIgnoreCase("ETH")) {
                                assertEquals(prrecondEthPrice, object.getString(GetExcRatesRespParser.RespInternalUsRateProp));
                                counter++;
                            }
                        }

                        assertEquals("Must be parsed two currencies",2, counter);
                    }catch(Exception e){
                        parseExceptions[0] = e.toString();
                    }
                }

                @Override
                public void onError(String error) {
                    parseExceptions[1] = error;
                }
            });
        } catch (JSONException e) {
            fail(String.format("Must not be caught any JSON parser errors. Error: %s", e));
        }

        assertNull(String.format("Must not be thrown any exception. Exception: %s", parseExceptions[0]), parseExceptions[0]);
        assertNull(String.format("Must not be caught any parser errors. Error: %s", parseExceptions[1]), parseExceptions[1]);

        assertEquals("com.example.hackday", appContext.getPackageName());
    }

    @Test
    public void parseApiResponseWithNoCallback() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        final GetExcRatesRespParser parser = new GetExcRatesRespParser(appContext);

        try {
            parser.Parse(getResponseJSONObject("0", "0"), null);
        } catch (JSONException e) {
            fail(String.format("Must not be caught any JSON parser errors. Error: %s", e));
        }
    }
}
