public static String generateDate(int shift) {
    return LocalDate.now()
            .plusDays(shift)
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
}

public static String generateCity(Faker faker) {
    return faker.address().city();
}

public static String generateName(Faker faker) {
    return faker.name().fullName();
}

public static String generatePhone(Faker faker) {
    return faker.phoneNumber().phoneNumber();
}

public static class Registration {
    private static Faker faker;
    private Registration() {}

    public static UserInfo generateUser(String locale) {
        faker = new Faker(new Locale(locale));
        return new UserInfo(
                generateCity(faker),
                generateName(faker),
                generatePhone(faker)
        );
    }
}