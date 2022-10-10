package jsonbeautify.controller;

import jsonbeautify.TopicEnum;
import jsonbeautify.dto.TopicDto;
import jsonbeautify.entity.Topic;
import jsonbeautify.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {
  private final TopicService topicService;

  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @GetMapping("")
  public List<TopicDto> findAll() {
    return Arrays.stream(TopicEnum.values()).map(TopicEnum::toTopicDto).collect(Collectors.toList());
  }

  @PostMapping("")
  public Topic addTopic(@RequestBody Topic topic) {
    if (Objects.nonNull(topic) && !"".equals(topic.getName())) {
      String slug = topic.getSlug().trim().replaceAll(" ", "-").toLowerCase();
      topic.setSlug(slug);
      return topicService.save(topic);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad payload");
    }
  }

  @GetMapping("/{id}")
  public Topic getTopic(@PathVariable int id) {
    Optional<Topic> optional = topicService.findById(id);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
    }
  }

  @PutMapping("/{id}")
  public Topic updateTopic(@RequestBody Topic topic, @PathVariable int id) {
    Optional<Topic> optional = topicService.findById(id);
    if (optional.isPresent()) {
      Topic topic1 = optional.get();
      topic1.setActive(topic.isActive());
      topic1.setSlug(topic.getSlug());
      topic1.setName(topic.getName());
      return topicService.save(topic1);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic not found with id: " + id);
    }
  }
}
