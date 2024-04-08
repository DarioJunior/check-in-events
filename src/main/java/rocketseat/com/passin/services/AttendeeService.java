package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeDTO;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
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
                    // LocalDateTime checkedInAt = checkIn.isPresent() ?
                    // checkIn.get().getCreatedAt() : null;
                    LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
                    return new AttendeeDetails(
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getCreatedAt(),
                            checkedInAt);
                }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent())
            throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeeRepository.findById(attendeeId)
                                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in")
                    .buildAndExpand(attendee.getId())
                    .toUri()
                    .toString();

        AttendeeBadgeDTO AttendeeBadgeDTO = new AttendeeBadgeDTO(
            attendee.getName(),
            attendee.getEmail(),
            uri,
            attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(AttendeeBadgeDTO);
                
    }
}
