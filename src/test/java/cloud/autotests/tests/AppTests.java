package cloud.autotests.tests;

import static cloud.autotests.helpers.DriverHelper.getConsoleLogs;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Description;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class AppTests extends TestBase {

  @Test
  @Description("Soon to be implemented by QA.GURU engineers")
  @DisplayName("The fastest path should be displayed first")
  void generatedTest() {
    step("Open page", () -> open("https://qa-metro.stand-1.praktikum-services.ru"));

    step("Select city \"Москва\"", () -> {
      $(".select_metro__tick").click();
      $$(".select_metro__text").findBy(text("Москва")).click();
    });

    step("Type to field From \"Пятницкое шоссе\"", () -> {
      $(".from-to-suggest__from-field input.y-input_islet__control").setValue("Пятницкое шоссе");
      $(".y-suggest-drop_islet__items").$(byText("Пятницкое шоссе")).click();
    });

    step("Type to field To \"Шипиловская\"", () -> {
      $(".from-to-suggest__to-field input.y-input_islet__control").setValue("Шипиловская");
      $$(".y-suggest-drop_islet__items").get(1).$(byText("Шипиловская")).click();
    });

    step("Verify that the fastest path display first", () -> {
      ElementsCollection routeElements = $$(".route-list .route-list-item__time").snapshot();
      ArrayList<Integer> timeValues = new ArrayList<>();
      routeElements.forEach(item -> {
        String timeFromWebElement = item.text().substring(1);
        timeValues.add(countTime(timeFromWebElement));
      });
      assertEquals(Collections.min(timeValues), timeValues.get(0));
    });
  }

  @Test
  @Description("Autogenerated, because test steps contain text 'http...' and the page has 'title' tag")
  @DisplayName("Page title should have header text")
  void titleTest() {
    step("Open url 'https://qa-metro.stand-1.praktikum-services.ru'", () ->
        open("https://qa-metro.stand-1.praktikum-services.ru"));

    step("Page title should have text 'Схема метро Москвы — Яндекс.Метро'", () -> {
      String expectedTitle = "Схема метро Москвы — Яндекс.Метро";
      String actualTitle = title();

      assertThat(actualTitle).isEqualTo(expectedTitle);
    });
  }

  @Test
  @Description("Autogenerated, because test steps contain text 'http...'")
  @DisplayName("Page console log should not have errors")
  void consoleShouldNotHaveErrorsTest() {
    step("Open url 'https://qa-metro.stand-1.praktikum-services.ru'", () ->
        open("https://qa-metro.stand-1.praktikum-services.ru"));

    step("Console logs should not contain text 'SEVERE'", () -> {
      String consoleLogs = getConsoleLogs();
      String errorText = "SEVERE";

      assertThat(consoleLogs).doesNotContain(errorText);
    });
  }

  private Integer countTime(String timeFromWebElement) {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(timeFromWebElement);
    int hours = 0;
    int minutes = 0;
    long matcherCount = matcher.results().count();
    matcher.reset();
    assertTrue(matcherCount > 0);
    if (matcherCount > 1 && matcher.find()) {
      hours = Integer.parseInt(matcher.group()) * 60;
    }
    if (matcher.find()) {
      minutes = Integer.parseInt(matcher.group());
    }
    return (hours + minutes);
  }
}