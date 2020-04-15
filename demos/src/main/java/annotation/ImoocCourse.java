package annotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@CourseInfoAnnotation(name = "Java Review")
public class ImoocCourse {
    @PersonInfoAnnotation(name = "Xiangzai", age = 19)
    private String author;
}
