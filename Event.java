package empty;
import java.util.ArrayList;
import java.util.List;

class Event {
    private String eventId;
    private String name;
    private String date;
    private String venue;
    private int capacity;

    public Event(String eventId, String name, String date, String venue, int capacity) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
    }

    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Attendee {
    private String attendeeId;
    private String name;
    private String email;
    private String ticketType;
    private boolean checkInStatus;

    public Attendee(String attendeeId, String name, String email, String ticketType) {
        this.attendeeId = attendeeId;
        this.name = name;
        this.email = email;
        this.ticketType = ticketType;
        this.checkInStatus = false;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTicketType() {
        return ticketType;
    }

    public boolean isCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(boolean checkInStatus) {
        this.checkInStatus = checkInStatus;
    }
}

class Ticket {
    protected String ticketId;
    protected String eventId;
    protected String type;
    protected double price;
    protected String state;

    public Ticket(String ticketId, String eventId, String type, double price) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.type = type;
        this.price = price;
        this.state = "available";
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double price() {
        return price;
    }

    public void printBadge() {
        System.out.println("Ticket ID: " + ticketId + ", Type: " + type);
    }
}

class VIPTicket extends Ticket {
    public VIPTicket(String ticketId, String eventId, double price) {
        super(ticketId, eventId, "VIP", price);
    }

    @Override
    public double price() {
        return price * 1.5;
    }

    @Override
    public void printBadge() {
        System.out.println("VIP Ticket ID: " + ticketId);
    }
}

class RegularTicket extends Ticket {
    public RegularTicket(String ticketId, String eventId, double price) {
        super(ticketId, eventId, "Regular", price);
    }

    @Override
    public void printBadge() {
        System.out.println("Regular Ticket ID: " + ticketId);
    }
}

class EventService {
    private List<Event> events = new ArrayList<>();
    private List<Attendee> attendees = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public void createEvent(String eventId, String name, String date, String venue, int capacity) {
        events.add(new Event(eventId, name, date, venue, capacity));
    }

    public void sellTicket(String ticketId, String eventId, String type, double price) {
        if (type.equalsIgnoreCase("VIP")) {
            tickets.add(new VIPTicket(ticketId, eventId, price));
        } else {
            tickets.add(new RegularTicket(ticketId, eventId, price));
        }
    }

    public boolean rsvp(String attendeeId, String name, String email, String ticketType) {
        for (Ticket ticket : tickets) {
            if (ticket.getType().equalsIgnoreCase(ticketType) && ticket.getState().equals("available")) {
                attendees.add(new Attendee(attendeeId, name, email, ticketType));
                ticket.setState("sold");
                return true;
            }
        }
        return false;
    }

    public boolean rsvp(String attendeeId, String name, String email) {
        return rsvp(attendeeId, name, email, "Regular");
    }

    public boolean checkIn(String attendeeId) {
        for (Attendee attendee : attendees) {
            if (attendee.getAttendeeId().equals(attendeeId)) {
                attendee.setCheckInStatus(true);
                return true;
            }
        }
        return false;
    }

    public List<Attendee> attendeeList() {
        return attendees;
    }

    public double computeRevenue() {
        double revenue = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getState().equals("sold")) {
                revenue += ticket.price();
            }
        }
        return revenue;
    }
}
public class EventAppMain {
    public static void main(String[] args) {
        EventService eventService = new EventService();

        eventService.createEvent("E01", "Tech Conference", "2025-10-01", "Auditorium", 100);
        eventService.createEvent("E02", "Music Concert", "2025-10-05", "Stadium", 200);

        eventService.sellTicket("T01", "E01", "VIP", 150);
        eventService.sellTicket("T02", "E01", "Regular", 75);
        eventService.sellTicket("T03", "E02", "VIP", 120);
        eventService.sellTicket("T04", "E02", "Regular", 60);

        eventService.rsvp("A01", "Alice", "alice@example.com", "VIP");
        eventService.rsvp("A02", "Bob", "bob@example.com");
        eventService.checkIn("A01");

        for (Attendee attendee : eventService.attendeeList()) {
            System.out.println("Attendee: " + attendee.getName() + ", Email: " + attendee.getEmail() + ", Checked In: " + attendee.isCheckInStatus());
        }

        System.out.println("Total Revenue: " + eventService.computeRevenue());
    }
}

