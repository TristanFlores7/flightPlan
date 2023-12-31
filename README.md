# graphsAndFlightPlans
This program determines the shortest time and/or lowest cost for a user to get from one destination to another given an airlines flight options.
The first input file details the available flight options, creating an undirected graph of cities and travel times/costs.
The format of the first input file is:
Austin|Boston|50|200
Boston|Dallas|50|100
Where the first two city names are a connections between two cities, the following integer is the connection cost, and the last number is the connection time value.
The second input file takes in user requests to determine the flight patht that gives the lowest cost or shortest time.
Austin|Dallas|T
Where the first city is the starting location, the second is the destination, and the last character is a specification that the user wants the path with the lowest amount of time.
The 3 cheapest/fastest costs/times are provided in the output.
A picture of the sample graph can be seen in the provided jpeg file.
