package com.omnipaste.omniapi.resource.v1;

import com.omnipaste.omniapi.InstrumentationTestCaseBase;
import com.omnipaste.omniapi.IsMock;
import com.omnipaste.omniapi.service.AuthorizationService;
import com.omnipaste.omnicommon.dto.AccessTokenDto;

import java.util.LinkedList;

import javax.inject.Inject;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationResourceTest extends InstrumentationTestCaseBase {
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
  public void setUp() throws Exception {
    super.setUp();

    LinkedList<Object> modules = new LinkedList<>();
    modules.add(new AuthorizationResourceTestModule());

    ObjectGraph testGraph = plus(modules);
    testGraph.inject(this);

    subject = new ResourceImpl(restAdapter, authorizationService);
  }

  public void testBearerAccessTokenWillPrefixWithBearer() throws Exception {
    when(authorizationService.getAccessTokenDto()).thenReturn(new AccessTokenDto("access", "refresh"));
    assertThat(subject.bearerAccessToken(), is("bearer access"));
  }
}
