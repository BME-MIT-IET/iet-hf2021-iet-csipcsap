package hu.bme.aut.freelancer_spring.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {

    @Mock
    public EmailValidator emailValidator;


    @Test
    void checkValidEmailAddress() {
        //Arrange
        List<String> emails = Stream.of("mukodoprobaegy@gmail.com",
                                      "nagyBetusProba@gmail.com",
                                      "EgyNemGmailesProba@citromail.hu",
                                      "szamokatTartalmazoEmail99@hotmail.com")
                                    .collect(Collectors.toList());
        emailValidator = new EmailValidator();

        // Act
        List<Boolean> resultsInOrder= emails.stream().map(email -> emailValidator.isValid(email, null)).collect(Collectors.toList());

        //Assert
        resultsInOrder.forEach(Assertions::assertTrue);

    }

    @Test
    void checkInvalidEmailAddress() {
        //Arrange
        List<String> emails = Stream.of("@//%!=''@gmail.com",
                "domainnelkulmegadottemailcim",
                "@.com",
                "@geg.hu",
                "csakazonosito.email",
                "csakazonosito@esdomain",
                "        ez@       gmail.com")
                .collect(Collectors.toList());
        emailValidator = new EmailValidator();

        // Act
        List<Boolean> resultsInOrder= emails.stream().map(email -> emailValidator.isValid(email, null)).collect(Collectors.toList());

        //Assert
        resultsInOrder.forEach(Assertions::assertFalse);
    }
}
