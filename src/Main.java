import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //1. Количество несовершеннолетних - люди младше 18 лет
        int minorsCount = (int) persons.stream().filter(person -> person.getAge() < 18).count();

        //2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> recruits = persons
                .stream()
                .filter(person -> person.getAge() > 17 && person.getAge() < 28 && person.getSex() == Sex.MAN)
                .map(person -> person.getFamily()).collect(Collectors.toList());

        //3. Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        //люди с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        Collection<Person> workable = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() > 17 && person.getAge() < 66 && person.getSex() == Sex.MAN || person.getAge() > 17 && person.getAge() < 61 && person.getSex() == Sex.WOMAN)
                .sorted(Comparator.comparing(person -> person.getFamily())).collect(Collectors.toList());
    }
}