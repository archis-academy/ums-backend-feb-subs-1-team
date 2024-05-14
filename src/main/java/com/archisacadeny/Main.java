package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.person.Person;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.person.PersonService;

public class Main {
    public static void main(String[] args) {
        DataBaseConnectorConfig.setConnection();

        Person person = new Person();
        person.setEmail("example@ggmail.com");
        person.setFullName("Muhammet gorkemli");
        person.setPassword("qwoiekasdhkjas");

        PersonService personService = new PersonService(new PersonRepository());
        Person personFromDb = personService.createPerson(person);

        System.out.println(personFromDb.getFullName());
    }
}
