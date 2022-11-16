#include <jni.h>
#include <string>
#include <chrono>

#define  DEFAULT_LOG_STRING_LENGTH 1024

std::string BuildLogString(const std::string& currencyName, const std::string& priceUsd)
{
    auto logLineTimePoint = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());

    std::string logSting;
    logSting.reserve(DEFAULT_LOG_STRING_LENGTH);

    logSting.append(std::ctime(&logLineTimePoint));
    logSting[logSting.length() -1 ] = ' ';
    logSting.append(currencyName);
    logSting.append(" ");
    logSting.append(priceUsd);

    return logSting;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hackday_common_asynctask_ProcessExchRateNativelyAsync_ProcessExchRateNatively(
        JNIEnv *env,
        jobject /*thiz*/,
        jstring curr_symbol,
        jstring price_usd)
{
    auto logLine = BuildLogString(env->GetStringUTFChars(curr_symbol, nullptr),
                   env->GetStringUTFChars(price_usd, nullptr));

    return env->NewStringUTF(logLine.c_str());
}