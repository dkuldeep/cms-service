package jsonbeautify.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblTopic")
public class Topic {

  @Id @GeneratedValue @Column private int id;

  @Column(unique = true)
  private String name;

  @Column(unique = true)
  private String slug;

  @Column private boolean active;

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "Topic{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", slug='"
        + slug
        + '\''
        + ", active="
        + active
        + '}';
  }
}
