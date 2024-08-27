import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDataProcessor {
    private List<User> users;

    public UserDataProcessor(List<User> users) {
        this.users = users;
    }

    public List<User> filterByAge(int minAge) {
        return users.stream()
                .filter(user -> user.getAge() >= minAge)
                .collect(Collectors.toList());
    }

    public void sortByName() {
        users.sort((u1, u2) -> u1.getName().compareTo(u2.getName()));
    }

    public List<User> getUsers() {
        return users;
    }

    public void printUsers() {
        users.forEach(System.out::println);
    }
}
