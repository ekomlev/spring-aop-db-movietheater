# Spring application for managing a movie theater
![badge-jdk-8]

## Description
Allows for admins to enter events, view purchased tickets, and for users to register, view events with air dates and times, get ticket price, buy tickets.

## Task to do
Services and their descriptions that the application should provide:

1. **`UserService`** manages registered users:
  * `save`, `remove`, `getById`, `getUserByEmail`, `getAll`
  
2. **`EventService`** manages events (movie shows). Event contains only basic information, like name, base price for tickets, rating (high, mid, low). Event can be presented on several dates and several times within each day. For each dateTime an Event will be presented only in single Auditorium.
   `save`, `remove`, `getById`, `getByName`, `getAll`;
   `getForDateRange(from, to)` returns events for specified date range (OPTIONAL)
   `getNextEvents(to)` returns events from now till the ‘to’ date (OPTIONAL)

3. **`AuditoriumService`** returns info about auditoriums and places. Since auditorium information is usually static, store it in some property file. The information that needs to be stored is:
   ```
    name
    number of seats
    vip seats (comma-separated list of expensive seats)
   ```   
   Several auditoriums can be stored in separate property files or in a single file, information from them should be injected into the AuditoriumService

   `getAll()`, `getByName()`
4. **`BookingService`** manages tickets, prices, bookings:

   `getTicketsPrice(event, dateTime, user, seats)` returns total price for buying all tickets for specified event on specific date and time for specified seats.
   * User is needed to calculate discount (see below)
   * Event is needed to get base price, rating
   * Vip seats should cost more than regular seats (For example, 2xBasePrice)
   * All prices for high rated movies should be higher (For example, 1.2xBasePrice)
  
   `bookTicket(tickets)` ticket should contain information about event, air dateTime, seat, and user. The user could be registered or not. If user is registered, then booking information is stored for that user (in the tickets collection). Purchased tickets for particular event should be stored.
   
   `getPurchasedTicketsForEvent(event, dateTime)` gets all purchased tickets for event for specific date and Time
5. **`DiscountService`** counts different discounts for purchased tickets

   `getDiscount(user, event, dateTime, numberOfTickets)` returns total discount (from 0 to 100) that can be applied to the user buying specified number of tickets for specific event and air dateTime
   
   `DiscountStrategy` single class with logic for calculating discount
   
   * Birthday strategy gives 5% if user has birthday within 5 days of air date
   
   * Every 10th ticket - give 50% for every 10th ticket purchased by user. This strategy can also be applied for not-registered users if 10 or more tickets are bought
   
   All discount strategies should be injected as list into the DiscountService. The getDiscount method will execute each strategy to get max available discount. Discounts should not add up. So, if one strategy returns 20% and another 30%, final discount would be 30%.
   
   Define DiscountService with all strategies as separate beans in separate XML configuration
   
**NOTE:** Skeleton project for Spring Core MOOC course is [here]

[here]: https://git.epam.com/yuriy_tkach/spring-core-hometask-skeleton/wikis/home
[badge-jdk-8]: https://img.shields.io/badge/jdk-8-yellow.svg "JDK-8"