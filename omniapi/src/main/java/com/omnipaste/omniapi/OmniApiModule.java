package com.omnipaste.omniapi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.omnipaste.omniapi.deserializer.ClippingTypeDeserializer;
import com.omnipaste.omniapi.prefs.ApiUrl;
import com.omnipaste.omniapi.prefs.PrefsModule;
import com.omnipaste.omniapi.serializer.PhoneCallStateSerializer;
import com.omnipaste.omniapi.serializer.PhoneCallTypeSerializer;
import com.omnipaste.omniapi.serializer.SmsMessageStateSerializer;
import com.omnipaste.omniapi.serializer.SmsMessageTypeSerializer;
import com.omnipaste.omnicommon.dto.ClippingDto;
import com.omnipaste.omnicommon.dto.PhoneCallDto;
import com.omnipaste.omnicommon.dto.SmsMessageDto;
import com.omnipaste.omnicommon.prefs.StringPreference;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module(
  includes = PrefsModule.class,
  complete = false,
  library = true
)
public class OmniApiModule {
  @Provides
  @Singleton
  public Endpoint provideEndpoint(@ApiUrl StringPreference apiUrl) {
    return Endpoints.newFixedEndpoint(apiUrl.get());
  }

  @Provides
  @Singleton
  public Client provideClient() {
    return new OkClient(new OkHttpClient());
  }

  @Provides
  @Singleton
  public RestAdapter.LogLevel provideLogLevel() {
    return RestAdapter.LogLevel.FULL;
  }

  @Provides
  @Singleton
  public RestAdapter provideRestAdapter(Endpoint endpoint, Client client, ApiHeaders apiHeaders, RestAdapter.LogLevel logLevel) {
    return new RestAdapter.Builder()
      .setClient(client)
      .setEndpoint(endpoint)
      .setLogLevel(logLevel)
      .setConverter(new GsonConverter(
          getGsonBuilder()
            .registerTypeAdapter(ClippingDto.ClippingType.class, new ClippingTypeDeserializer())
            .registerTypeAdapter(SmsMessageDto.State.class, new SmsMessageStateSerializer())
            .registerTypeAdapter(SmsMessageDto.Type.class, new SmsMessageTypeSerializer())
            .registerTypeAdapter(PhoneCallDto.State.class, new PhoneCallStateSerializer())
            .registerTypeAdapter(PhoneCallDto.Type.class, new PhoneCallTypeSerializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        )
      )
      .setRequestInterceptor(apiHeaders)
      .build();
  }

  @SuppressWarnings("SpellCheckingInspection")
  protected GsonBuilder getGsonBuilder() {
    return new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  }
}
