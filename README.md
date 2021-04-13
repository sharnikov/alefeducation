#Alef Education Test Task

https://www.alefeducation.com/

Tech Test
The tech test is a programming exercise to evaluate the candidate technical skills and
approach to software development. The candidate is expected to produce a solution in code
that demonstrates they understand how to solve the problem using clear concise code.
When completing the technical test please do:
● Include instructions on how to run the code
● Keep it simple
● Include instructions on how to run the code
● Return the (zipped) solution within 72 hours by email to
technicaltest@alefeducation.com (or a link to a google drive folder with the zipped
solution)
● Ask any questions by emailing the above address
● Use any programming language to solve the problem
Please don’t:
● Build a UI - the focus of the test is on the technical solution and not the interface
● Spend more than 2 hours - the important thing is that we understand your approach
and way of thinking, it’s enough to demonstrate you could complete the task if you
spent more time
● Feel the need to cover every edge case
● Write more than 200-300 lines of code ! it’s possible to produce quite lean solutions
The Oyster Card Problem
You are required to model the following fare card system which is a limited version of
London’s Oyster card system. At the end of the test, you should be able to demonstrate a
user loading a card with £30, and taking the following trips, and then viewing the balance.
- Tube Holborn to Earl’s Court
- 328 bus from Earl’s Court to Chelsea
- Tube Earl’s court to Hammersmith
  Operation
  When the user passes through the inward barrier at the station, their oyster card is charged
  the maximum fare.
  When they pass out of the barrier at the exit station, the fare is calculated and the maximum
  fare transaction removed and replaced with the real transaction (in this way, if the user
  doesn’t swipe out, they are charged the maximum fare).
  All bus journeys are charged at the same price.
  The system should favour the customer where more than one fare is possible for a given
  journey. E.g. Holburn to Earl’s Court is charged at £2.50.
  For the purposes of this test use the following data:
  Stations and zones:
  Station Zone(s)
  Holborn 1
  Earl’s Court 1, 2
  Wimbledon 3
  Hammersmith 2
  Fares:
  Journey Fare
  Anywhere in Zone 1 £2.50
  Any one zone outside zone 1 £2.00
  Any two zones including zone 1 £3.00
  Any two zones excluding zone 1 £2.25
  Any three zones £3.20
  Any bus journey £1.80
  The maximum possible fare is therefore £3.20.
  Assessment Criteria
  Points you will be assessed on:
  ● Following the Operational requirements
  ● A working solution which meets the requirements.
  ● Testing methods and coverage
  ● Design, Approach and Elegance of Solution.

#Remarks to the solution

## Comments
In src folder you can find my solution of the problem. It contains several raw implemented services. You can find a "Database" which could be a real DB in a real system, "Trips service" which supposed to be some kind of Facade with a cache or a in-memory BD instead of a hashmap and a "Calculation service"

It took about 3 hours to write this solution. It works fine but violates picking the cheapest way to reach a station in a case of several possible ways  

## Instruction
In order to implement everything as simple as possible, I haven't added even command line arguments. This way if you want to run the program, use `oyster.card.task.Starter` object. You can find all services being initialized there with several println-s which just show the logic of interaction with the "system"

