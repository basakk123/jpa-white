package site.metacoding.white.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
class Dog {
    private Integer id;
    private String name;
}

@ToString
@AllArgsConstructor
@Setter
@Getter
class DogDto {
    private Integer id;
    private String name;

    public DogDto(Dog dog) {
        this.id = dog.getId();
        this.name = dog.getName();
    }

}

public class MapperTest2 {

    @Test
    public void convert_test() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog(1, "강아지"));
        dogList.add(new Dog(2, "고양이"));

        // dogList => List<DogDto>

        List<DogDto> dogDtoList = new ArrayList<>();
        for (Dog dog : dogList) {
            dogDtoList.add(new DogDto(dog));
        }
        System.out.println(dogDtoList.get(0).getName());
        System.out.println(dogDtoList.get(1).getName());
        System.out.println(dogDtoList);
    }

    @Test
    public void convert_test2() {
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog(1, "강아지"));
        dogList.add(new Dog(2, "고양이"));

        // List<Dog> -> List<DogDto>
        List<DogDto> dogDtoList = dogList.stream()
                .map((dog) -> new DogDto(dog))
                .collect(Collectors.toList());

        System.out.println(dogDtoList);
    }

}
