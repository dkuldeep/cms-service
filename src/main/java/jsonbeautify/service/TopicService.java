package jsonbeautify.service;

import jsonbeautify.model.Topic;
import jsonbeautify.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

  private final TopicRepository topicRepository;

  public TopicService(TopicRepository topicRepository) {
    this.topicRepository = topicRepository;
  }

  public void deleteById(int id) {
    topicRepository.deleteById(id);
  }

  public Topic save(Topic topic) {
    return topicRepository.save(topic);
  }

  public Optional<Topic> findById(int id) {
    return topicRepository.findById(id);
  }

  public Iterable<Topic> findAll() {
    return topicRepository.findAll();
  }
}
