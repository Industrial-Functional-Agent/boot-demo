/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    // @Autowired
    // private StringRedisTemplate template;

    public static void main(String[] args) {
        // Close the context so it doesn't stay awake listening for redis
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo2() {
        Arrays.stream(Thread.currentThread().getStackTrace()).forEach(a -> {
            log.info("current stacktrace = {}", a);
        });
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            Arrays.stream(Thread.currentThread().getStackTrace()).forEach(a -> {
                log.info("current stacktrace = {}", a);
            });
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L)
                .ifPresent(customer -> {
                    log.info("Customer found with findById(1L):");
                    log.info("--------------------------------");
                    log.info(customer.toString());
                    log.info("");
                });

            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (Customer bauer : repository.findByLastName("Bauer")) {
            //  log.info(bauer.toString());
            // }
            log.info("");
        };
    }

    // @Override
    // public void run(String... args) throws Exception {
    //     ValueOperations<String, String> ops = this.template.opsForValue();
    //     String key = "spring.boot.redis.test";
    //     if (!this.template.hasKey(key)) {
    //         ops.set(key, "foo");
    //     }
    //     System.out.println("Found key " + key + ", value=" + ops.get(key));
    // }
}
