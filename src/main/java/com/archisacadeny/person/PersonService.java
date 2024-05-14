package com.archisacadeny.person;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(Person person){
        return personRepository.save(person);
    }
}
