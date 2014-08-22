package com.example.helloworld.authentication;

import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

public class ServerRestrictedToProvider implements InjectableProvider<RestrictedTo, Parameter> {

    private final UserAuthenticator userRoleAuthenticator;
    private final String realm;

    public ServerRestrictedToProvider(UserAuthenticator userRoleAuthenticator, String realm) {
        this.userRoleAuthenticator = userRoleAuthenticator;
        this.realm = realm;
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    public Injectable getInjectable(ComponentContext ic, RestrictedTo restrictedTo, Parameter parameter) {
        return new ServerRestrictedToInjector(userRoleAuthenticator, realm, restrictedTo.value());
    }
}
