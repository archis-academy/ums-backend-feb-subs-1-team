package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.person.PersonRepository;

public class Main {
    public static void main(String[] args) {
        DataBaseConnectorConfig.setConnection();
        PersonRepository.createPersonTable();
    }
}