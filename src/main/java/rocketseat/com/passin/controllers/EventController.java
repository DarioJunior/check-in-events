package rocketseat.com.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
//import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.services.EventService;

@RestController
@RequestMapping("/events")
//@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO event = this.service.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(
        @RequestBody EventRequestDTO body,
        UriComponentsBuilder uriComponentsBuilder
    ) {
        EventIdDTO eventIdDTO = this.service.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.EventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }
}
