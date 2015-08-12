package org.tadeusantos.routeopt.services.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.tadeusantos.routeopt.services.impl.PointManagementServices;

/**
 * Spring configuration setting for Services module.
 * 
 * Spring IoC configuration is set here.
 * 
 * @author Tadeu Santos
 *
 */
@Configuration
@ComponentScan(basePackageClasses = PointManagementServices.class)
public class ServicesConfiguration {
}
