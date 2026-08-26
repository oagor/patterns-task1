package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

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

        var firstMeetingDate = DataGenerator.generateDate(4);
        var firstMeetingDateForInput = DataGenerator.generateDateForInput(4);

        var secondMeetingDate = DataGenerator.generateDate(7);
        var secondMeetingDateForInput = DataGenerator.generateDateForInput(7);

        System.out.println("ПЕРВАЯ ДАТА ДЛЯ ПРОВЕРКИ: " + firstMeetingDate);
        System.out.println("ПЕРВАЯ ДАТА ДЛЯ INPUT: " + firstMeetingDateForInput);
        System.out.println("ВТОРАЯ ДАТА ДЛЯ ПРОВЕРКИ: " + secondMeetingDate);
        System.out.println("ВТОРАЯ ДАТА ДЛЯ INPUT: " + secondMeetingDateForInput);

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
                .findBy(Condition.text("Запланировать"))
                .click();

        var notification = $("[data-test-id='success-notification']")
                .shouldBe(visible);

        System.out.println("ФАКТИЧЕСКИЙ ТЕКСТ: " + notification.getText());

        $("[data-test-id='date'] input")
                .clear();

        $("[data-test-id='date'] input")
                .setValue(secondMeetingDateForInput);

        $$("button")
                .findBy(Condition.text("Запланировать"))
                .click();

        $("[data-test-id='replan-notification']")
                .shouldBe(visible);

        $$("button")
                .findBy(Condition.text("Перепланировать"))
                .click();

        var secondNotification = $("[data-test-id='success-notification']")
                .shouldBe(visible);

        System.out.println("ФАКТИЧЕСКИЙ ТЕКСТ ПОСЛЕ ПЕРЕПЛАНИРОВАНИЯ: "
                + secondNotification.getText());
    }
}