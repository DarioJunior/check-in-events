package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeDetails;
import rocketseat.com.passin.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.repositories.AttendeeRepository;
import rocketseat.com.passin.repositories.CheckinRepository;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
        return attendeeList;
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeesList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeesList.stream().map(
            attendee -> {
                Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
                //LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
                LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
                return new AttendeeDetails(
                    attendee.getId(),
                    attendee.getName(),
                    attendee.getEmail(),
                    attendee.getCreatedAt(),
                    checkedInAt
                );
            }
        ).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }
}
