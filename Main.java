import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = new ArrayList<>();

        // Загрузка данных из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    String city = parts[2].trim();
                    users.add(new User(name, age, city));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        UserDataProcessor processor = new UserDataProcessor(users);

        // Отображение данных
        System.out.println("Данные из файла:");
        processor.printUsers();

        // Фильтрация данных по возрасту
        System.out.print("\nВведите минимальный возраст для фильтрации: ");
        int minAge = scanner.nextInt();
        scanner.nextLine(); // Для захвата новой строки
        List<User> filteredUsers = processor.filterByAge(minAge);

        // Сортировка по имени
        processor = new UserDataProcessor(filteredUsers);
        processor.sortByName();

        System.out.println("\nОтфильтрованные и отсортированные данные:");
        processor.printUsers();

        // Сохранение данных в файл
        System.out.print("\nВведите имя файла для сохранения: ");
        String outputFileName = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (User user : processor.getUsers()) {
                writer.write(user.getName() + "," + user.getAge() + "," + user.getCity());
                writer.newLine();
            }
            System.out.println("Данные сохранены в файл: " + outputFileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }

        // Ввод новых данных пользователем
        System.out.print("\nХотите добавить нового пользователя? (да/нет): ");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("да")) {
            System.out.print("Введите имя нового пользователя: ");
            String name = scanner.nextLine();
            System.out.print("Введите возраст нового пользователя: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Для захвата новой строки
            System.out.print("Введите город нового пользователя: ");
            String city = scanner.nextLine();

            User newUser = new User(name, age, city);
            users.add(newUser);
            System.out.println("Новый пользователь добавлен.");
        }

        // Сохранение обновленных данных в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getName() + "," + user.getAge() + "," + user.getCity());
                writer.newLine();
            }
            System.out.println("Обновленные данные сохранены в файл: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }

        scanner.close();
    }
}
