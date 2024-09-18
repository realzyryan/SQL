package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCode;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankTest {
    LoginPage loginPage;

    @AfterEach
    void tableСlearingAuthCode() {
        cleanAuthCode();
    }

    @AfterAll
    static void clearingAllTables() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldSuccesFullLogin() {
        var authInfo = DataHelper.getUser();
        var verificatinPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerifaicationCode();
        verificatinPage.validCode(verificationCode);
    }

    @Test
    void invalidUser() {
        var authInfo = DataHelper.getRandomUser();
        loginPage.invalidUser(authInfo);
        loginPage.error("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void invalidAuthCode() {
        var authInfo = DataHelper.getUser();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificatinCode = DataHelper.randomVerifai();
        verificationPage.virify(verificatinCode.getCode());
        verificationPage.errorMessage("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}