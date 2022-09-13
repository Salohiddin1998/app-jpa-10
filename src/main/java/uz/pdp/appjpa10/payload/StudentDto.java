package uz.pdp.appjpa10.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String firstName;
    private String lastName;
    private String district;
    private String city;
    private String street;
    private Integer groupId;
    private List<Integer> subjects;
}
