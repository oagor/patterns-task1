package ru.netology.delivery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        var firstMeetingDateForInput = LocalDate.now()
                .plusDays(daysToAddForFirstMeeting)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        var secondMeetingDateForInput = LocalDate.now()
                .plusDays(daysToAddForSecondMeeting)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Первое планирование
        $("[data-test-id='city'] input")
                .setValue(validUser.getCity());

        $("[data-test-id='date'] input")
                .setValue(firstMeetingDateForInput);

        $("[data-test-id='name'] input")
                .setValue(validUser.getName());

        $("[data-test-id='phone'] input")
                .setValue(validUser.getPhone());

        $("[data-test-id='agreement']")
                .click();

        $$("button")
                .findBy(text("Запланировать"))
                .click();

        $("[data-test-id='success-notification']")
                .shouldBe(visible);

        // Перепланирование
        $("[data-test-id='date'] input")
                .setValue(secondMeetingDateForInput);

        $$("button")
                .findBy(text("Запланировать"))
                .click();

        $("[data-test-id='replan-notification']")
                .shouldBe(visible);

        $$("button")
                .findBy(text("Перепланировать"))
                .click();

        $("[data-test-id='success-notification']")
                .shouldBe(visible);
    }
}