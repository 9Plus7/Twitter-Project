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

### State assumptions

General

 -Traffic is not evenly distributed

 -Posting a tweet should be fast

 -Fanning out a tweet to all of the followers should be fast, unless user have millions of followers

 -100 million active users

 -500 million tweets per day or 15 billion tweets per month

 -Each tweet averages a fanout of 10 deliveries

 -5 billion total tweets delivered on fanout per day

 -150 billion tweets delivered on fanout per month

 -250 billion read requests per month

 -10 billion searches per month

Timeline

 -Viewing the timeline should be fast

 -Twitter is more read heavy than write heavy

 -Optimize for fast reads of tweets

 -Ingesting tweets is write heavy


Search

 -Searching should be fast

 -Search is read-heavy
