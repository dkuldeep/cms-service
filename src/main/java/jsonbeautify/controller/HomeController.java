package jsonbeautify.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jsonbeautify.TagEnum;
import jsonbeautify.dto.TagDto;
import jsonbeautify.entity.ContactForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class HomeController {

  private static final Calendar calendar = Calendar.getInstance();
  private static final String FOLDER_NAME = "contact";

  public HomeController() {
    new File(FOLDER_NAME).mkdir();
  }

  @GetMapping("")
  public String homePage() {
    return "";
  }

  @PostMapping("/contact")
  public String contact(@RequestBody ContactForm form) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    File file = new File(FOLDER_NAME + "/" + Calendar.getInstance().getTimeInMillis() + ".json");
    try {
      objectMapper.writeValue(file, form);
      return "success";
    } catch (IOException e) {
      e.printStackTrace();
      return "error";
    }
  }

  @GetMapping("/tags")
  public List<TagDto> findAll() {
    return Arrays.stream(TagEnum.values()).map(TagEnum::toTagDto).collect(Collectors.toList());
  }

  public static void main(String[] args) {
    System.out.println(calendar.getTimeInMillis());
  }
}
