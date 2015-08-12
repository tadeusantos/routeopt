package org.tadeusantos.routeopt.repositories.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.tadeusantos.routeopt.domain.Subroute;
import org.tadeusantos.routeopt.repositories.SubrouteRepository;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * Spring configuration setting for Repositories module.
 * 
 * Repositories, JPA and Mongo are set here.
 * 
 * @author Tadeu Santos
 *
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = SubrouteRepository.class)
@ComponentScan(basePackageClasses = Subroute.class)
@PropertySource("classpath:mongodb.properties")
public class RepositoriesConfiguration extends AbstractMongoConfiguration {
	private static final Logger logger = 
				LoggerFactory.getLogger(RepositoriesConfiguration.class);
	
	@Resource
	private Environment env;

	@Override
	protected String getDatabaseName() {
		String database = env.getProperty("database");

		if (StringUtils.isEmpty(database)) {
			database = "routeopt_tst";
		}

		return database;
	}

	@Override
	@Bean
	public MongoClient mongo() throws Exception {
		String[] addresses = env.getProperty("addresses").split(",");
		MongoClient client = null;

		if (addresses.length > 0) {
			List<ServerAddress> serverAddresses = getAddressList(addresses);
			
			return new MongoClient(serverAddresses);
		} else {
			client = new MongoClient("localhost");
		}

		client.setWriteConcern(WriteConcern.SAFE);
		return client;
	}

	@Override
	protected String getMappingBasePackage() {
		return "org.tadeusantos.routeopt.domain";
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), getDatabaseName());
	}
	
	private List<ServerAddress> getAddressList(String[] addresses) {
		List<ServerAddress> serverAddresses = new ArrayList<>();

		for (String address : addresses) {
			String[] addressTokens = address.split(":");
			String host = "localhost";
			int port = 27017;

			if (addressTokens.length > 1) {
				host = addressTokens[0];
				if (addressTokens.length == 2) {
					try {
						port = Integer.parseInt(addressTokens[1]);
					} 
					catch (NumberFormatException e) {
						logger.warn(e.getMessage(), e);
					}
				}
			}
			
			serverAddresses.add(new ServerAddress(host, port));
		}
		return serverAddresses;
	}

}
