package com.omnipaste.omniapi.dto.resource.v1;

import com.omnipaste.omniapi.dto.IsMock;
import com.omnipaste.omniapi.dto.ObjectGraphTest;
import com.omnipaste.omniapi.resource.v1.AuthorizationResource;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import org.junit.Test;

import java.util.LinkedList;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationResourceTest extends ObjectGraphTest {
  public interface Resource {
  }

  public class ResourceImpl extends AuthorizationResource<Resource> {
    protected ResourceImpl(RestAdapter restAdapter, AuthorizationService authorizationService) {
      super(restAdapter, Resource.class, authorizationService);
    }
  }

  @Module(
      injects = AuthorizationResourceTest.class,
      library = true,
      complete = false
  )
  public class AuthorizationResourceTestModule {
    @Provides @IsMock
    public AuthorizationService provideAuthorizationService() {
      return mock(AuthorizationService.class);
    }
  }

  private ResourceImpl subject;

  @Inject public RestAdapter restAdapter;
  @Inject @IsMock public AuthorizationService authorizationService;

  @Override
  public void setUp() {
    LinkedList<Object> modules = new LinkedList<>();
    modules.add(new AuthorizationResourceTestModule());

    objectGraph = plus(modules);
  }

  @Test
  public void bearerAccessTokenWillPrefixWithBearer() throws Exception {
    subject = new ResourceImpl(restAdapter, authorizationService);
    when(authorizationService.getAccessTokenDto()).thenReturn(new AccessTokenDto("access", "refresh"));
    assertThat(subject.bearerAccessToken(), is("bearer access"));
  }
}
