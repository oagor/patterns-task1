package ru.netology.delivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

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
        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id='city'] input")
                .setValue(validUser.getCity());
        setDate(firstMeetingDate);
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
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));

        setDate(secondMeetingDate);
        $$("button")
                .findBy(text("Запланировать"))
                .click();

        $("[data-test-id='replan-notification']")
                .shouldBe(visible);

        $$("button")
                .findBy(text("Перепланировать"))
                .click();

        $("[data-test-id='success-notification']")
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }

    private void setDate(String date) {
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateInput.sendKeys(date);
    }
}