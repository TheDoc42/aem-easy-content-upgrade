/*
 *  Copyright 2018 Valtech GmbH
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>
 */
package de.valtech.aecu.core.groovy.console.bindings.hello;

import com.icfolson.aem.groovy.console.api.BindingExtensionProvider;
import groovy.lang.Binding;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Map;

/**
 * Provides additional AECU Bindings for the Groovy Console
 * @author Roxana Muresan
 */
@Component(immediate = true)
public class AECUBindingExtensionProvider implements BindingExtensionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(AECUBindingExtensionProvider.class);

    @Reference
    private BindingExtensionProvider defaultBindingExtensionProvider;


    @Activate
    public void activate(BundleContext bundleContext) {
        LOG.debug("defaultBindingExtensionProvider is " + defaultBindingExtensionProvider);
    }

    @Override
    public Binding getBinding(SlingHttpServletRequest request) {
        ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        Binding binding = defaultBindingExtensionProvider.getBinding(request);
        Map<String, Object> inheritedBindings =  binding.getVariables();
        inheritedBindings.put("helloWorld", new HelloWorld());

        return new Binding(inheritedBindings);
    }

}
