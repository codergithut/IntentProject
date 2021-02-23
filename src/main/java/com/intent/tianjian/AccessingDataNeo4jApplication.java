package com.intent.tianjian;


import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRepository;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableNeo4jRepositories
public class AccessingDataNeo4jApplication {

    private final static Logger log = LoggerFactory.getLogger(AccessingDataNeo4jApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AccessingDataNeo4jApplication.class, args);
//        System.exit(0);
    }

//    @Bean
//    CommandLineRunner demo(PersonRepository personRepository, ProductRepository productRepository,
//                           ComponentRepository componentRepository) {
//        return args -> {
//
//            productRepository.deleteAll();
//            componentRepository.deleteAll();
//            personRepository.deleteAll();
//
//
//
//            Component huohuasai = new Component();
//            huohuasai.setFixedCost(10);
//            huohuasai.setTotalCost(30);
//            huohuasai.setName("火花塞");
//
//            Component dahuoshi = new Component();
//            dahuoshi.setName("打火石");
//            dahuoshi.setTotalCost(1);
//            dahuoshi.setFixedCost(1);
//            huohuasai.addComponent(dahuoshi);
//
//            Component taozhi = new Component();
//            taozhi.setName("套子");
//            taozhi.setTotalCost(1);
//            taozhi.setFixedCost(1);
//            huohuasai.addComponent(taozhi);
//
//            Component chepi = new Component();
//            chepi.setFixedCost(2);
//            chepi.setTotalCost(3);
//            chepi.setName("车大皮");
//
//            Component lungu = new Component();
//            lungu.setName("轮毂");
//            lungu.setFixedCost(1);
//            lungu.setTotalCost(2);
//
//            Component lunzi = new Component();
//            lunzi.setName("轮子");
//            lunzi.setFixedCost(10);
//            lunzi.setTotalCost(20);
//            lunzi.addComponent(chepi);
//            lunzi.addComponent(lungu);
//
//            Set<Component> componentSet = new HashSet<>();
//            componentSet.add(huohuasai);
//            componentSet.add(lunzi);
//
//            Product product = new Product();
//            product.setFixedCost(110);
//            product.setName("机器人");
//            product.setTotalCost(220);
//            product.setComponents(componentSet);
//
//            productRepository.save(product);
//
//            product = productRepository.findByName("机器人");
//
//            product.countTotalCost();
//
//            productRepository.save(product);
//
//
//
//            Person greg = new Person("Greg");
//            Person roy = new Person("Roy");
//            Person craig = new Person("Craig");
//
//            List<Person> team = Arrays.asList(greg, roy, craig);
//
//            log.info("Before linking up with Neo4j...");
//
//            team.stream().forEach(person -> log.info("\t" + person.toString()));
//
//            personRepository.save(greg);
//            personRepository.save(roy);
//            personRepository.save(craig);
//
//            greg = personRepository.findByName(greg.getName());
//            greg.worksWith(roy);
//            greg.worksWith(craig);
//            personRepository.save(greg);
//
//            roy = personRepository.findByName(roy.getName());
//            roy.worksWith(craig);
//            // We already know that roy works with greg
//            personRepository.save(roy);
//
//            // We already know craig works with roy and greg
//
//            log.info("Lookup each person by name...");
//            team.stream().forEach(person -> log.info(
//                    "\t" + personRepository.findByName(person.getName()).toString()));
//
//            List<Person> teammates = personRepository.findByTeammatesName(greg.getName());
//            log.info("The following have Greg as a teammate...");
//            teammates.stream().forEach(person -> log.info("\t" + person.getName()));
//        };
//    }



}
