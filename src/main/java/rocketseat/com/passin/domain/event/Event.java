package rocketseat.com.passin.domain.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, name="maximum_attendees")
    private Integer maximumAttendees;

    public Event() {
    }

    public Event(String id, String title, String details, String slug, Integer maximumAttendees) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.slug = slug;
        this.maximumAttendees = maximumAttendees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getMaximumAttendees() {
        return maximumAttendees;
    }

    public void setMaximumAttendees(Integer maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
    }
}