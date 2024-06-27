package org.example.less_3.opt;

import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionalMain {

    private static class UserNotFoundException extends RuntimeException {

    }

    @Getter
    private static class User {

        private Long id;
        private String email;

        public User(Long id, String email) {
            this.id = id;
            this.email = email;
        }

        public Optional<String> getPassword() {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        // Optional<T> - класс-контейнер, в котором либо есть значение, либо нет

        Optional<User> userOpt = Stream.of(
                        new User(1L, "user@mail.com"),
                        new User(2L, "user2@mail.com"),
                        new User(3L, "user_unnamed@mail.com"),
                        new User(4L, "user@gmail.com"))
                .filter(it -> it.getId() == 2L)
                .findFirst();

//    if (userOpt.isPresent()) {
//      String email = userOpt.get().getEmail();
//      sendEmail(email);
//    } else {
//      throw new NoSuchElementException("Юзер не найден");
//    }

        String userEmail = userOpt.map(User::getEmail) // Optional<String>
                .filter(it -> !it.isBlank()) // Optional<String>
                .orElseThrow(UserNotFoundException::new); // .get()

        User user = userOpt
                .orElseGet(() -> new User(-1L, "unknown@email.com"));

//    Optional<User> user1 = userOpt.filter(it -> it.getEmail() != null);

        Optional<User> adsfsadf = userOpt.or(() -> Optional.of(new User(1L, "adsfsadf")));

        Optional<String> s = userOpt.flatMap(User::getPassword);

        // return userRepository.findById(1L) // Optional<User>
        //    .or(this::findDefaultUser);
        // findDefaultUser -> Optional<User>

//    itemOpt.ifPresentOrElse(
//      it -> System.out.println("Найден объект: " + it),
//      () -> System.out.println("Объект не найден")
//    );


        // nullable, callable, runnable

        Optional<User> opt1 = Optional.of(new User(1L, "notempty")); // нельзя null
        Optional<User> opt2 = Optional.ofNullable(null); // можно null

        Optional<User> empty = Optional.empty();


        // Person -> Department -> head Person -> boss Person -> Address -> toString()
        // Optional.ofNullable(person)
        //   .map(Person::getDepartment)
        //   .map(Department::getHead)
        //   .map(Person::getBoss)
        //   .map(Person::getAddress)
        //   .map(Object::toString)
        //   .orElseThrow(() -> new IllegalStateException("Не удалось найти адрес начальства"))


    }

    private static void sendEmail(String address) {
        // ... 200 строк кода, которые посылают емейл на адрес


        // ... 200 строк кода, которые посылают емейл на адрес
    }


}

