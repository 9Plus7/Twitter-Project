# Twitter-Project

Designs Twitter feed API that allows users to view timelines, like/comment/share tweets with friends and communicate with others.

##  Use Cases and Constraints Outline

- User posts a tweet
- Service pushes tweets to followers, sending push notifications and emails
- User views the user timeline (activity from the user)
- User views the home timeline (activity from people the user is following)
- User searches keywords
- Service has high availability

# API Requests

To make a REST API request, combine the HTTP GET, POST, PUT, PATCH, or DELETE method, the URL to the API service, 
the URI to a resource to query, submit data to, update, or delete, and one or more HTTP request headers.

# Query parameter 

## Tweet Look Up

### GET /tweets request parameters
attachments.poll_ids
attachments.media_keys
author_id
entities.mentions.username
geo.place_id
in_reply_to_user_id
referenced_tweets.id
referenced_tweets.id.author_id


### Sending Tweets With API
When creating a new Tweet with endpoint, text or media for the Tweet are the required body parameters.

Key	Value	Parameter type
text	Hello world! 	body

{
  "data": {
    "id": "1445880548472328192",
    "text": "Hello world!"
  }
}
-If the returned response object contains an id and the text of the Tweet, then successfully created a Tweet.

### Delete tweet

{
   "data": {
       "deleted" : true
   }
}


## Timeline 

- Tweets are delivered in reverse-chronological order, starting with the most recent. Results are paginated up to 100 Tweets per page. Pagination tokens are provided for paging through large sets of Tweets. The Tweet IDs of the newest and the oldest Tweets included in the given page are also provided as metadata, which can also be used for polling timelines for recent Tweets, or for navigating through the timeline similar to the v1.1 user_timeline endpoints. The user Tweet timeline also supports the ability to specify start_time and end_time parameters to receive Tweets that were created within a certain window of time. 


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
 







