# Twitter-Project

Designs Twitter feed API that allows users to view timelines, like/comment/share tweets with friends and communicate with others.

##  Use Cases and Constraints Outline

- User posts a tweet
- Service pushes tweets to followers, sending push notifications and emails
- User views the user timeline (activity from the user)
- User views the home timeline (activity from people the user is following)
- User searches keywords
- Service has high availability


## Constraints and assumptions

General

 - Traffic is not evenly distributed

 - Posting a tweet should be fast

 - Fanning out a tweet to all of the followers should be fast, unless user have millions of followers

 - 100 million active users

 - 500 million tweets per day or 15 billion tweets per month

 - Each tweet averages a fanout of 10 deliveries

 - 5 billion total tweets delivered on fanout per day

 - 150 billion tweets delivered on fanout per month

 - 250 billion read requests per month

 - 10 billion searches per month

Timeline

 - Viewing the timeline should be fast

 - Twitter is more read heavy than write heavy

 - Optimize for fast reads of tweets

 - Ingesting tweets is write heavy


Search

 - Searching should be fast

 - Search is read-heavy
 
## Usage Design

waiting to be edited.


## Tweet

 - Store the user's own tweets to populate the user timeline in database
 - Delivering tweets and building the home timeline
 - The Client posts a tweet to the Web Server
 - The Web Server forwards the request to the Write API server
 - The Write API stores the tweet in the user's timeline on a SQL databas
 
## Use case: User views the home timeline
- The Client posts a home timeline request to the Web Server
- The Web Server forwards the request to the Read API server

- The Read API server contacts the Timeline Service, which does the following:

  Gets the timeline data stored in the Memory Cache, containing tweet ids and user ids - O(1)
 
  Queries the Tweet Info Service with a multiget to obtain additional info about the tweet ids - O(n)
 
  Queries the User Info Service with a multiget to obtain additional info about the user ids - O(n)
  
## Use case: User views the user timeline

- The Client posts a user timeline request to the Web Server
- The Web Server forwards the request to the Read API server
- The Read API retrieves the user timeline from the SQL Database

## Use case: User searches keywords
- The Client sends a search request to the Web Server
- The Web Server forwards the request to the Search API server
- The Search API contacts the Search Service

## Scale:
waiting to be edied.

## Caching

## Asynchronism and microservices

## Security
 







