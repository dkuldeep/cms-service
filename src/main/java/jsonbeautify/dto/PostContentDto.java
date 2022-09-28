package jsonbeautify.dto;

public class PostContentDto {
  private String content;

  public PostContentDto() {
  }

  public PostContentDto(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
