package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class RentingService {
    
    Properties serviceProperties;
    
    public RentingService(User user) throws IOException, ClassNotFoundException {
        loadProperties();
        int userId = AuthenticationService.execute(user, serviceProperties);
        System.out.print(user);
    }
    
    private void loadProperties() {
        this.serviceProperties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(getAbsolutePropertyFilePath())) {
            serviceProperties.load(fileInputStream);
        } catch (Exception exception) {
            throw CustomRuntimeException.unableToLoadServiceProperties();
        }
    }

    abstract String getAbsolutePropertyFilePath();
}
